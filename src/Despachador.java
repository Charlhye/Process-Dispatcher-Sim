import sun.awt.windows.ThemeReader;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Despachador{

    public static int micros;
    public static int cuantum;
    public static int tCambios;
    public static int tBloqueo;
    public static double delay;

    public static GUI gui;

    public static Queue<Proceso> procesoQueue;
    public static LinkedList<Procesador> procesadores;

    public static boolean steady=true;
    public static boolean terminado;

    public static void main (String... args){
        procesoQueue=new LinkedList<>();
        procesoQueue.add(new Proceso("B",300,2,0));
        procesoQueue.add(new Proceso("D",100,2,0));
        procesoQueue.add(new Proceso("F",500,3,0));
        procesoQueue.add(new Proceso("H",700,4,0));
        procesoQueue.add(new Proceso("J",300,2,1500));
        procesoQueue.add(new Proceso("L",3000,5,1500));
        procesoQueue.add(new Proceso("N",50,2,1500));
        procesoQueue.add(new Proceso("O",600,3,1500));
        procesoQueue.add(new Proceso("A",400,2,3000));
        procesoQueue.add(new Proceso("C",50,2,3000));
        procesoQueue.add(new Proceso("E",1000,5,3000));
        procesoQueue.add(new Proceso("G",10,2,3000));
        procesoQueue.add(new Proceso("I",450,3,3000));
        procesoQueue.add(new Proceso("K",100,2,4000));
        procesoQueue.add(new Proceso("M",80,2,4000));
        procesoQueue.add(new Proceso("P",800,4,4000));
        procesoQueue.add(new Proceso("Ñ",500,3,8000));

        terminado=false;
        gui=new GUI();

        start();

    }

    public static void start(){

        while (steady) {
            System.out.print("");
        }
        procesadores=null;
        procesadores = new LinkedList<>();

        for (int i = 0; i < Despachador.micros; i++) {
            procesadores.add(new Procesador(i + 1));
        }
        gui.iniciarTablas();
        handler();
        printer();
    }

    public static void handler() {
        while (!procesoQueue.isEmpty()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Procesador p : procesadores) {//for each Procesador en procesadores

                if (!p.isEnEjecucion()&!procesoQueue.isEmpty()) {
                    if(!p.produceHueco(procesoQueue.peek())){
                        p.agregarProceso(procesoQueue.remove());// queue.dequeue()
                        gui.progreso.setValue(gui.progreso.getValue()+1);
                    }else{
                        p.agregarProceso(new Proceso(p.tActual,procesoQueue.peek().listoEn));
                    }

                }
            }
        }
    }

    public static void printer(){
        boolean terminado;
        while(!Despachador.terminado){
            terminado=true;
            for (Procesador p:procesadores) {
                Despachador.terminado=terminado&!p.enEjecucion;
            }
        }
        Toolkit.getDefaultToolkit().beep();
        procesoQueue=new LinkedList<>();
        procesoQueue.add(new Proceso("B",300,2,0));
        procesoQueue.add(new Proceso("D",100,2,0));
        procesoQueue.add(new Proceso("F",500,3,0));
        procesoQueue.add(new Proceso("H",700,4,0));
        procesoQueue.add(new Proceso("J",300,2,1500));
        procesoQueue.add(new Proceso("L",3000,5,1500));
        procesoQueue.add(new Proceso("N",50,2,1500));
        procesoQueue.add(new Proceso("O",600,3,1500));
        procesoQueue.add(new Proceso("A",400,2,3000));
        procesoQueue.add(new Proceso("C",50,2,3000));
        procesoQueue.add(new Proceso("E",1000,5,3000));
        procesoQueue.add(new Proceso("G",10,2,3000));
        procesoQueue.add(new Proceso("I",450,3,3000));
        procesoQueue.add(new Proceso("K",100,2,4000));
        procesoQueue.add(new Proceso("M",80,2,4000));
        procesoQueue.add(new Proceso("P",800,4,4000));
        procesoQueue.add(new Proceso("Ñ",500,3,8000));

        steady=true;
        Despachador.terminado=false;
        new Thread(gui, "gui").start();
        start();
    }
}
