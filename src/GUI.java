import oracle.jrockit.jfr.JFR;
import sun.awt.image.ImageWatched;
import sun.nio.cs.MS1257;
import sun.security.krb5.internal.crypto.Des;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class GUI implements Runnable{
    private JPanel mainPanel;
    private JTextField microsfTF;
    private JTextField bloqueoTF;
    private JTextField cambiosTF;
    private JTextField cuantumTF;
    private JButton run;

    private JPanel micros;
    private JTable M1;
    private JTable M2;
    private JTable M3;
    private JTable M4;
    private JTable M5;
    private JTable M6;
    private JTable M7;
    private JTable M8;
    private JTable M9;
    private JTable M10;
    private JTable M11;
    private JTable M12;
    private JTable M13;
    private JTable M14;
    private JTable M15;
    private JTable M16;
    private JTable M17;
    private JTable M18;
    private JTextField delayTF;
    public JProgressBar progreso;

    private JTable[] mps;
    JFrame frame=new JFrame();

    public GUI(){
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel.setPreferredSize(new Dimension(frame.getHeight(),frame.getWidth()));
        frame.setTitle("Despachador");
        progreso.setMinimum(0);
        progreso.setMaximum(17);
        progreso.setStringPainted(true);
        progreso.setBorder(BorderFactory.createTitledBorder("Progreso..."));
        progreso.setIndeterminate(true);
        delayTF.setToolTipText("Uno es a tiempo real");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        copiarTablas();

        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                start();
                for (JTable tabla:mps
                        ) {
                    tabla.setVisible(false);
                }
                progreso.setValue(progreso.getMinimum());
            }
        });
    }

    public void start(){
        progreso.setIndeterminate(false);

        if(!microsfTF.getText().equals("")&!cuantumTF.getText().equals("")&!cambiosTF.getText().equals("")&!bloqueoTF.getText().equals("")) {
            if (Integer.parseInt(microsfTF.getText()) > 0 & Integer.parseInt(microsfTF.getText()) <= 18 & Integer.parseInt(cuantumTF.getText()) > 0 & Integer.parseInt(cambiosTF.getText()) >= 0 & Integer.parseInt(bloqueoTF.getText()) >= 0 & Double.parseDouble(delayTF.getText()) >= 1) {
                Despachador.micros = Integer.parseInt(microsfTF.getText());
                Despachador.cuantum = Integer.parseInt(cuantumTF.getText());
                Despachador.tCambios = Integer.parseInt(cambiosTF.getText());
                Despachador.tBloqueo = Integer.parseInt(bloqueoTF.getText());

                if (delayTF.getText().equals("")) {
                    Despachador.delay = 1;
                } else {
                    Despachador.delay = Double.parseDouble(delayTF.getText());
                }
                new Thread(this, "gui").start();
                Despachador.steady = false;
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Checa los datos, no pueden ser menores o iguales a cero y no puede haber mas de 17 micros", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Todas las casillas deben tener un numero", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void copiarTablas(){
        mps=new JTable[18];
        M1.setTableHeader(new JTableHeader());
        mps[0]=M1;
        mps[1]=M2;
        mps[2]=M3;
        mps[3]=M4;
        mps[4]=M5;
        mps[5]=M6;
        mps[6]=M7;
        mps[7]=M8;
        mps[8]=M9;
        mps[9]=M10;
        mps[10]=M11;
        mps[11]=M12;
        mps[12]=M13;
        mps[13]=M14;
        mps[14]=M15;
        mps[15]=M16;
        mps[16]=M17;
        mps[17]=M18;
        for (JTable tabla:mps
             ) {
            tabla.setVisible(false);
        }
    }

    public void iniciarTablas(){
        int cont=0;
        for (Procesador p: Despachador.procesadores) {
            mps[cont].setModel(new ProgramTableModel(p.procesosAsignados));
            mps[cont].setVisible(true);
            cont++;
        }
    }

    @Override
    public void run() {
        while(!Despachador.terminado){
            frame.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
