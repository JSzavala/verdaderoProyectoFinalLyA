import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
public class Lector {
    public ArrayList<String> lineas(String path) {
        ArrayList<String> lineas = null;
        try {
             lineas = (ArrayList<String>) Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            System.err.println("Error en la lectura del archivo");
            return null;
        }
        return lineas;
    }

}
