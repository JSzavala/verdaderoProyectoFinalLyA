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
            return Files.readAllLines( Path.of(path))
                    .stream()
                    .map(linea -> {
                        int firstParenthesis = linea.indexOf('(');
                        int lastParenthesis = linea.lastIndexOf(')');

                        if (firstParenthesis == -1 || lastParenthesis == -1 || lastParenthesis <= firstParenthesis) {
                            throw new IllegalArgumentException("Formato de cuadruplo inválido: " + linea + "\n El formato correcto es: i.(operando1, operador, operando2, resultado)");
                        }

                        String[] partes = linea.substring(firstParenthesis + 1, lastParenthesis).trim().split(",\\s*");

                        if(partes.length != 4){
                            throw new IllegalArgumentException("Formato de cuadruplo inválido: " + linea + "\n El formato correcto es: i.(operando1, operador, operando2, resultado)");
                        }

                        return new Cuadruplo(partes[0], partes[1], partes[2], partes[3]);
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            System.err.println("Error en la lectura del archivo: " + e.getMessage());
            return new ArrayList<Cuadruplo>();
        }
    }
}
