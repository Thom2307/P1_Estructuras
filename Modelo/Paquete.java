package Modelo;

import java.util.Objects;

/**
 * Representa un paquete dentro del sistema logístico.
 */
public class Paquete implements Comparable<Paquete> {
    private String codigo;
    private String destino;
    private double peso;
    private int prioridad;
    private int tiempoEstimado;

    public Paquete(String codigo, String destino, double peso, int prioridad, int tiempoEstimado) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException("La prioridad debe estar comprendida entre 1 y 5.");
        }
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser mayor que cero.");
        }

        this.codigo = codigo;
        this.destino = destino;
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getCodigo() { return codigo; }
    public String getDestino() { return destino; }
    public double getPeso() { return peso; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoEstimado() { return tiempoEstimado; }

    @Override
    public int compareTo(Paquete otroPaquete) {
        return this.codigo.compareTo(otroPaquete.getCodigo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquete paquete = (Paquete) o;
        return Objects.equals(codigo, paquete.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return String.format("Paquete [%s] Destino: %s | Peso: %.1f kg | Prioridad: %d | Tiempo: %d min",
                codigo, destino, peso, prioridad, tiempoEstimado);
    }
}