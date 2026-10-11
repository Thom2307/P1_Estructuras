package Modelo;
/**
 * Representa al repartidor encargado de la distribución.
 */
public class Repartidor {
    private String identificacion;
    private String nombre;
    private String zona;
    private boolean disponible;

    public Repartidor(String identificacion, String nombre, String zona) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación no puede ser vacía.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío.");
        }
        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("La zona no puede ser vacía.");
        }

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.zona = zona;
        this.disponible = true;
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getZona() { return zona; }
    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return String.format("Repartidor [%s] %s - Zona: %s (Disponible: %b)",
                identificacion, nombre, zona, disponible);
    }
}