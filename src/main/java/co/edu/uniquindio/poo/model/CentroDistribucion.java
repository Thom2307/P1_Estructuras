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


    // ==========================================
    //        ALGORITMOS RECURSIVOS
    // ==========================================

    //1. Determinar el peso total de los paquetes destinados a un municipio (Wrapper)
    public double calcularPesoTotalPorMunicipio(List<Paquete> paquetes, String municipio) {
        if (paquetes == null || paquetes.isEmpty()) return 0.0;
        return calcularPesoRecursivo(paquetes, municipio, 0);
    }

    // Metodo recursivo real (privado)
    private double calcularPesoRecursivo(List<Paquete> paquetes, String municipio, int indice) {
        // Caso base: llegamos al final de la lista
        if (indice == paquetes.size()) {
            return 0.0;
        }

        // Caso recursivo: avanzamos sumando o no dependiendo del municipio
        Paquete actual = paquetes.get(indice);
        double pesoSumar = actual.getDestino().equals(municipio) ? actual.getPeso() : 0.0;

        return pesoSumar + calcularPesoRecursivo(paquetes, municipio, indice + 1);
    }

    /**
     * 2. Determinar cuántos paquetes poseen una prioridad igual o superior (Wrapper)
     */
    public int contarPaquetesPorPrioridadMinima(List<Paquete> paquetes, int prioridadMin) {
        if (paquetes == null || paquetes.isEmpty()) return 0;
        return contarPrioridadRecursivo(paquetes, prioridadMin, 0);
    }

    // Metodo recursivo real (privado)
    private int contarPrioridadRecursivo(List<Paquete> paquetes, int prioridadMin, int indice) {
        // Caso base: llegamos al final
        if (indice == paquetes.size()) {
            return 0;
        }

        // Caso recursivo
        int contar = paquetes.get(indice).getPrioridad() >= prioridadMin ? 1 : 0;

        return contar + contarPrioridadRecursivo(paquetes, prioridadMin, indice + 1);
    }


    // ==========================================
    //        ALGORITMO DE BÚSQUEDA BINARIA
    // ==========================================

    /**
     * Busca un paquete por código en una lista previamente ordenada por código.
     * Precondición: La lista DEBE estar ordenada por código de menor a mayor.
     */
    public Paquete busquedaBinariaPorCodigo(List<Paquete> paquetesOrdenados, String codigoBuscado) {
        int inicio = 0;
        int fin = paquetesOrdenados.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Paquete paqueteMedio = paquetesOrdenados.get(medio);

            int comparacion = paqueteMedio.getCodigo().compareTo(codigoBuscado);

            if (comparacion == 0) {
                return paqueteMedio; // ¡Encontrado!
            }
            if (comparacion < 0) {
                inicio = medio + 1; // Buscar en la mitad derecha
            } else {
                fin = medio - 1; // Buscar en la mitad izquierda
            }
        }
        return null; // No se encontró
    }


    // ==========================================
    //        ALGORITMO DIVIDE Y VENCERÁS
    // ==========================================

    /**
     * Determina el paquete de mayor peso dentro de un conjunto (Wrapper).
     */
    public Paquete obtenerPaqueteMasPesado(List<Paquete> paquetes) {
        if (paquetes == null || paquetes.isEmpty()) return null;
        return divideYVencerasMayorPeso(paquetes, 0, paquetes.size() - 1);
    }

    // Metodo real Divide y Vencerás
    private Paquete divideYVencerasMayorPeso(List<Paquete> paquetes, int inicio, int fin) {
        // 1. Caso Base: Si solo hay un elemento, ese es el más pesado
        if (inicio == fin) {
            return paquetes.get(inicio);
        }

        // 2. División del problema: Encontramos la mitad
        int mitad = inicio + (fin - inicio) / 2;

        // 3. Subproblema izquierdo y 4. Subproblema derecho (Llamadas recursivas)
        Paquete masPesadoIzq = divideYVencerasMayorPeso(paquetes, inicio, mitad);
        Paquete masPesadoDer = divideYVencerasMayorPeso(paquetes, mitad + 1, fin);

        // 5. Combinación de resultados y 6. Resultado final: Comparar ambos y devolver el mayor
        if (masPesadoIzq.getPeso() > masPesadoDer.getPeso()) {
            return masPesadoIzq;
        } else {
            return masPesadoDer;
        }
    }

}