import java.util.LinkedList;

public class Procesador implements Runnable{
    int id;
    LinkedList<Proceso> procesosAsignados;

    boolean desocupado;
    boolean enEjecucion;

    Proceso aEjecutar;

    Thread hilo;

    int tActual;



    public Procesador(int id) {
        this.id = id;
        enEjecucion=false;
        tActual=0;
        desocupado=true;
        procesosAsignados=new LinkedList<>();
    }

    public boolean isEnEjecucion() {
        return enEjecucion;
    }

    public void agregarProceso(Proceso p){
        enEjecucion=true;
        aEjecutar=p;
        hilo=new Thread(this, "Procesador "+id);
        hilo.start();
    }

    public boolean produceHueco(Proceso p){
        return !p.estaListo(tActual);
    }

    @Override
    public String toString() {
        if(procesosAsignados.getLast().nombre=="--"){
            procesosAsignados.removeLast();
        }
        String res="Micro "+id+"\nP\tTCC\tTTE\tTVC\tTB\tTT\tTI\tTF\n";
        for (Proceso p:procesosAsignados) {
            res+=p.toString();
        }
        return res;
    }

    @Override
    public void run() {

        if(aEjecutar.nombre=="--") {
            //Huecos; si viene de hueco =0
            procesosAsignados.add(new Proceso(tActual, aEjecutar.tFinal));
            try {
                Thread.sleep((long) (aEjecutar.tEjecucion*Despachador.delay));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tActual = aEjecutar.tFinal;
            desocupado = true;
        }else {
            //Añadirlo a la lista
            procesosAsignados.add(aEjecutar);

            //Variables
            int tRestante = aEjecutar.tEjecucion;
            int tejec = 0;

            //TI
            aEjecutar.tInicial = tActual;

            //TCC
            if (!desocupado) {
                aEjecutar.tCambioDeContexto = Despachador.tCambios;
                aEjecutar.tTotal += Despachador.tCambios;
                try {
                    Thread.sleep((long) (Despachador.tCambios*Despachador.delay));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                desocupado = false;
            }

            //Cuantum
            while (tRestante > 0) {
                try {
                    if (tRestante <= Despachador.cuantum) {
                        tejec = tRestante;
                    } else {
                        tejec = Despachador.cuantum;
                        //TVC
                        aEjecutar.tTotal += Despachador.tCambios;
                        Thread.sleep((long) (Despachador.tCambios*Despachador.delay));
                        aEjecutar.tVencimientoDeCuantum += Despachador.tCambios;
                    }
                    Thread.sleep((long) (tejec*Despachador.delay));//Ejecutando el proceso
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tRestante -= tejec;
                aEjecutar.tTotal += tejec;
            }

            //TB
            int tBloqueoTotal = aEjecutar.cantBloqueos * Despachador.tBloqueo;
            try {
                Thread.sleep((long) (tBloqueoTotal*Despachador.delay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            aEjecutar.tBloqueo = tBloqueoTotal;
            aEjecutar.tTotal += tBloqueoTotal;

            //TF
            tActual += aEjecutar.tTotal;
            aEjecutar.tFinal = aEjecutar.tInicial + aEjecutar.tTotal;


        }

        enEjecucion = false;
    }
}
