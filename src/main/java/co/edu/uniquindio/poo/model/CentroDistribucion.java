package co.edu.uniquindio.poo.model;

import java.util.*;

public class CentroDistribucion {

    // 1. LinkedHashMap: Guarda paquetes manteniendo el orden estricto de llegada y permite búsqueda O(1) por código.
    private final LinkedHashMap<String, Paquete> registroPaquetes;

    // 2. Colección de Repartidores
    private final Map<String, Repartidor> repartidores;

    public CentroDistribucion() {
        this.registroPaquetes = new LinkedHashMap<>();
        this.repartidores = new HashMap<>();
    }

    /*
     * Registra un paquete nuevo en el sistema (Orden de llegada)
     */
    public void registrarPaquete(Paquete paquete) {
        // Validar que el código no exista previamente (Requerimiento estricto)
        if (registroPaquetes.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete registrado con el código: " + paquete.getCodigo());
        }
        registroPaquetes.put(paquete.getCodigo(), paquete);

        System.out.println("Paquete " + paquete.getCodigo() + " registrado exitosamente.");
    }

    /*
     * Búsqueda frecuente O(1): Consulta rápida de un paquete por su código
     */
    public Paquete consultarPaquetePorCodigo(String codigo) {
        return registroPaquetes.get(codigo); // Retorna null si no existe, lo cual es útil.
    }

    /*
     * Registra un repartidor
     */
    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidores.containsKey(repartidor.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un repartidor con ID: " + repartidor.getIdentificacion());
        }
        repartidores.put(repartidor.getIdentificacion(), repartidor);
    }

    // Getters básicos si son necesarios
    public Collection<Paquete> getTodosLosPaquetes() {
        return registroPaquetes.values();
    }
}