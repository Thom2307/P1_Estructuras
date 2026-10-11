package Estructuras;

/**
 * Interfaz genérica que define el contrato básico para una colección propia.
 * Extiende Iterable para permitir recorridos limpios sin exponer nodos.
 */
public interface Coleccion<T> extends Iterable<T> {
    void agregar(T elemento);
    boolean eliminar(T elemento);
    T obtener(int posicion);
    int getTamaño();
    boolean estaVacia();
}