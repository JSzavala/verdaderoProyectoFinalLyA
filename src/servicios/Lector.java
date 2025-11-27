package servicios;

import modelos.Cuadruplo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lector {
    public ArrayList<Cuadruplo> ExtraerCuadruplos(String path) {
        ArrayList<Cuadruplo> listaCuadruplos = new ArrayList<>();
        Validador validador = new Validador();

        try {
            List<String> lineas = Files.readAllLines(Path.of(path));
            List<String> erroresSintaxis = validador.validarFormatoLineas(lineas);

            if (!erroresSintaxis.isEmpty()) {
                System.err.println("--- ERRORES DE SINTAXIS EN EL ARCHIVO ---");
                erroresSintaxis.forEach(System.err::println);
                return new ArrayList<>();
            }

            List<String> erroresSemanticos = validador.validarIntegridadReferencial(listaCuadruplos);
            if (!erroresSemanticos.isEmpty()) {
                System.err.println("--- ERRORES DE LÓGICA EN EL ARCHIVO ---");
                erroresSemanticos.forEach(System.err::println);
                return new ArrayList<>();
            }

            for (String linea : lineas) {
                if (!linea.trim().isEmpty()) {
                    listaCuadruplos.add(new Cuadruplo(linea));
                }
            }

        } catch (IOException e) {
            System.err.println("Error crítico leyendo el archivo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error al parsear un cuádruplo específico: " + e.getMessage());
        }

        return listaCuadruplos;
    }
}
