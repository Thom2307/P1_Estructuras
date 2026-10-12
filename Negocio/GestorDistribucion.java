package Negocio;

import Algoritmos.ComparadorPrioridad;
import Algoritmos.ComparadorTiempo;
import Estructuras.ListaSimplementeEnlazada;
import Modelo.Paquete;
import Modelo.Repartidor;

import java.util.*;

/**
 * Gestor principal del centro de distribución.
 */
public class GestorDistribucion {
    private final LinkedHashMap<String, Paquete> registroPaquetes;
    private final Map<String, Repartidor> repartidores;
    private final Map<String, List<Paquete>> paquetesPorMunicipio;
    private final Queue<Paquete> colaLlegada;
    private final PriorityQueue<Paquete> colaPrioridad;
    private final Map<String, Repartidor> asignacionesActivas;
    private final ListaSimplementeEnlazada<Paquete> historialEntregas;

    public GestorDistribucion() {
        this.registroPaquetes = new LinkedHashMap<>();
        this.repartidores = new HashMap<>();
        this.paquetesPorMunicipio = new HashMap<>();
        this.colaLlegada = new ArrayDeque<>();
        this.asignacionesActivas = new HashMap<>();
        this.historialEntregas = new ListaSimplementeEnlazada<>();

        Comparator<Paquete> reglasDespacho = new ComparadorPrioridad()
                .thenComparing(new ComparadorTiempo())
                .thenComparing(Paquete::compareTo);

        this.colaPrioridad = new PriorityQueue<>(reglasDespacho);
    }

    public void registrarPaquete(Paquete paquete) {
        if (registroPaquetes.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete registrado con el código: " + paquete.getCodigo());
        }

        registroPaquetes.put(paquete.getCodigo(), paquete);
        paquetesPorMunicipio.putIfAbsent(paquete.getDestino(), new ArrayList<>());
        paquetesPorMunicipio.get(paquete.getDestino()).add(paquete);

        colaLlegada.add(paquete);
        colaPrioridad.add(paquete);
    }

    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidores.containsKey(repartidor.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un repartidor con ID: " + repartidor.getIdentificacion());
        }
        repartidores.put(repartidor.getIdentificacion(), repartidor);
    }

    public Set<String> obtenerMunicipiosOrdenados() {
        return new TreeSet<>(paquetesPorMunicipio.keySet());
    }

    public Paquete despacharPorLlegada() {
        Paquete paqueteDespachado = colaLlegada.poll();
        if (paqueteDespachado != null) {
            colaPrioridad.remove(paqueteDespachado);
            asignarRepartidorAutomatico(paqueteDespachado);
        }
        return paqueteDespachado;
    }

    public Paquete despacharPorPrioridad() {
        Paquete paqueteDespachado = colaPrioridad.poll();
        if (paqueteDespachado != null) {
            colaLlegada.remove(paqueteDespachado);
            asignarRepartidorAutomatico(paqueteDespachado);
        }
        return paqueteDespachado;
    }

    private void asignarRepartidorAutomatico(Paquete paquete) {
        for (Repartidor repartidor : repartidores.values()) {
            if (repartidor.isDisponible() && repartidor.getZona().equalsIgnoreCase(paquete.getDestino())) {
                repartidor.setDisponible(false);
                asignacionesActivas.put(paquete.getCodigo(), repartidor);
                break;
            }
        }
    }

    public Repartidor consultarAsignacion(String codigoPaquete) {
        return asignacionesActivas.get(codigoPaquete);
    }

    public boolean registrarEntrega(String codigoPaquete) {
        Paquete paquete = registroPaquetes.get(codigoPaquete);
        if (paquete == null || !asignacionesActivas.containsKey(codigoPaquete)) {
            return false;
        }

        Repartidor repartidor = asignacionesActivas.remove(codigoPaquete);
        repartidor.setDisponible(true);
        historialEntregas.agregar(paquete);
        return true;
    }

    public int cantidadEntregas() {
        return historialEntregas.getTamaño();
    }

    public void mostrarHistorial() {
        // Recorrido clásico adaptado a tu lista propia sin iteradores
        for (int i = 0; i < historialEntregas.getTamaño(); i++) {
            Paquete paquete = historialEntregas.obtener(i);
            System.out.println("  - " + paquete);
        }
    }

    public Collection<Paquete> obtenerTodosLosPaquetes() {
        return registroPaquetes.values();
    }
}