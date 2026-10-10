package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.poo.util.ComparadorPrioridad;
import co.edu.uniquindio.poo.util.ComparadorTiempo;

import java.util.*;

public class CentroDistribucion {

    private final LinkedHashMap<String, Paquete> registroPaquetes;
    private final Map<String, Repartidor> repartidores;

    // Agrupación por municipio
    private final Map<String, List<Paquete>> paquetesPorMunicipio;

    // Colas de despacho
    private final Queue<Paquete> colaLlegada; // Para despacho normal (FIFO)
    private final PriorityQueue<Paquete> colaPrioridad; // Para despacho prioritario

    // Nuestra estructura propia para el historial
    private final ListaSimplementeEnlazada<Paquete> historialEntregas;

    public CentroDistribucion() {
        this.registroPaquetes = new LinkedHashMap<>();
        this.repartidores = new HashMap<>();
        this.paquetesPorMunicipio = new HashMap<>();
        this.colaLlegada = new LinkedList<>();
        this.historialEntregas = new ListaSimplementeEnlazada<>();

        // Configuramos la Cola de Prioridad con las reglas exactas del parcial:
        // 1. Mayor prioridad -> 2. Menor tiempo -> 3. Orden natural (alfabético por código)
        Comparator<Paquete> reglasDespacho = new ComparadorPrioridad()
                .thenComparing(new ComparadorTiempo())
                .thenComparing(Paquete::compareTo);

        this.colaPrioridad = new PriorityQueue<>(reglasDespacho);
    }

    public void registrarPaquete(Paquete paquete) {
        if (registroPaquetes.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete con el código: " + paquete.getCodigo());
        }

        // 1. Guardar en el registro principal (Mantiene el orden exacto de llegada)
        registroPaquetes.put(paquete.getCodigo(), paquete);

        // 2. Agrupar por municipio
        paquetesPorMunicipio.putIfAbsent(paquete.getDestino(), new ArrayList<>());
        paquetesPorMunicipio.get(paquete.getDestino()).add(paquete);

        // 3. Agregar a las colas de atención
        colaLlegada.add(paquete);
        colaPrioridad.add(paquete);
    }

    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidores.containsKey(repartidor.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un repartidor con ID: " + repartidor.getIdentificacion());
        }
        repartidores.put(repartidor.getIdentificacion(), repartidor);
    }

    public Paquete consultarPaquetePorCodigo(String codigo) {
        return registroPaquetes.get(codigo);
    }

    // --- REQUERIMIENTOS DE MUNICIPIOS ---

    //Retorna los municipios que tienen paquetes registrados, ordenados alfabéticamente.

    public Set<String> getMunicipiosConPaquetes() {
        // TreeSet elimina duplicados automáticamente y los ordena alfabéticamente
        return new TreeSet<>(paquetesPorMunicipio.keySet());
    }

    public List<Paquete> getPaquetesPorMunicipio(String municipio) {
        return paquetesPorMunicipio.getOrDefault(municipio, Collections.emptyList());
    }

    // --- REQUERIMIENTOS DE DESPACHO E HISTORIAL ---

    //Retorna y remueve el siguiente paquete según orden de llegada.

    public Paquete despacharPorOrdenLlegada() {
        Paquete p = colaLlegada.poll();
        if (p != null) colaPrioridad.remove(p); // Lo sacamos de la otra cola para sincronizar
        return p;
    }

    //Retorna y remueve el siguiente paquete según reglas de prioridad.

    public Paquete despacharPorPrioridad() {
        Paquete p = colaPrioridad.poll();
        if (p != null) colaLlegada.remove(p); // Lo sacamos de la otra cola para sincronizar
        return p;
    }

    //Marca un paquete como entregado y lo añade al historial propio.

    public void registrarEntrega(Paquete paquete, Repartidor repartidor) {
        if (!repartidor.isDisponible()) {
            throw new IllegalStateException("El repartidor no está disponible.");
        }
        if (!repartidor.getZona().equals(paquete.getDestino())) {
            throw new IllegalArgumentException("La zona del repartidor no coincide con el destino del paquete.");
        }

        // Se agrega a nuestra Lista Simplemente Enlazada (historial)
        historialEntregas.agregar(paquete);

        // Simulación: el repartidor queda ocupado (opcional según la lógica que quieras darle luego)
        // repartidor.setDisponible(false);
    }

    public ListaSimplementeEnlazada<Paquete> getHistorialEntregas() {
        return historialEntregas;
    }

    // Este getter nos servirá más adelante para los algoritmos
    public Collection<Paquete> getTodosLosPaquetes() {
        return registroPaquetes.values();
    }
}