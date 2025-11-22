import javax.swing.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center on screen

        frmTablaOptimizacion form = new frmTablaOptimizacion();

        frame.add(form.getMainPanel());

        // Display the frame
        frame.setVisible(true);
        /*Lector l = new Lector();
        Validador v = new Validador();
        Optimizador o = new Optimizador();
        ArrayList<String> cuadruplos = l.lineas("src/archivo.txt");
        if(v.validar(cuadruplos)){

        }
        else{
        }*/
    }
}