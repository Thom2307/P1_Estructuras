package Algoritmos;


import Modelo.Paquete;
import java.util.List;

/**
 * Clase utilitaria que centraliza los algoritmos avanzados obligatorios:
 * Recursividad, Divide y Vencerás y Búsqueda Binaria.
 */
public class AlgoritmosPaquetes {

    private AlgoritmosPaquetes() {}

    /**
     * Calcula recursivamente el peso total de los paquetes destinados a un municipio.
     */
    public static double calcularPesoTotalMunicipio(List<Paquete> listaPaquetes, String municipioBuscado) {
        if (listaPaquetes == null || listaPaquetes.isEmpty()) return 0.0;
        return calcularPesoRecursivo(listaPaquetes, municipioBuscado, 0);
    }

    private static double calcularPesoRecursivo(List<Paquete> listaPaquetes, String municipioBuscado, int indice) {
        if (indice == listaPaquetes.size()) return 0.0;
        Paquete paqueteActual = listaPaquetes.get(indice);
        double pesoAcumulado = paqueteActual.getDestino().equalsIgnoreCase(municipioBuscado) ? paqueteActual.getPeso() : 0.0;
        return pesoAcumulado + calcularPesoRecursivo(listaPaquetes, municipioBuscado, indice + 1);
    }

    /**
     * Cuenta recursivamente cuántos paquetes poseen una prioridad igual o superior al mínimo dado.
     */
    public static int contarPorPrioridadMinima(List<Paquete> listaPaquetes, int prioridadMinima) {
        if (listaPaquetes == null || listaPaquetes.isEmpty()) return 0;
        return contarPrioridadRecursiva(listaPaquetes, prioridadMinima, 0);
    }

    private static int contarPrioridadRecursiva(List<Paquete> listaPaquetes, int prioridadMinima, int indice) {
        if (indice == listaPaquetes.size()) return 0;
        Paquete paqueteActual = listaPaquetes.get(indice);
        int cumple = (paqueteActual.getPrioridad() >= prioridadMinima) ? 1 : 0;
        return cumple + contarPrioridadRecursiva(listaPaquetes, prioridadMinima, indice + 1);
    }

    /**
     * Encuentra el paquete de mayor peso utilizando la estrategia Divide y Vencerás.
     */
    public static Paquete encontrarPaqueteMasPesado(List<Paquete> listaPaquetes) {
        if (listaPaquetes == null || listaPaquetes.isEmpty()) return null;
        return encontrarMaximoDivideYVenceras(listaPaquetes, 0, listaPaquetes.size() - 1);
    }

    private static Paquete encontrarMaximoDivideYVenceras(List<Paquete> listaPaquetes, int inicio, int fin) {
        if (inicio == fin) {
            return listaPaquetes.get(inicio);
        }

        int medio = inicio + (fin - inicio) / 2;
        Paquete paqueteIzquierdo = encontrarMaximoDivideYVenceras(listaPaquetes, inicio, medio);
        Paquete paqueteDerecho = encontrarMaximoDivideYVenceras(listaPaquetes, medio + 1, fin);

        return (paqueteIzquierdo.getPeso() >= paqueteDerecho.getPeso()) ? paqueteIzquierdo : paqueteDerecho;
    }

    /**
     * Realiza una búsqueda binaria de un paquete por su código (requiere lista ordenada).
     */
    public static Paquete busquedaBinariaPorCodigo(List<Paquete> listaOrdenada, String codigoBuscado) {
        int inicio = 0;
        int fin = listaOrdenada.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Paquete paqueteActual = listaOrdenada.get(medio);
            int comparacion = paqueteActual.getCodigo().compareTo(codigoBuscado);

            if (comparacion == 0) return paqueteActual;
            if (comparacion < 0) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }
}