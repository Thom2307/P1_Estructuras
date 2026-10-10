package co.edu.uniquindio.poo.model;

public class Repartidor {
    private String identificacion;
    private String nombre;
    private String zona;
    private boolean disponible;

    public Repartidor(String identificacion, String nombre, String zona, boolean disponible) {
        // Validaciones
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación no puede ser nula o vacía.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        }
        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("La zona no puede ser nula o vacía.");
        }

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.zona = zona;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getZona() { return zona; }
    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Metodo para reasignar zona si el negocio lo requiere a futuro
    public void setZona(String zona) { this.zona = zona; }

    @Override
    public String toString() {
        return String.format("Repartidor[%s] %s (Zona: %s | Disp: %b)",
                identificacion, nombre, zona, disponible);
    }
}