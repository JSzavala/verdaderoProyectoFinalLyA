import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Lector l = new Lector();
        ArrayList<String> cuadruplos = l.lineas("src/archivo.txt");
        System.out.println(cuadruplos.size());
    }
}