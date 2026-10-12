package co.edu.uniquindio.poo.estructuras;

// Usamos <T> para que sea genérica
public interface ColeccionGenerica<T> {
    void agregar(T elemento);
    boolean eliminar(T elemento);
    T obtener(int posicion);
    int getTamaño();
    boolean estaVacia();
}