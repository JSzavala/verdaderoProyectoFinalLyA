package servicios;

import modelos.Cuadruplo;

import java.util.ArrayList;
import java.util.List;

public class Validador {

    public boolean esConstante(String valor) {
        boolean esNumero = valor.matches("\\d+(\\.\\d+)?");
        boolean esCadena = valor.matches("\".*\"");
        boolean esCaracter = valor.matches("'.'");

        return (esNumero || esCadena || esCaracter);
    }
    public boolean esAdicionACero(Cuadruplo c){
        return ((c.getOperando1().equals("0")||c.getOperando2().equals("0"))&&(c.getOperador().equals("+")||(c.getOperador().equals("-"))));
    }
    public boolean esVecesUno(Cuadruplo c){
        return ((c.getOperando1().equals("1")||c.getOperando2().equals("1"))&&(c.getOperador().equals("*")||(c.getOperador().equals("/"))));
    }
    public boolean usaCuadruplo(int cuadruploI, Cuadruplo cuadruplo) {
        return stringContieneCuadruplo(cuadruplo.getOperando1(), cuadruploI) ||
                stringContieneCuadruplo(cuadruplo.getOperando2(), cuadruploI);
    }

    public boolean stringContieneCuadruplo(String s, int cuadruploNum) {
        return s.contains("[" + String.valueOf(cuadruploNum) + "]");
    }

    public boolean esOperacionBinaria(String operador) {
        return operador.equals("+") || operador.equals("-")
                || operador.equals("*") || operador.equals("/")
                || operador.equals("%") || operador.equals("^");
    }

    public boolean esOperacionCritica(String operador) {
        return operador.equals("SOUT")
                || operador.equals("PMAIN");
    }

    public List<Integer> extraerReferencias(Cuadruplo cuad) {
        List<Integer> referencias = new ArrayList<>();

        extraerReferencia(cuad.getOperando1(), referencias);
        extraerReferencia(cuad.getOperando2(), referencias);

        return referencias;
    }


    private void extraerReferencia(String operando, List<Integer> referencias) {
        if (operando == null || operando.isEmpty()) {
            return;
        }

        if (operando.startsWith("[") && operando.endsWith("]")) {
            try {
                int num = Integer.parseInt(operando.substring(1, operando.length() - 1));
                referencias.add(num);
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

    public boolean sonExpresionesIguales(Cuadruplo c1, Cuadruplo c2) {
        if (!c1.getOperador().equals(c2.getOperador())) {
            return false;
        }

        boolean mismosOperandos = c1.getOperando1().equals(c2.getOperando1())
                && c1.getOperando2().equals(c2.getOperando2());

        boolean esConmutativo = c1.getOperador().equals("+") || c1.getOperador().equals("*");
        boolean operandosInvertidos = esConmutativo
                && c1.getOperando1().equals(c2.getOperando2())
                && c1.getOperando2().equals(c2.getOperando1());

        return mismosOperandos || operandosInvertidos;
    }

    public List<String> extraerVariables(Cuadruplo cuad) {
        List<String> variables = new ArrayList<>();

        extraerVariable(cuad.getOperando1(), variables);
        extraerVariable(cuad.getOperando2(), variables);

        return variables;
    }

    private void extraerVariable(String operando, List<String> variables) {
        if (operando == null || operando.isEmpty()) {
            return;
        }

        // Ignorar constantes numéricas y referencias [n]
        if (operando.matches("\\d+") || operando.matches("\\[\\d+\\]")) {
            return;
        }

        variables.add(operando);
    }

}
