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
    private boolean alterado = false;

    public Cuadruplo(String operando1, String operador, String operando2, String resultado, int numero) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
        this.numero = numero;
        remplazadoCon = "";
    }

    public Cuadruplo(String cuadruploStr){
        cuadruploStr = cuadruploStr.trim();

        int parentesisIndex = cuadruploStr.indexOf('(');

        String[] partes;
        try{
            if (parentesisIndex == -1 || !cuadruploStr.endsWith(")")) {
                throw new IllegalArgumentException("Formato inválido. Se esperaba: numero(op1,operador,op2,resultado)");
            }

            this.numero = Integer.parseInt(cuadruploStr.substring(0, parentesisIndex).trim());

            String contenido = cuadruploStr.substring(parentesisIndex + 1, cuadruploStr.length() - 1);
            partes = contenido.split(",");
            if (partes.length != 4) {
                throw new IllegalArgumentException("Se esperaban 4 elementos, se encontraron: " + partes.length);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
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

    public boolean isAlterado() {
        return alterado;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
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

    public boolean esReferenciaAMemoria(String operando) {
        return operando.matches("ID.*") || operando.equals("IDPMAIN") || operando.matches("\\[\\d+\\]");
    }

    public boolean esOperacionValidable() {
        return operador.equals("+") || operador.equals("-") || operador.equals("*") || 
               operador.equals("/") || operador.equals("%") || operador.equals("^") || 
               operador.equals("=");
    }

    public boolean contieneIncognita() {
        if (esReferenciaAMemoria(operando1) || esReferenciaAMemoria(operando2)) {
            return false;
        }
        return operando1.matches("[a-zA-Z_][a-zA-Z0-9_]*") || operando2.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public boolean sonAmbosConstantesNumericos() {
        return operando1.matches("\\d+(\\.\\d+)?") && operando2.matches("\\d+(\\.\\d+)?");
    }

    public String evaluarResultado() {
        if (esReferenciaAMemoria(operando1) || esReferenciaAMemoria(operando2)) {
            return evaluarConReferenciaAMemoria();
        }
        if (!sonAmbosConstantesNumericos()) {
            return generarResultadoConIncognita();
        }
        return evaluarOperacionNumerica();
    }

    private String evaluarConReferenciaAMemoria() {
        boolean op1EsReferencia = esReferenciaAMemoria(operando1);
        boolean op2EsReferencia = esReferenciaAMemoria(operando2);
        
        if (op1EsReferencia && op2EsReferencia) {
            return operando1 + operador + operando2;
        }
        
        if (operador.equals("=")) {
            if (op1EsReferencia) {
                return operando2;
            }
            if (op2EsReferencia) {
                return operando1;
            }
        }
        
        return operando1 + operador + operando2;
    }

    private String evaluarOperacionNumerica() {
        try {
            double op1 = Double.parseDouble(operando1);
            double op2 = Double.parseDouble(operando2);
            double res = 0;
            boolean operacionValida = true;

            switch (operador) {
                case "+":
                    res = op1 + op2;
                    break;
                case "-":
                    res = op1 - op2;
                    break;
                case "*":
                    res = op1 * op2;
                    break;
                case "/":
                    if (op2 == 0) {
                        return "ERROR: División por cero";
                    }
                    res = op1 / op2;
                    break;
                case "%":
                    if (op2 == 0) {
                        return "ERROR: Módulo por cero";
                    }
                    res = op1 % op2;
                    break;
                case "^":
                    res = Math.pow(op1, op2);
                    break;
                default:
                    operacionValida = false;
            }

            if (!operacionValida) {
                return "ERROR: Operador no soportado";
            }

            if (res == (long) res) {
                return String.valueOf((long) res);
            } else {
                return String.valueOf(res);
            }
        } catch (NumberFormatException e) {
            return "ERROR: Operandos inválidos";
        }
    }

    private String generarResultadoConIncognita() {
        String var1 = obtenerVariable(operando1);
        String var2 = obtenerVariable(operando2);
        String coef1 = obtenerCoeficiente(operando1);
        String coef2 = obtenerCoeficiente(operando2);

        if (!var1.isEmpty() && var2.isEmpty()) {
            return procesarOperacionConVariable(coef1, var1, operador, coef2);
        } else if (var1.isEmpty() && !var2.isEmpty()) {
            return procesarOperacionConVariable(coef2, var2, operador, coef1);
        } else if (!var1.isEmpty() && !var2.isEmpty()) {
            if (var1.equals(var2)) {
                return procesarOperacionDosVariables(coef1, var1, operador, coef2);
            } else {
                return operando1 + operador + operando2;
            }
        }

        return operando1 + operador + operando2;
    }

    private String obtenerVariable(String operando) {
        if (operando.matches("ID*") || operando.equals("ID[0-9]*PMAIN") || operando.matches("\\[\\d+\\]")) {
            return "";
        }
        if (operando.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return operando;
        }
        if (operando.matches("\\d+[a-zA-Z_][a-zA-Z0-9_]*")) {
            return operando.replaceAll("^\\d+", "");
        }
        if (operando.matches("[a-zA-Z_][a-zA-Z0-9_]*\\d+")) {
            return operando.replaceAll("\\d+$", "");
        }
        return "";
    }

    private String obtenerCoeficiente(String operando) {
        if (operando.matches("\\d+(\\.\\d+)?")) {
            return operando;
        }
        if (operando.matches("\\d+[a-zA-Z_].*")) {
            return operando.replaceAll("[a-zA-Z_].*", "");
        }
        return "1";
    }

    private String procesarOperacionConVariable(String coef, String var, String op, String otrovalor) {
        try {
            double c = coef.isEmpty() || coef.equals("") ? 1 : Double.parseDouble(coef);
            double otro = otrovalor.isEmpty() || otrovalor.equals("") ? 0 : Double.parseDouble(otrovalor);
            double resultado = 0;

            switch (op) {
                case "+":
                    resultado = c + otro;
                    break;
                case "-":
                    resultado = c - otro;
                    break;
                case "*":
                    resultado = c * otro;
                    break;
                case "/":
                    if (otro == 0) {
                        return coef + var;
                    }
                    resultado = c / otro;
                    break;
                case "%":
                    if (otro == 0) {
                        return coef + var;
                    }
                    resultado = c % otro;
                    break;
                case "^":
                    return coef + var + op + otrovalor;
                default:
                    return coef + var;
            }

            if (resultado == 0) {
                return "0";
            }

            if (op.equals("+") || op.equals("-")) {
                if (resultado == (long) resultado) {
                    return (long) resultado + op + var;
                }
                return resultado + op + var;
            } else if (op.equals("*") || op.equals("/")) {
                if (resultado == 1) {
                    return var;
                }
                if (resultado == (long) resultado) {
                    return (long) resultado + var;
                }
                return resultado + var;
            }

            return coef + var;
        } catch (NumberFormatException e) {
            return coef + var;
        }
    }

    private String procesarOperacionDosVariables(String coef1, String var, String op, String coef2) {
        try {
            double c1 = coef1.isEmpty() || coef1.equals("") ? 1 : Double.parseDouble(coef1);
            double c2 = coef2.isEmpty() || coef2.equals("") ? 1 : Double.parseDouble(coef2);
            double resultado = 0;

            switch (op) {
                case "+":
                    resultado = c1 + c2;
                    break;
                case "-":
                    resultado = c1 - c2;
                    break;
                case "*":
                    resultado = c1 * c2;
                    break;
                case "/":
                    if (c2 == 0) {
                        return coef1 + var;
                    }
                    resultado = c1 / c2;
                    break;
                case "%":
                    if (c2 == 0) {
                        return coef1 + var;
                    }
                    resultado = c1 % c2;
                    break;
                case "^":
                    return coef1 + var + op + coef2 + var;
                default:
                    return coef1 + var;
            }

            if (resultado == 0) {
                return "0";
            }

            if (op.equals("+") || op.equals("-")) {
                if (resultado == (long) resultado) {
                    return (long) resultado + var;
                }
                return resultado + var;
            } else if (op.equals("*") || op.equals("/")) {
                if (resultado == 1) {
                    return var;
                }
                if (resultado == (long) resultado) {
                    return (long) resultado + var;
                }
                return resultado + var;
            }

            return coef1 + var;
        } catch (NumberFormatException e) {
            return coef1 + var;
        }
    }

    public boolean verificarCongruencia() {
        String resultadoEsperado = evaluarResultado();

        if (resultadoEsperado.startsWith("ERROR")) {
            return false;
        }

        return resultado.equals(resultadoEsperado);
    }

    public boolean verificarCongruenciaConResolucion(ArrayList<Cuadruplo> todosLosCuadruplos) {
        if (!esOperacionValidable()) {
            return true;
        }

        String resultadoEsperado = evaluarResultadoConResolucion(todosLosCuadruplos);

        if (resultadoEsperado.startsWith("ERROR")) {
            return false;
        }

        return resultado.equals(resultadoEsperado);
    }

    public void validarYLanzarExcepcion(ArrayList<Cuadruplo> todosLosCuadruplos) {
        if (!esOperacionValidable()) {
            return;
        }

        if (!verificarCongruenciaConResolucion(todosLosCuadruplos)) {
            String resultadoEsperado = evaluarResultadoConResolucion(todosLosCuadruplos);
            throw new IllegalArgumentException("El cuádruplo no tiene el valor correcto. Esperado: " + resultadoEsperado + ", Obtenido: " + resultado);
        }
    }

    public String evaluarResultadoConResolucion(ArrayList<Cuadruplo> todosLosCuadruplos) {
        String op1Resuelto = resolverOperando(operando1, todosLosCuadruplos);
        String op2Resuelto = resolverOperando(operando2, todosLosCuadruplos);

        if (esReferenciaAMemoria(operando1) || esReferenciaAMemoria(operando2)) {
            return evaluarConReferenciaAMemoriaResuelto(op1Resuelto, op2Resuelto);
        }

        if (op1Resuelto.matches("\\d+(\\.\\d+)?") && op2Resuelto.matches("\\d+(\\.\\d+)?")) {
            return evaluarOperacionNumericaResuelto(op1Resuelto, op2Resuelto);
        }

        return op1Resuelto + operador + op2Resuelto;
    }

    private String resolverOperando(String operando, ArrayList<Cuadruplo> todosLosCuadruplos) {
        if (operando.matches("\\[\\d+\\]")) {
            int idReferencia = Integer.parseInt(operando.substring(1, operando.length() - 1));
            for (Cuadruplo c : todosLosCuadruplos) {
                if (c.getNumero() == idReferencia) {
                    return c.getResultado();
                }
            }
        }
        return operando;
    }

    private String evaluarConReferenciaAMemoriaResuelto(String op1Resuelto, String op2Resuelto) {
        boolean op1EsReferencia = esReferenciaAMemoria(operando1);
        boolean op2EsReferencia = esReferenciaAMemoria(operando2);

        if (op1EsReferencia && op2EsReferencia) {
            return op1Resuelto + operador + op2Resuelto;
        }

        if (operador.equals("=")) {
            if (op1EsReferencia) {
                return op2Resuelto;
            }
            if (op2EsReferencia) {
                return op1Resuelto;
            }
        }

        return op1Resuelto + operador + op2Resuelto;
    }

    private String evaluarOperacionNumericaResuelto(String op1Resuelto, String op2Resuelto) {
        try {
            double op1 = Double.parseDouble(op1Resuelto);
            double op2 = Double.parseDouble(op2Resuelto);
            double res = 0;
            boolean operacionValida = true;

            switch (operador) {
                case "+":
                    res = op1 + op2;
                    break;
                case "-":
                    res = op1 - op2;
                    break;
                case "*":
                    res = op1 * op2;
                    break;
                case "/":
                    if (op2 == 0) {
                        return "ERROR: División por cero";
                    }
                    res = op1 / op2;
                    break;
                case "%":
                    if (op2 == 0) {
                        return "ERROR: Módulo por cero";
                    }
                    res = op1 % op2;
                    break;
                case "^":
                    res = Math.pow(op1, op2);
                    break;
                default:
                    operacionValida = false;
            }

            if (!operacionValida) {
                return "ERROR: Operador no soportado";
            }

            if (res == (long) res) {
                return String.valueOf((long) res);
            } else {
                return String.valueOf(res);
            }
        } catch (NumberFormatException e) {
            return "ERROR: Operandos inválidos";
        }
    }

}
