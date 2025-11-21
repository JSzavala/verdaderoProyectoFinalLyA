import javax.swing.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center on screen

        // Create an instance of your form
        frmTablaOptimizacion form = new frmTablaOptimizacion();

        // Add the form's main panel to the frame
        frame.add(form.getMainPanel());

        // Display the frame
        frame.setVisible(true);
        /*Lector l = new Lector();
        Validador v = new Validador();
        Optimizador o = new Optimizador();
        ArrayList<String> cuadruplos = l.lineas("src/archivo.txt");
        try{
            //v.validar(cuadruplos);
        } catch (InvalidFormatException e) {
            System.err.println("Los cuadruplos no tienen un formato valido" + e.getMessage());
        }*/
    }
}