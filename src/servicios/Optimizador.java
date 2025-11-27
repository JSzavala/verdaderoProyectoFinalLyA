package servicios;

import modelos.Cuadruplo;
import modelos.FilaTabla;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Optimizador {
    public ArrayList<Cuadruplo> cuadruplos;
    public ArrayList<Cuadruplo> optimizados;
    public ArrayList<String> optimizaciones;
    public ArrayList<String> lineasAfectadas;
    private Validador validador;
    private Set<Integer> indicesOptimizados;

    public Optimizador(ArrayList<Cuadruplo> cuadruplos) {
        final int len = cuadruplos.size();

        this.cuadruplos = cuadruplos;
        this.optimizados = cuadruplos.stream()
                .map(Cuadruplo::clone)
                .collect(Collectors.toCollection(ArrayList::new)); // Se clonan los cuadruplos para no modificar los originales
        this.optimizaciones = new ArrayList<String>(Collections.nCopies(len, ""));
        this.lineasAfectadas = new ArrayList<String>(Collections.nCopies(len, ""));
        validador = new Validador();
        indicesOptimizados = new HashSet<>();
    }


    public void Optimizar() {
        boolean cambios = false;

        // Se elimina el codigo muerto antes de iniciar otras optimizaciones
        eliminarCodigoMuerto();

        do {
            cambios = false;
            cambios |= aplicarPropagacionConstantes();
            cambios |= aplicarFolding();
            cambios |= aplicarSimplificacionExpresiones();
            cambios |= aplicarReduccionSubexpresiones();
        } while (cambios); // Repetir hasta que no haya mas cambios

        /*
        SE: simplificacion de expresiones
        PC: Propagacion de constantes
        FO: Folding
        RS: Reduccion de subexpresiones
        CM: Codigo Muerto
        */
    }

    private boolean aplicarFolding() {
        boolean cambio = false;

        for (int i = 0; i < optimizados.size(); i++) {
            var cuadruploi = optimizados.get(i);

            // Es más optimo aplicar simplificacion que folding en estos casos
            if (validador.esAdicionACero(cuadruploi) || validador.esVecesUno(cuadruploi)) continue;

            if (!cuadruploi.getEsValido()) continue;

            if (validador.esConstante(cuadruploi.getOperando1()) && validador.esConstante(cuadruploi.getOperando2())) {
                String resultado = cuadruploi.getResultado();
                StringBuilder lineasAfectadasStr = new StringBuilder();

                String patron = "[" + cuadruploi.getNumero() + "]";
                int pos = i + 1;

                while ((pos = identificarSiguienteCuadruploAfectado(pos, cuadruploi.getNumero())) != -1) {
                    Cuadruplo cuad = optimizados.get(pos);

                    cuad.setOperando1(cuad.getOperando1().replace(patron, resultado));
                    cuad.setOperando2(cuad.getOperando2().replace(patron, resultado));
                    if (lineasAfectadasStr.length() > 0) {
                        lineasAfectadasStr.append(", ");
                    }
                    indicesOptimizados.add(pos);

                    lineasAfectadasStr.append(cuad.getNumero());

                    pos++; // Buscar desde el siguiente
                    cambio = true;
                }

                if (lineasAfectadasStr.length() > 0) {
                    lineasAfectadas.set(i, lineasAfectadasStr.toString());
                }

                optimizaciones.set(i, "FO" + (indicesOptimizados.contains(i) ? "D" : ""));
                cuadruploi.setEsValido(false);
                cuadruploi.setRemplazadoCon("Eliminado");
                cuadruploi.setRemplezadoPor(resultado);
            }
        }
        return cambio;
    }

    private boolean aplicarPropagacionConstantes() {
        boolean cambio = false;

        for (int i = 0; i < optimizados.size(); i++) {
            var cuadruploi = optimizados.get(i);

            if (!cuadruploi.getEsValido()) continue;
            String valorAsignado = cuadruploi.getOperando2();

            if (cuadruploi.getOperador().equals("=") && validador.esConstante(valorAsignado)) {
                String patron = "[" + cuadruploi.getNumero() + "]";
                StringBuilder lineasAfectadasStr = new StringBuilder();

                int pos = i + 1;
                while ((pos = identificarSiguienteCuadruploAfectado(pos, cuadruploi.getNumero())) != -1) {
                    Cuadruplo cuad = optimizados.get(pos);

                    if (cuad.getOperador().equals("=") && cuad.getOperando1().equals(cuadruploi.getOperando1())) {
                        break;
                    }

                    cuad.setOperando1(cuad.getOperando1().replace(patron, valorAsignado));
                    cuad.setOperando2(cuad.getOperando2().replace(patron, valorAsignado));
                    indicesOptimizados.add(pos);

                    if (lineasAfectadasStr.length() > 0) {
                        lineasAfectadasStr.append(", ");
                    }

                    lineasAfectadasStr.append(cuad.getNumero());

                    pos++; // Buscar desde el siguiente
                    cambio = true;
                }

                if (lineasAfectadasStr.length() > 0) {
                    optimizaciones.set(i, "PC" + (indicesOptimizados.contains(i) ? "D" : ""));
                    lineasAfectadas.set(i, lineasAfectadasStr.toString());
                    cuadruploi.setEsValido(false);
                    cuadruploi.setRemplazadoCon("Eliminado");
                }

            }
        }
        return cambio;
    }

    private boolean aplicarReduccionSubexpresiones() {
        boolean cambio = false;

        for (int i = 0; i < optimizados.size(); i++) {
            Cuadruplo cuadi = optimizados.get(i);

            if(!cuadi.getEsValido()) continue;

            if (!validador.esOperacionBinaria(cuadi.getOperador())) {
                continue;
            }

            for (int j = i + 1; j < optimizados.size(); j++) {
                Cuadruplo cuadj = optimizados.get(j);

                if (validador.sonExpresionesIguales(cuadi, cuadj)) {
                    optimizaciones.set(j, "RS" + (indicesOptimizados.contains(i) ? "D" : ""));

                    // Reemplazar con referencia al resultado anterior
                    cuadj.setOperador("");
                    cuadj.setOperando1("");
                    cuadj.setOperando2("");
                    cuadj.setRemplazadoCon("Eliminado");
                    cuadj.setRemplezadoPor("[" + cuadi.getNumero() + "]");

                    lineasAfectadas.set(j, String.valueOf(cuadi.getNumero()));
                    cambio = true;
                }
            }

        }

        return cambio;
    }


    // Simplificacion de expresiones como x + 0 = x, x * 1 = x, etc.
    private boolean aplicarSimplificacionExpresiones() {
        boolean cambio = false;

        for (int i = 0; i < optimizados.size(); i++) {
            var cuadruploi = optimizados.get(i);

            if (!cuadruploi.getEsValido()) continue;
            String valorAsignado = cuadruploi.getResultado();

            if (validador.esAdicionACero(cuadruploi) || validador.esVecesUno(cuadruploi)) {
                cuadruploi.setRemplazadoCon("Eliminado");
                cuadruploi.setRemplezadoPor(valorAsignado);

                String patron = "[" + cuadruploi.getNumero() + "]";
                StringBuilder lineasAfectadasStr = new StringBuilder();

                int pos = i + 1;
                while ((pos = identificarSiguienteCuadruploAfectado(pos, cuadruploi.getNumero())) != -1) {
                    Cuadruplo cuad = optimizados.get(pos);

                    cuad.setOperando1(cuad.getOperando1().replace(patron, valorAsignado));
                    cuad.setOperando2(cuad.getOperando2().replace(patron, valorAsignado));
                    indicesOptimizados.add(pos);

                    if (lineasAfectadasStr.length() > 0) {
                        lineasAfectadasStr.append(", ");
                    }
                    lineasAfectadasStr.append(cuad.getNumero());

                    pos++; // Buscar desde el siguiente
                    cambio = true;
                }

                if (lineasAfectadasStr.length() > 0) {
                    lineasAfectadas.set(i, lineasAfectadasStr.toString());
                }

                optimizaciones.set(i, "SE" + (indicesOptimizados.contains(i) ? "D" : ""));
                cuadruploi.setEsValido(false);
                cuadruploi.setRemplazadoCon("Eliminado");


            }
        }
        return cambio;
    }

    private void eliminarCodigoMuerto() {
        Set<Integer> cuadruplosVivos = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();

        for (int i = optimizados.size() - 1; i >= 0; i--) {
            Cuadruplo cuad = optimizados.get(i);

            if (validador.esOperacionCritica(cuad.getOperador())) {
                cuadruplosVivos.add(i);
                cola.add(i);
            }
        }

        while (!cola.isEmpty()) {
            int idx = cola.poll();
            Cuadruplo cuad = optimizados.get(idx);

            List<Integer> referencias = validador.extraerReferencias(cuad);
            for (int numCuadruplo : referencias) {
                int indice = buscarIndicePorNumero(numCuadruplo);
                if (indice != -1 && !cuadruplosVivos.contains(indice)) {
                    cuadruplosVivos.add(indice);
                    cola.add(indice);
                }
            }

            List<String> variables = validador.extraerVariables(cuad);
            for (String variable : variables) {
                int indice = buscarUltimaAsignacion(variable, idx);
                if (indice != -1 && !cuadruplosVivos.contains(indice)) {
                    cuadruplosVivos.add(indice);
                    cola.add(indice);
                }
            }
        }

        for (int i = 0; i < optimizados.size(); i++) {
            if (!cuadruplosVivos.contains(i)) {
                Cuadruplo cuadi = optimizados.get(i);
                optimizaciones.set(i, "CM");
                cuadi.setRemplazadoCon("Eliminado");
                cuadi.setEsValido(false);
            }
        }
    }

    private int buscarIndicePorNumero(int numero) {
        for (int i = 0; i < optimizados.size(); i++) {
            if (optimizados.get(i).getNumero() == numero) {
                return i;
            }
        }
        return -1;
    }

    private int buscarUltimaAsignacion(String variable, int hastaIndice) {
        for (int i = hastaIndice - 1; i >= 0; i--) {
            Cuadruplo cuad = optimizados.get(i);
            if (cuad.getOperando1().equals(variable) && cuad.getOperador().equals("=")) {
                return i;
            }
        }
        return -1;
    }

    private int identificarSiguienteCuadruploAfectado(int i, int cuadruploNum) {
        for (; i < optimizados.size(); i++) {
            var cuadruploi = optimizados.get(i);

            if (!cuadruploi.getEsValido()) {
                continue;
            }

            if (validador.usaCuadruplo(cuadruploNum, cuadruploi)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<FilaTabla> getCuadruplosOptimizados() {
        return IntStream.range(0, cuadruplos.size()).mapToObj(i -> {
            return new FilaTabla(cuadruplos.get(i), optimizaciones.get(i), lineasAfectadas.get(i), optimizados.get(i));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
