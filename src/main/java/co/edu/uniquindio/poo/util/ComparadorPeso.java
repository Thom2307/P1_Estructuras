package co.edu.uniquindio.poo.util;

import co.edu.uniquindio.poo.model.Paquete;
import java.util.Comparator;

public class ComparadorPeso implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        // De MAYOR a MENOR
        return Double.compare(p2.getPeso(), p1.getPeso());
    }
}