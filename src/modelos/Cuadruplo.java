package modelos;

import java.util.ArrayList;

public class Cuadruplo implements  Cloneable{
    private int numero;
    private String operador;
    private String operando1;
    private String operando2;
    private String resultado;
    private String remplazadoCon;
    private String remplezadoPor;
    private boolean esValido = true;

    public Cuadruplo(String operando1, String operador, String operando2, String resultado, int numero) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
        this.numero = numero;
        remplazadoCon = "";
    }

    public Cuadruplo(String cuadruploStr) {
        cuadruploStr = cuadruploStr.trim();

        int parentesisIndex = cuadruploStr.indexOf('(');

        if (parentesisIndex == -1 || !cuadruploStr.endsWith(")")) {
            throw new IllegalArgumentException("Formato inválido. Se esperaba: numero(op1,operador,op2,resultado)");
        }

        this.numero = Integer.parseInt(cuadruploStr.substring(0, parentesisIndex).trim());

        String contenido = cuadruploStr.substring(parentesisIndex + 1, cuadruploStr.length() - 1);
        String[] partes = contenido.split(",");

        if (partes.length != 4) {
            throw new IllegalArgumentException("Se esperaban 4 elementos, se encontraron: " + partes.length);
        }

        this.operando1 = partes[0].trim();
        this.operador = partes[1].trim();
        this.operando2 = partes[2].trim();
        this.resultado = partes[3].trim();
        remplazadoCon = "";
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setRemplazadoCon(String remplazadoCon) {
        this.remplazadoCon = remplazadoCon;
    }

    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }

    public boolean getEsValido() {
        return esValido;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public void setOperando1(String operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(String operando2) {
        this.operando2 = operando2;
    }

    public String getRemplazadoCon() {
        return remplazadoCon;
    }

    public int getNumero() {
        return numero;
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    public String getResultado() {
        return resultado;
    }

    public String getRemplezadoPor() {
        return remplezadoPor;
    }

    public void setRemplezadoPor(String remplezadoPor) {
        this.remplezadoPor = remplezadoPor;
    }

    @Override
    public String toString() {
        String resultado = numero + " " + "(" + operando1 + ", " + operador + ", " + operando2 + ", " + this.resultado + ")";
        if (getRemplazadoCon() != null && !getRemplazadoCon().isEmpty())
            resultado = getRemplazadoCon() + " -> " + resultado;
        if(getRemplezadoPor() != null && !getRemplezadoPor().isEmpty())
            resultado += " -> " + getRemplezadoPor();
        return resultado;
    }

    public String toShortString() {
        return numero + ". (" + operando1 + ", " + operador + ", " + operando2 + ", " + this.resultado + ")";
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(operando1);
        list.add(operador);
        list.add(operando2);
        list.add(resultado);
        return list;
    }

    @Override
    public Cuadruplo clone() {
        try {
            return (Cuadruplo) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Error inesperado");
            return null;
        }
    }

}
