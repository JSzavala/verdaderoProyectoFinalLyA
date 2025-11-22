package modelos;

import javax.swing.table.DefaultTableModel;

public class FilaTabla {
    private Cuadruplo cuadruplo;
    private String optimizacion;
    private String lineaAfectada;
    private Cuadruplo resultado;

    public FilaTabla(Cuadruplo cuadruplo, String optimizacion, String lineaAfectada, Cuadruplo resultado) {
        this.cuadruplo = cuadruplo;
        this.optimizacion = optimizacion;
        this.lineaAfectada = lineaAfectada;
        this.resultado = resultado;
    }

    public FilaTabla(Object[] rowData) {
        try {
            this.cuadruplo = new Cuadruplo(rowData[0].toString());
            this.optimizacion = rowData[1].toString();
            this.lineaAfectada = rowData[2].toString();
            this.resultado = new Cuadruplo(rowData[3].toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al crear FilaTabla desde rowData: " + e.getMessage(), e);
        }
    }

    public Object[] getObjectArray() {
        return new Object[]{
                cuadruplo.toString(),
                optimizacion,
                lineaAfectada,
                resultado.toString()
        };
    }

}
