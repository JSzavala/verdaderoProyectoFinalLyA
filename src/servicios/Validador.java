package servicios;

import modelos.Cuadruplo;

import java.util.*;
import java.util.regex.Pattern;

public class Validador {

    public boolean seRepiteId(String id,ArrayList<Cuadruplo> cuadruplos){
        int cnt =0;
        for(Cuadruplo c: cuadruplos){
            if(c.getOperando1().matches(id))cnt++;
            if(cnt>1)return true;
        }
        return false;
    }
    public boolean esConstante(String valor) {
        boolean esNumero = valor.matches("\\d+(\\.\\d+)?f?");
        boolean esCadena = valor.matches("\".*\"");
        boolean esCaracter = valor.matches("'.'");

        return (esNumero || esCadena || esCaracter);
    }

    public boolean esAdicionACero(Cuadruplo c) {
        return ((c.getOperando1().equals("0") || c.getOperando2().equals("0")) && (c.getOperador().equals("+") || (c.getOperador().equals("-"))));
    }

    public boolean esVecesUno(Cuadruplo c) {
        return ((c.getOperando1().equals("1") || c.getOperando2().equals("1")) && (c.getOperador().equals("*") || (c.getOperador().equals("/"))));
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
                || operador.equals("%") || operador.equals("^")
                || operador.equals("=") || operador.equals(".");
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

    public List<String> validarFormatoLineas(List<String> lineas) {
        List<String> errores = new ArrayList<>();

        String regex = "^(\\d+)\\.?\\s*\\(([^,]+),\\s*([^,]+),\\s*([^,]+),\\s*([^)]+)\\)$";

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            if (linea.isEmpty()) continue;

            if (!linea.matches(regex)) {
                // Mensaje de error más descriptivo
                errores.add("Error de Sintaxis en línea " + (i + 1) + ": \"" + linea +
                        "\" -> Revise formato 'N. (Op1, Op, Op2, Res)' o comas internas prohibidas.");
            }
        }
        return errores;
    }


    public List<String> validarIntegridadReferencial(List<Cuadruplo> cuadruplos) {
        List<String> errores = new ArrayList<>();
        Set<Integer> idsExistentes = new HashSet<>();

        // Verificar una numeración única de cuádruplos
        for (Cuadruplo c : cuadruplos) {
            if (idsExistentes.contains(c.getNumero())) {
                errores.add("Error Semántico: El ID de cuádruplo " + c.getNumero() + " está duplicado.");
            }
            idsExistentes.add(c.getNumero());
        }

        // Paso 2: Verificar referencias [n]
        for (Cuadruplo c : cuadruplos) {
            verificarReferencia(c.getOperando1(), c.getNumero(), idsExistentes, errores);
            verificarReferencia(c.getOperando2(), c.getNumero(), idsExistentes, errores);
        }

        return errores;
    }

    private void verificarReferencia(String operando, int idActual, Set<Integer> ids, List<String> errores) {
        if (operando != null && operando.startsWith("[") && operando.endsWith("]")) {
            try {
                String numStr = operando.substring(1, operando.length() - 1);
                int refId = Integer.parseInt(numStr);

                // Referencia a ID inexistente
                if (!ids.contains(refId)) {
                    errores.add("Error de Referencia en Cuádruplo " + idActual +
                            ": El operando '" + operando + "' apunta a un ID inexistente.");
                }

                // Referencias adelantadas o ciclos
                if (refId >= idActual) {
                    errores.add("Error de Flujo en Cuádruplo " + idActual +
                            ": Referencia '" + operando + "' apunta al futuro o a sí misma (Ciclo).");
                }

            } catch (NumberFormatException e) {
                errores.add("Error de Formato en Cuádruplo " + idActual + ": Referencia inválida " + operando);
            }
        }
    }


    public String actualizarReferencia(String valor, Map<Integer, Integer> mapa) {
        if (valor == null) return null;

        if (valor.matches("\\[\\d+\\]")) {
            int idViejo = Integer.parseInt(valor.substring(1, valor.length() - 1));

            if (mapa.containsKey(idViejo)) {
                return "[" + mapa.get(idViejo) + "]";
            }
        }
        return valor;
    }
}
