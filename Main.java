import Algoritmos.AlgoritmosPaquetes;
import Modelo.Paquete;
import Negocio.GestorDistribucion;
import Negocio.Datos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que ejecuta de forma automatizada las pruebas del sistema
 * utilizando los 30 paquetes controlados y mostrando las estructuras y
 * algoritmos.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("QuindiExpress \n");

        GestorDistribucion gestor = new GestorDistribucion();

        // 1. Carga de datos iniciales (30 paquetes estables mediante semilla)
        System.out.println("Paquetes enviados \n");
        Datos.cargarPaquetesPrueba(gestor);

        // 2. Municipios ordenados alfabeticamente
        System.out.println(" Municipios registrados:");
        for (String municipio : gestor.obtenerMunicipiosOrdenados()) {
            System.out.println("   - " + municipio);
        }

        // 3. Despacho por orden de llegada (FIFO)
        System.out.println("Despachando 5 paquetes por orden de llegada:");
        for (int i = 1; i <= 5; i++) {
            Paquete paquete = gestor.despacharPorLlegada();
            if (paquete != null) {
                System.out
                        .println(paquete.getCodigo() + "  Destino: " + paquete.getDestino());
                gestor.registrarEntrega(paquete.getCodigo());
            }
        }

        // 4. Despacho por prioridad
        System.out.println("Despachando 5 paquetes por prioridad:");
        for (int i = 1; i <= 5; i++) {
            Paquete paquete = gestor.despacharPorPrioridad();
            if (paquete != null) {
                System.out.println( paquete.getCodigo() + "  Destino: "
                        + paquete.getDestino() + "  Prioridad: " + paquete.getPrioridad());
                gestor.registrarEntrega(paquete.getCodigo());
            }
        }

        // 5. Historial con lista enlazada propia
        System.out.println("Historial de entregas");
        System.out.println("   Total entregados registrados: " + gestor.cantidadEntregas());
        gestor.mostrarHistorial();

        // 6. Algoritmos obligatorios
        System.out.println("algoritmos:");
        List<Paquete> listaGeneral = new ArrayList<>(gestor.obtenerTodosLosPaquetes());

        double pesoArmenia = AlgoritmosPaquetes.calcularPesoTotalMunicipio(listaGeneral, "Armenia");
        System.out.println("Peso total en Armenia: " + pesoArmenia + " kg");

        int prioritarios = AlgoritmosPaquetes.contarPorPrioridadMinima(listaGeneral, 4);
        System.out.println("Paquetes con prioridad : " + prioritarios);

        Paquete masPesado = AlgoritmosPaquetes.encontrarPaqueteMasPesado(listaGeneral);
        System.out.println("Paquete mas pesado: " + masPesado);

    }
}
