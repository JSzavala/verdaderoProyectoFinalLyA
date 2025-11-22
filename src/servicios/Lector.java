package servicios;

import modelos.Cuadruplo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lector {
    public ArrayList<Cuadruplo> ExtraerCuadruplos(String path) {
        try {
            return Files.readAllLines( Path.of(path) )
                    .stream()
                    .map(Cuadruplo::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.err.println("Error en la lectura del archivo: " + e.getMessage());
            return new ArrayList<Cuadruplo>();
        }
    }
}
