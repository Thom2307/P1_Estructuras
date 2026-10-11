package Estructuras;



/**
 * Representa un nodo contenedor para la lista simplemente enlazada.
 * Su visibilidad y uso están estrictamente encapsulados.
 */
public class Nodo<T> {
    private T valor;
    private Nodo<T> siguiente;

    public Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}