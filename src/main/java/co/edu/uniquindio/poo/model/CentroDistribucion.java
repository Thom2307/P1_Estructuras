package co.edu.uniquindio.poo.model;

import co.edu.uniquindio.poo.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.poo.util.ComparadorPrioridad;
import co.edu.uniquindio.poo.util.ComparadorTiempo;

import java.util.*;

public class CentroDistribucion {

    private final LinkedHashMap<String, Paquete> registroPaquetes;
    private final Map<String, Repartidor> repartidores;
    private final Map<String, List<Paquete>> paquetesPorMunicipio;
    private final Queue<Paquete> colaLlegada;
    private final PriorityQueue<Paquete> colaPrioridad;
    private final ListaSimplementeEnlazada<Paquete> historialEntregas;

    public CentroDistribucion() {
        this.registroPaquetes = new LinkedHashMap<>();
        this.repartidores = new HashMap<>();
        this.paquetesPorMunicipio = new HashMap<>();
        this.colaLlegada = new LinkedList<>();
        this.historialEntregas = new ListaSimplementeEnlazada<>();

        Comparator<Paquete> reglasDespacho = new ComparadorPrioridad()
                .thenComparing(new ComparadorTiempo())
                .thenComparing(Paquete::compareTo);

        this.colaPrioridad = new PriorityQueue<>(reglasDespacho);
    }

    public void registrarPaquete(Paquete paquete) {
        if (registroPaquetes.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete con el código: " + paquete.getCodigo());
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

    public Paquete consultarPaquetePorCodigo(String codigo) {
        return registroPaquetes.get(codigo);
    }

    public Set<String> getMunicipiosConPaquetes() {
        return new TreeSet<>(paquetesPorMunicipio.keySet());
    }

    public List<Paquete> getPaquetesPorMunicipio(String municipio) {
        return paquetesPorMunicipio.getOrDefault(municipio, Collections.emptyList());
    }

    public Paquete despacharPorOrdenLlegada() {
        Paquete p = colaLlegada.poll();
        if (p != null) colaPrioridad.remove(p);
        return p;
    }

    public Paquete despacharPorPrioridad() {
        Paquete p = colaPrioridad.poll();
        if (p != null) colaLlegada.remove(p);
        return p;
    }

    public void registrarEntrega(Paquete paquete, Repartidor repartidor) {
        if (!repartidor.isDisponible()) {
            throw new IllegalStateException("El repartidor no está disponible.");
        }
        if (!repartidor.getZona().equals(paquete.getDestino())) {
            throw new IllegalArgumentException("La zona del repartidor no coincide con el destino del paquete.");
        }

        historialEntregas.agregar(paquete);
    }

    public ListaSimplementeEnlazada<Paquete> getHistorialEntregas() {
        return historialEntregas;
    }

    public Collection<Paquete> getTodosLosPaquetes() {
        return registroPaquetes.values();
    }

    public double calcularPesoTotalPorMunicipio(List<Paquete> paquetes, String municipio) {
        if (paquetes == null || paquetes.isEmpty()) return 0.0;
        return calcularPesoRecursivo(paquetes, municipio, 0);
    }

    private double calcularPesoRecursivo(List<Paquete> paquetes, String municipio, int indice) {
        if (indice == paquetes.size()) {
            return 0.0;
        }

        Paquete actual = paquetes.get(indice);
        double pesoSumar = actual.getDestino().equals(municipio) ? actual.getPeso() : 0.0;

        return pesoSumar + calcularPesoRecursivo(paquetes, municipio, indice + 1);
    }

    public int contarPaquetesPorPrioridadMinima(List<Paquete> paquetes, int prioridadMin) {
        if (paquetes == null || paquetes.isEmpty()) return 0;
        return contarPrioridadRecursivo(paquetes, prioridadMin, 0);
    }

    private int contarPrioridadRecursivo(List<Paquete> paquetes, int prioridadMin, int indice) {
        if (indice == paquetes.size()) {
            return 0;
        }

        int contar = paquetes.get(indice).getPrioridad() >= prioridadMin ? 1 : 0;

        return contar + contarPrioridadRecursivo(paquetes, prioridadMin, indice + 1);
    }

    public Paquete busquedaBinariaPorCodigo(List<Paquete> paquetesOrdenados, String codigoBuscado) {
        int inicio = 0;
        int fin = paquetesOrdenados.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Paquete paqueteMedio = paquetesOrdenados.get(medio);

            int comparacion = paqueteMedio.getCodigo().compareTo(codigoBuscado);

            if (comparacion == 0) {
                return paqueteMedio;
            }
            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    public Paquete obtenerPaqueteMasPesado(List<Paquete> paquetes) {
        if (paquetes == null || paquetes.isEmpty()) return null;
        return divideYVencerasMayorPeso(paquetes, 0, paquetes.size() - 1);
    }

    private Paquete divideYVencerasMayorPeso(List<Paquete> paquetes, int inicio, int fin) {
        if (inicio == fin) {
            return paquetes.get(inicio);
        }

        int mitad = inicio + (fin - inicio) / 2;

        Paquete masPesadoIzq = divideYVencerasMayorPeso(paquetes, inicio, mitad);
        Paquete masPesadoDer = divideYVencerasMayorPeso(paquetes, mitad + 1, fin);

        if (masPesadoIzq.getPeso() > masPesadoDer.getPeso()) {
            return masPesadoIzq;
        } else {
            return masPesadoDer;
        }
    }
}