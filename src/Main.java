import ui.frmTablaOptimizacion;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Optimización de Cuádruplos - Roberto,Samuel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        frmTablaOptimizacion form = new frmTablaOptimizacion();

        frame.add(form.getMainPanel());

        // Display the frame
        frame.setVisible(true);
        /*Servicios.Lector l = new Servicios.Lector();
        Servicios.Validador v = new Servicios.Validador();
        Servicios.Optimizador o = new Servicios.Optimizador();
        ArrayList<String> cuadruplos = l.lineas("src/archivo.txt");
        if(v.validar(cuadruplos)){

        }
        else{
        }*/
    }
}