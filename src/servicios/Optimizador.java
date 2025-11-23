package servicios;

import modelos.Cuadruplo;
import modelos.FilaTabla;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Optimizador {
    public ArrayList<Cuadruplo> cuadruplos;
    public ArrayList<Cuadruplo> optimizados;
    Set<Cuadruplo> repetidos;

    public Optimizador(ArrayList<Cuadruplo> cuadruplos){
        this.cuadruplos = cuadruplos;
    }
    private boolean isNumeric(String s){
        if(s.length()!=1)return false;
        return (s.charAt(0)>='0'&&s.charAt(0)<='9');
    }
    private String tipo (Cuadruplo c){
        if(repetidos.contains(c))return "RS";
        if(c.getOperando1().equals(c.getResultado())||c.getOperando2().equals(c.getResultado()))return "SE";
        if(isNumeric(c.getOperando2())&&isNumeric(c.getOperando1()))return "FO";
        return "X";
    }
    private void Optimizar(){
        for(Cuadruplo c: cuadruplos){
            if(!tipo(c).equals("X")){
                //TODO: añadir el detectar si un es codigo muerto o si es propagacion de constantes
            }
            repetidos.add(c);
        }
        /*
        SE: simplificacion de expresiones
        FO: Folding
        PC: Propagacion de constantes
        RS: Reduccion de subexpresiones
        CM: Codigo Muerto
        */

    }

    public ArrayList<FilaTabla> getCuadruplosOptimizados(){
        return cuadruplos.stream().map(cuadruplo ->{
            return new FilaTabla(cuadruplo, "",  "", cuadruplo);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
