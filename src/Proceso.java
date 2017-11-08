public class Proceso {
    int tCambioDeContexto;
    int tEjecucion;
    int tVencimientoDeCuantum;
    int tBloqueo;
    int tTotal;
    int tInicial;
    int tFinal;

    String nombre;

    int cantBloqueos;
    int listoEn;

    public Proceso(String nombre, int tEjecucion, int cantBloqueos, int listoEn) {
        this.tEjecucion = tEjecucion;
        this.nombre = nombre;
        this.cantBloqueos = cantBloqueos;
        this.listoEn = listoEn;
    }

    public Proceso(int tInicial, int tFinal) {
        this.tInicial = tInicial;
        this.tFinal = tFinal;
        this.tTotal= tFinal-tInicial;
        this.tEjecucion=tFinal-tInicial;
        this.tCambioDeContexto=0;
        this.nombre="--";
    }

    public boolean estaListo(int tiempo){
        return listoEn<=tiempo;
    }

    @Override
    public String toString() {
        return nombre+"\t"+tCambioDeContexto+"\t"+tEjecucion+"\t"+tVencimientoDeCuantum+"\t"+tBloqueo+"\t"+tTotal+"\t"+tInicial+"\t"+tFinal+"\n";
    }
}
