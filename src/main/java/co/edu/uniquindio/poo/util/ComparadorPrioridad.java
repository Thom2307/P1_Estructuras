package co.edu.uniquindio.poo.util;

import co.edu.uniquindio.poo.model.Paquete;
import java.util.Comparator;

public class ComparadorPrioridad implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        // Invertimos p2 y p1 para que sea de MAYOR a MENOR
        return Integer.compare(p2.getPrioridad(), p1.getPrioridad());
    }
}