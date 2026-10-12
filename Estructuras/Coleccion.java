package Estructuras;

public interface Coleccion<T> {
    void agregar(T elemento);
    boolean eliminar(T elemento);
    T obtener(int posicion);
    int getTamaño();
    boolean estaVacia();
}