package co.edu.uniquindio.poo.estructuras;

public class ListaSimplementeEnlazada<T> implements ColeccionGenerica<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;

    public ListaSimplementeEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            cola = nuevo;
        }
        tamaño++;
    }

    @Override
    public boolean eliminar(T elemento) {
        if (estaVacia()) return false;

        if (cabeza.getValor().equals(elemento)) {
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) cola = null;
            tamaño--;
            return true;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getValor().equals(elemento)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            if (actual.getSiguiente() == null) cola = actual;
            tamaño--;
            return true;
        }
        return false;
    }

    @Override
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            throw new IndexOutOfBoundsException("Posición fuera de rango: " + posicion);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getValor();
    }

    @Override
    public int getTamaño() {
        return tamaño;
    }

    @Override
    public boolean estaVacia() {
        return cabeza == null;
    }
}