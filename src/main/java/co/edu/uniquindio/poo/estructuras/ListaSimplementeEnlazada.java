package co.edu.uniquindio.poo.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaSimplementeEnlazada<T> implements ColeccionGenerica<T> {

    private Nodo<T> cabeza;
    private int tamaño;

    public ListaSimplementeEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);

        // Si está vacía, el nuevo nodo es la cabeza
        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            // Si no, recorremos hasta el último nodo y lo enlazamos al final
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    @Override
    public boolean eliminar(T elemento) {
        // Caso: Lista vacía
        if (estaVacia()) {
            return false;
        }

        // Caso: El elemento a eliminar es la cabeza (único o primero)
        if (cabeza.getValor().equals(elemento)) {
            cabeza = cabeza.getSiguiente();
            tamaño--;
            return true;
        }

        // Caso: El elemento está en el medio o al final
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getValor().equals(elemento)) {
            actual = actual.getSiguiente();
        }

        // Si el siguiente nodo no es nulo, significa que lo encontramos
        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            tamaño--;
            return true;
        }

        // Caso: Intento de eliminación de un elemento inexistente
        return false;
    }

    @Override
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
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

    // Implementación del Patrón Iterator
    // Esto permite que quien use nuestra lista pueda hacer un for(T item : lista)
    // sin tener ni idea de que internamente usamos Nodos.
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay más elementos en la lista");
                }
                T valor = actual.getValor();
                actual = actual.getSiguiente(); // Avanzamos al siguiente nodo
                return valor;
            }
        };
    }
}
