import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class frmTablaOptimizacion {
    private JTable tblCuadruplos;
    private JPanel panel1;

    private void createUIComponents() {
        String[] columnNames = {"Column 1", "Column 2", "Column 3", "Column 4"};
        Object[][] data = {
                {"Data 1", "Short", "Short", "Data 4"},
                {"Data 1", "Short", "Short", "Data 4"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblCuadruplos = new JTable(model);

        // Set column widths - columns 2 and 3 are smaller
            tblCuadruplos.getColumnModel().getColumn(0).setPreferredWidth(200); // Column 1 - wider
            tblCuadruplos.getColumnModel().getColumn(1).setPreferredWidth(80);  // Column 2 - smaller
            tblCuadruplos.getColumnModel().getColumn(2).setPreferredWidth(80);  // Column 3 - smaller
            tblCuadruplos.getColumnModel().getColumn(3).setPreferredWidth(200); // Column 4 - wider

        // Optional: Prevent columns from being resized
        // table.getColumnModel().getColumn(1).setResizable(false);
        // table.getColumnModel().getColumn(2).setResizable(false);

        // Optional: Set minimum and maximum widths for tighter control
            tblCuadruplos.getColumnModel().getColumn(1).setMinWidth(60);
            tblCuadruplos.getColumnModel().getColumn(1).setMaxWidth(100);
            tblCuadruplos.getColumnModel().getColumn(2).setMinWidth(60);
            tblCuadruplos.getColumnModel().getColumn(2).setMaxWidth(100);
    }
    public JPanel getMainPanel() {
        return panel1;
    }
}
