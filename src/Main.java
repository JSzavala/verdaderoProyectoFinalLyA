import modelos.Cuadruplo;
import modelos.FilaTabla;
import servicios.Lector;
import servicios.Optimizador;
import servicios.Validador;
import ui.frmTablaOptimizacion;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static ArrayList<FilaTabla> listaResultados = new ArrayList<>();
    private static Validador validador = new Validador();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Optimización de Cuádruplos - Roberto,Samuel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);

        frmTablaOptimizacion form = new frmTablaOptimizacion();
        form.getBtnIniciar().addActionListener(e -> cargarYOptimizarCuadruplos(form));
        form.getBtnOptimizados().addActionListener(e -> mostrarVentanaOptimizados(frame));
        SwingUtilities.invokeLater(()-> cargarYOptimizarCuadruplos(form));

        frame.add(form.getMainPanel());
        frame.setVisible(true);
    }


    private static void cargarYOptimizarCuadruplos(frmTablaOptimizacion form) {
        Lector lector = new Lector();
        ArrayList<Cuadruplo> cuadruplos= lector.ExtraerCuadruplos("src/recursos/cuadruplos.txt");
        form.limpiarTabla();
        Optimizador optimizador = new Optimizador(cuadruplos);
        optimizador.Optimizar();
        listaResultados = optimizador.getCuadruplosOptimizados();
        form.cargarDatos(listaResultados);

    }

    private static void mostrarVentanaOptimizados(JFrame parent){
        if (listaResultados.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Primero debes cargar y optimizar los cuádruplos.");
            return;
        }

        List<Cuadruplo> listaFinal = generarListaNormalizada(listaResultados);

        JDialog dialog = new JDialog(parent, "Código Intermedio Optimizado", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        StringBuilder sb = new StringBuilder();
        sb.append("--- CÓDIGO OPTIMIZADO ---\n\n");

        int contador = 1;
        for (Cuadruplo c : listaFinal) {

            // Solo se muestra si es valido
            if (c.getEsValido()) {
                sb.append(c.toShortString()+"\n");
            }
        }

        textArea.setText(sb.toString());
        dialog.add(new JScrollPane(textArea));
        dialog.setVisible(true);

    }


    private static List<Cuadruplo> generarListaNormalizada(ArrayList<FilaTabla> filas) {
        List<Cuadruplo> listaLimpia = new ArrayList<>();
        Map<Integer, Integer> mapaIds = new HashMap<>();
        int nuevoContador = 1;

        for (FilaTabla fila : filas) {
            Cuadruplo original = fila.getResultado();
            if (original.getEsValido()) {
                Cuadruplo copia = original.clone();
                mapaIds.put(copia.getNumero(), nuevoContador);
                copia.setNumero(nuevoContador);
                listaLimpia.add(copia);
                nuevoContador++;
            }
        }

        for (Cuadruplo c : listaLimpia) {
            c.setOperando1(validador.actualizarReferencia(c.getOperando1(), mapaIds));
            c.setOperando2(validador.actualizarReferencia(c.getOperando2(), mapaIds));
            c.setResultado(validador.actualizarReferencia(c.getResultado(), mapaIds));
        }

        return listaLimpia;
    }



}