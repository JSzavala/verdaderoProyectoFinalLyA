package servicios;

import modelos.Cuadruplo;
import modelos.FilaTabla;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Optimizador {
    public ArrayList<Cuadruplo> cuadruplos;

    public Optimizador(ArrayList<Cuadruplo> cuadruplos){
        this.cuadruplos = cuadruplos;
    }

    public ArrayList<FilaTabla> getCuadruplosOptimizados(){
        return cuadruplos.stream().map(cuadruplo ->{
            return new FilaTabla(cuadruplo, "",  "", cuadruplo);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
