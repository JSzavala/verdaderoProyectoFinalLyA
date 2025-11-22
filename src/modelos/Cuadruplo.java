package modelos;

import java.util.ArrayList;

public class Cuadruplo {
    private int numero;
    private String operador;
    private String operando1;
    private String operando2;
    private String resultado;

    public Cuadruplo(String operando1, String operador, String operando2, String resultado, int numero) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
        this.numero = numero;
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
        this.resultado = partes[3 ].trim();
    }




    @Override
    public String toString(){
        return numero + " " + "(" + operando1 +", "+operador+", "+operando2+", "+resultado + ")";
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(operando1);
        list.add(operador);
        list.add(operando2);
        list.add(resultado);
        return list;
    }



}
