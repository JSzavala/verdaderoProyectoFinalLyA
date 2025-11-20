import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Lector l = new Lector();
        Validador v = new Validador();
        Optimizador o = new Optimizador();
        ArrayList<String> cuadruplos = l.lineas("src/archivo.txt");
        try{
            //v.validar(cuadruplos);
        } catch (InvalidFormatException e) {
            System.err.println("Los cuadruplos no tienen un formato valido" + e.getMessage());
        }
    }
}