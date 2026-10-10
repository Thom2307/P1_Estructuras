package co.edu.uniquindio.poo.model;

import java.util.Objects;

// Implementamos Comparable para establecer el Orden Natural por código
public class Paquete implements Comparable<Paquete> {
    private String codigo;
    private String destino;
    private double peso;
    private int prioridad;
    private int tiempoEstimado;

    // Constructor con validaciones
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
        // Aplicamos la regla estricta: entre 1 y 5 (como lo indica la parte final del requerimiento)
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

    // Getters (Normalmente los atributos de un paquete registrado no cambian, por eso omito setters)
    public String getCodigo() { return codigo; }
    public String getDestino() { return destino; }
    public double getPeso() { return peso; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoEstimado() { return tiempoEstimado; }

    // --- Criterio de Orden Natural ---
    @Override
    public int compareTo(Paquete otroPaquete) {
        // Orden alfabético ascendente por código (Ej: PQ105 va antes que PQ318)
        return this.codigo.compareTo(otroPaquete.getCodigo());
    }

    // Sobrescribimos equals y hashCode basándonos SOLO en el código (ya que es el identificador único)
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
        return String.format("%s -> %s (Peso: %.1f kg, Prio: %d, Tiempo: %d min)",
                codigo, destino, peso, prioridad, tiempoEstimado);
    }
}