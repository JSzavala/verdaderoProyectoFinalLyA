import modelos.Cuadruplo;
import modelos.FilaTabla;
import servicios.Lector;
import servicios.Optimizador;
import ui.frmTablaOptimizacion;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Optimización de Cuádruplos - Roberto,Samuel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        frmTablaOptimizacion form = new frmTablaOptimizacion();
        form.getBtnIniciar().addActionListener(e -> cargarYOptimizarCuadruplos(form));
        SwingUtilities.invokeLater(() -> cargarYOptimizarCuadruplos(form));

        frame.add(form.getMainPanel());
        frame.setVisible(true);
    }
    private static void cargarYOptimizarCuadruplos(frmTablaOptimizacion form) {
        Lector lector = new Lector();
        ArrayList<Cuadruplo> cuadruplos= lector.ExtraerCuadruplos("src/recursos/cuadruplos.txt");

        form.limpiarTabla();
        Optimizador optimizador = new Optimizador(cuadruplos);
        optimizador.Optimizar();
        ArrayList<FilaTabla> cuadruplosOptimizados = optimizador.getCuadruplosOptimizados();
        form.cargarDatos(lector.ExtraerCuadruplos("src/recursos/cuadruplos.txt"),cuadruplosOptimizados);
    }
}