import java.util.ArrayList;

public class Cuadruplo {
    private String operador;
    private String operando1;
    private String operando2;
    private String resultado;

    public Cuadruplo(String operando1, String operador, String operando2, String resultado) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(operando1);
        list.add(operador);
        list.add(operando2);
        list.add(resultado);
        return list;
    }

}
