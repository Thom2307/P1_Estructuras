package co.edu.uniquindio.poo.util;

import co.edu.uniquindio.poo.model.Paquete;
import java.util.Comparator;

public class ComparadorTiempo implements Comparator<Paquete> {
    @Override
    public int compare(Paquete p1, Paquete p2) {
        // De MENOR a MAYOR (Orden normal)
        return Integer.compare(p1.getTiempoEstimado(), p2.getTiempoEstimado());
    }
}
