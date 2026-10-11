package Negocio;



import Algoritmos.AlgoritmosPaquetes;
import Modelo.Paquete;
import Modelo.Repartidor;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que ejecuta de forma automatizada las pruebas del sistema.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Pruebas \n");

        GestorDistribucion gestor = new GestorDistribucion();

    
        Datos.cargarPaquetesAleatorios(gestor, 5);

        // 2. Mostrar municipios ordenados alfabéticamente (TreeSet)
        System.out.println("\n Municipios registrados y ordenados:");
        for (String municipio : gestor.obtenerMunicipiosOrdenados()) {
            System.out.println(" - " + municipio);
        }

        // 3. Probar despacho por orden de llegada (FIFO)
        System.out.println("\n Mandando Paquete");
        Paquete paqueteDespachadoLlegada = gestor.despacharPorLlegada();
        if (paqueteDespachadoLlegada != null) {
            System.out.println("   > " + paqueteDespachadoLlegada);
            Repartidor repartidorAsignado = gestor.consultarAsignacion(paqueteDespachadoLlegada.getCodigo());
            if (repartidorAsignado != null) {
                System.out.println("   > Asignado a: " + repartidorAsignado.getNombre());
            }
            gestor.registrarEntrega(paqueteDespachadoLlegada.getCodigo());
            System.out.println("   > Entrega registrada en el historial.");
        }

        // 4. Probar despacho por prioridad
        System.out.println("\n Mandando con Rapidez");
        Paquete paqueteDespachadoPrioridad = gestor.despacharPorPrioridad();
        if (paqueteDespachadoPrioridad != null) {
            System.out.println("   > " + paqueteDespachadoPrioridad);
            gestor.registrarEntrega(paqueteDespachadoPrioridad.getCodigo());
        }

        // 5. Mostrar Historial usando la lista propia y el iterador oculto
        System.out.println("\nHistorial de Entregas");
        System.out.println("   Total entregados: " + gestor.cantidadEntregas());
        gestor.mostrarHistorial();

        // 6. Pruebas de Algoritmos Avanzados
        System.out.println("\nAlgoritmos Obligatorios:");
        List<Paquete> listaGeneral = new ArrayList<>(gestor.obtenerTodosLosPaquetes());

        double pesoTotalArmenia = AlgoritmosPaquetes.calcularPesoTotalMunicipio(listaGeneral, "Armenia");
        System.out.println("   - Recursividad (Peso total en Armenia): " + pesoTotalArmenia + " kg");

        int cantidadPrioritarios = AlgoritmosPaquetes.contarPorPrioridadMinima(listaGeneral, 3);
        System.out.println("   - Recursividad (Paquetes con prioridad >= 3): " + cantidadPrioritarios);

        Paquete paqueteMasPesado = AlgoritmosPaquetes.encontrarPaqueteMasPesado(listaGeneral);
        System.out.println("   - Divide y Vencerás (Paquete más pesado): " + paqueteMasPesado);

        listaGeneral.sort(Paquete::compareTo);
        if (!listaGeneral.isEmpty()) {
            Paquete paqueteBuscado = listaGeneral.get(0);
            Paquete paqueteEncontrado = AlgoritmosPaquetes.busquedaBinariaPorCodigo(listaGeneral, paqueteBuscado.getCodigo());
            System.out.println("   - Búsqueda Binaria (Buscando " + paqueteBuscado.getCodigo() + "): " + 
                    (paqueteEncontrado != null ? "Encontrado exitosamente" : "No encontrado"));
        }


    }
}