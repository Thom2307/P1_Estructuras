package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.CentroDistribucion;
import co.edu.uniquindio.poo.model.Paquete;
import co.edu.uniquindio.poo.model.Repartidor;
import co.edu.uniquindio.poo.util.Datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("sistema de distribucion \n");

        CentroDistribucion cd = new CentroDistribucion();

        Datos.cargarPaquetesPrueba(cd);
        List<Paquete> listaPaquetes = new ArrayList<>(cd.getTodosLosPaquetes());

        // municipios
        Set<String> municipios = cd.getMunicipiosConPaquetes();
        System.out.println("municipios: " + municipios + "\n");

        // busqueda por codigo
        String codigoPruebaBusqueda = listaPaquetes.get(10).getCodigo();
        System.out.println("buscando paquete " + codigoPruebaBusqueda + "...");
        System.out.println(cd.consultarPaquetePorCodigo(codigoPruebaBusqueda) + "\n");

        // paquetes por municipio
        System.out.println("paquetes en armenia:");
        for (Paquete p : cd.getPaquetesPorMunicipio("Armenia")) {
            System.out.println("  paquete " + p.getCodigo() + " peso " + p.getPeso() + "kg");
        }

        // despachos
        Paquete pLlegada = cd.despacharPorOrdenLlegada();
        System.out.println("\n despacho fifo: " + pLlegada.getCodigo() + "\n");

        Paquete pPrioridad1 = cd.despacharPorPrioridad();
        System.out.println("despacho prioridad: " + pPrioridad1 + "\n");

        Repartidor repartidorSimulado1 = new Repartidor("R_SIM", "repartidor uno", pLlegada.getDestino(), true);
        Repartidor repartidorSimulado2 = new Repartidor("R_SIM2", "repartidor dos", pPrioridad1.getDestino(), true);

        cd.registrarEntrega(pLlegada, repartidorSimulado1);
        cd.registrarEntrega(pPrioridad1, repartidorSimulado2);

        System.out.println("historial de entregas:");
        for (Paquete entregado : cd.getHistorialEntregas()) {
            System.out.print(entregado.getCodigo() + " \n");
        }
        System.out.println();

        System.out.println("Algoritmos: ");
        double pesoArmenia = cd.calcularPesoTotalPorMunicipio(listaPaquetes, "Armenia");
        System.out.printf("peso total armenia: %.2f kg\n", pesoArmenia);

        int paquetesPrioritarios = cd.contarPaquetesPorPrioridadMinima(listaPaquetes, 4);
        System.out.println("paquetes con prioridad minima: " + paquetesPrioritarios);

        Paquete masPesado = cd.obtenerPaqueteMasPesado(listaPaquetes);
        System.out.println("paquete mas pesado: " + masPesado.getCodigo() + " con " + masPesado.getPeso() + "kg");

        listaPaquetes.sort(Paquete::compareTo);
        String codigoPruebaBinaria = listaPaquetes.get(15).getCodigo();
        Paquete encontrado = cd.busquedaBinariaPorCodigo(listaPaquetes, codigoPruebaBinaria);
        System.out.println("busqueda binaria " + codigoPruebaBinaria + ": " + (encontrado != null ? encontrado.toString() : "no encontrado"));
    }
}