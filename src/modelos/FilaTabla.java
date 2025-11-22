package modelos;

import javax.swing.table.DefaultTableModel;

public class FilaTabla {
    private Cuadruplo cuadruplo;
    private String optimizacion;
    private int lineaAfectada;
    private Cuadruplo resultado;

    public FilaTabla(Cuadruplo cuadruplo, String optimizacion, int lineaAfectada, Cuadruplo resultado) {
        this.cuadruplo = cuadruplo;
        this.optimizacion = optimizacion;
        this.lineaAfectada = lineaAfectada;
        this.resultado = resultado;
    }

    public FilaTabla(Object[] rowData) {
        try {
            this.cuadruplo = new Cuadruplo(rowData[0].toString());
            this.optimizacion = rowData[1].toString();
            this.lineaAfectada = Integer.parseInt(rowData[2].toString());
            this.resultado = new Cuadruplo(rowData[3].toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear FilaTabla desde rowData: " + e.getMessage(), e);
        }
    }

    public Object getObjectArray() {
        Object[] fila = new Object[5];
        fila[0] = cuadruplo.toString();
        fila[1] = optimizacion;
        fila[2] = lineaAfectada;
        fila[3] = resultado.toString();

        return fila;
    }

}
