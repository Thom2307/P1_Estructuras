package Estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación independiente del patrón Iterator para la lista enlazada simple,
 * siguiendo el mismo estilo y estructura vista en clase.
 */
public class IteradorListaSimple<T> implements Iterator<T> {
    private Nodo<T> actual;

    public IteradorListaSimple(Nodo<T> cabeza) {
        this.actual = cabeza;
    }

    @Override
    public boolean hasNext() {
        return actual != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en la lista.");
        }
        T valor = actual.getValor();
        actual = actual.getSiguiente();
        return valor;
    }
}