package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class frmTablaOptimizacion {
    private JTable tblCuadruplos;
    private JPanel panel1;
    private JButton btnIniciar;
    private JButton btnOptimizados;
    private JLabel lblTitulo;
    private JPanel panelBotones;
    private JPanel panelSuperior;

    public frmTablaOptimizacion() {
        configurarEstilos();
    }

    private void configurarEstilos() {
        panel1.setBackground(new Color(245, 245, 250));

        if (panelSuperior != null) {
            panelSuperior.setBackground(new Color(63, 81, 181));
            panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        if (lblTitulo != null) {
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblTitulo.setForeground(Color.WHITE);
        }

        if (panelBotones != null) {
            panelBotones.setBackground(new Color(245, 245, 250));
            panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        }

        configurarBoton(btnOptimizados, new Color(76, 175, 80), new Color(67, 160, 71));
        configurarBoton(btnIniciar, new Color(33, 150, 243), new Color(30, 136, 229));
    }

    private void configurarBoton(JButton boton, Color colorNormal, Color colorHover) {
        if (boton == null) return;

        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorNormal);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 45));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorNormal);
            }
        });
    }

    private void createUIComponents() {
        String[] columnNames = {"Cuádruplo", "Optimización", "Línea", "Resultado"};
        Object[][] data = new Object[0][4];

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCuadruplos = new JTable(model);
        tblCuadruplos.setFont(new Font("Consolas", Font.PLAIN, 13));
        tblCuadruplos.setRowHeight(28);
        tblCuadruplos.setShowGrid(true);
        tblCuadruplos.setGridColor(new Color(224, 224, 224));
        tblCuadruplos.setSelectionBackground(new Color(197, 202, 233));
        tblCuadruplos.setSelectionForeground(Color.BLACK);
        tblCuadruplos.setBackground(Color.WHITE);

        JTableHeader header = tblCuadruplos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        tblCuadruplos.getColumnModel().getColumn(0).setPreferredWidth(300);
        tblCuadruplos.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

        tblCuadruplos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblCuadruplos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblCuadruplos.getColumnModel().getColumn(1).setMinWidth(100);
        tblCuadruplos.getColumnModel().getColumn(1).setMaxWidth(200);

        tblCuadruplos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblCuadruplos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblCuadruplos.getColumnModel().getColumn(2).setMinWidth(60);
        tblCuadruplos.getColumnModel().getColumn(2).setMaxWidth(100);

        tblCuadruplos.getColumnModel().getColumn(3).setPreferredWidth(300);
        tblCuadruplos.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
    }

    public JPanel getMainPanel() {
        return panel1;
    }

    public JTable getTblCuadruplos() {
        return tblCuadruplos;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JButton getBtnOptimizados() {
        return btnOptimizados;
    }

    public void agregarFila(String cuadruplo, String optimizacion, String linea, String resultado) {
        DefaultTableModel model = (DefaultTableModel) tblCuadruplos.getModel();
        model.addRow(new Object[]{cuadruplo, optimizacion, linea, resultado});
    }

    public void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblCuadruplos.getModel();
        model.setRowCount(0);
    }

    public void cargarDatos(Object[][] datos) {
        DefaultTableModel model = (DefaultTableModel) tblCuadruplos.getModel();
        model.setRowCount(0);
        for (Object[] fila : datos) {
            model.addRow(fila);
        }
    }

    public void actualizarFila(int indice, String cuadruplo, String optimizacion, String linea, String resultado) {
        DefaultTableModel model = (DefaultTableModel) tblCuadruplos.getModel();
        if (indice >= 0 && indice < model.getRowCount()) {
            model.setValueAt(cuadruplo, indice, 0);
            model.setValueAt(optimizacion, indice, 1);
            model.setValueAt(linea, indice, 2);
            model.setValueAt(resultado, indice, 3);
        }
    }

    public int obtenerCantidadFilas() {
        return tblCuadruplos.getModel().getRowCount();
    }

    public Object[] obtenerFila(int indice) {
        DefaultTableModel model = (DefaultTableModel) tblCuadruplos.getModel();
        if (indice >= 0 && indice < model.getRowCount()) {
            Object[] fila = new Object[4];
            for (int i = 0; i < 4; i++) {
                fila[i] = model.getValueAt(indice, i);
            }
            return fila;
        }
        return null;
    }
}
