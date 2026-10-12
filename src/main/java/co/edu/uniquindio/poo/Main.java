package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.CentroDistribucion;
import co.edu.uniquindio.poo.model.Paquete;
import co.edu.uniquindio.poo.model.Repartidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("📦 Iniciando Sistema QuindíoExpress...\n");

        CentroDistribucion cd = new CentroDistribucion();

        // 1. Registrar 6 Repartidores

        cd.registrarRepartidor(new Repartidor("R001", "Thomas Orozco", "Armenia", true));
        cd.registrarRepartidor(new Repartidor("R002", "Faber Andres", "Salento", true));
        cd.registrarRepartidor(new Repartidor("R003", "Ana Londoño", "Calarcá", true));
        cd.registrarRepartidor(new Repartidor("R004", "Alex Zabala", "Quimbaya", true));
        cd.registrarRepartidor(new Repartidor("R005", "Juan Pérez", "Circasia", true));
        cd.registrarRepartidor(new Repartidor("R006", "María Gómez", "Armenia", true));

        // 2. Registrar 30 Paquetes con 5 destinos diferentes y códigos no consecutivos (Requisito)

        Object[][] datosPaquetes = {
                // Código, Destino, Peso(kg), Prioridad(1-5), Tiempo(min)
                {"PQ731", "Armenia", 4.5, 5, 45},
                {"PQ105", "Salento", 3.2, 5, 25},
                {"PQ942", "Armenia", 7.2, 4, 20},
                {"PQ318", "Calarcá", 1.5, 3, 30},
                {"PQ567", "Salento", 5.0, 1, 60},
                {"PQ829", "Armenia", 2.3, 2, 15},
                {"PQ420", "Quimbaya", 8.1, 3, 30},
                {"PQ111", "Circasia", 0.5, 5, 10},
                {"PQ222", "Armenia", 12.0, 4, 50},
                {"PQ333", "Salento", 6.4, 2, 40},
                {"PQ444", "Calarcá", 2.1, 1, 20},
                {"PQ555", "Quimbaya", 9.5, 5, 35},
                {"PQ666", "Circasia", 3.3, 3, 25},
                {"PQ777", "Armenia", 4.8, 4, 15},
                {"PQ888", "Salento", 1.2, 2, 10},
                {"PQ999", "Calarcá", 7.7, 5, 55},
                {"PQ012", "Quimbaya", 5.5, 1, 45},
                {"PQ034", "Circasia", 8.8, 3, 20},
                {"PQ056", "Armenia", 2.9, 4, 30},
                {"PQ078", "Salento", 4.1, 5, 15},
                {"PQ090", "Calarcá", 6.2, 2, 35},
                {"PQ123", "Quimbaya", 1.8, 1, 25},
                {"PQ234", "Circasia", 3.7, 4, 40},
                {"PQ345", "Armenia", 9.1, 5, 10},
                {"PQ456", "Salento", 5.2, 3, 50},
                {"PQ568", "Calarcá", 0.8, 2, 15},
                {"PQ679", "Quimbaya", 15.0, 5, 60},
                {"PQ780", "Circasia", 4.4, 1, 20},
                {"PQ891", "Armenia", 7.5, 3, 35},
                {"PQ902", "Salento", 2.6, 4, 25}
        };

        for (Object[] dato : datosPaquetes) {
            Paquete p = new Paquete((String) dato[0], (String) dato[1], (Double) dato[2], (Integer) dato[3], (Integer) dato[4]);
            cd.registrarPaquete(p);
        }

        // --- PRUEBA DE REQUERIMIENTOS ---

        // A. Municipios sin repetir y ordenados alfabéticamente
        System.out.println("\n▶️ 1. Municipios con paquetes registrados (Ordenados y sin duplicados):");
        Set<String> municipios = cd.getMunicipiosConPaquetes();
        System.out.println(municipios);

        // B. Búsqueda Rápida O(1) por Código
        System.out.println("\n▶️ 2. Búsqueda rápida por código (LinkedHashMap):");
        System.out.println("Buscando PQ942: " + cd.consultarPaquetePorCodigo("PQ942"));

        // C. Paquetes destinados a un municipio
        System.out.println("\n▶️ 3. Paquetes destinados a Salento:");
        for (Paquete p : cd.getPaquetesPorMunicipio("Salento")) {
            System.out.println("  - " + p.getCodigo() + " (Peso: " + p.getPeso() + "kg)");
        }

        // D. Despachos y Colección Propia
        System.out.println("\n▶️ 4. Prueba de despachos e Historial (Lista Simplemente Enlazada Propia):");
        Repartidor r1 = new Repartidor("R001", "Thomas", "Armenia", true);

        // Despacho 1: Por orden de llegada (FIFO)
        Paquete pLlegada = cd.despacharPorOrdenLlegada();
        System.out.println("Despachado por LLEGADA: " + pLlegada.getCodigo());

        // Lo entregamos si el destino cuadra (Armenia)
        if(pLlegada.getDestino().equals("Armenia")) {
            cd.registrarEntrega(pLlegada, r1);
        }

        // Despacho 2: Por Prioridad (Las reglas de desempate)
        Paquete pPrioridad1 = cd.despacharPorPrioridad();
        Paquete pPrioridad2 = cd.despacharPorPrioridad();
        System.out.println("Despachado por PRIORIDAD 1: " + pPrioridad1);
        System.out.println("Despachado por PRIORIDAD 2: " + pPrioridad2);

        // Omitimos validaciones de zona solo para simular que se entregaron al historial
        cd.getHistorialEntregas().agregar(pPrioridad1);
        cd.getHistorialEntregas().agregar(pPrioridad2);

        System.out.println("\nRecorriendo nuestro Historial Propio con Iterator (for-each):");
        for (Paquete entregado : cd.getHistorialEntregas()) {
            System.out.print(entregado.getCodigo() + " -> ");
        }
        System.out.println("FIN");

        // E. ALGORITMOS (Recursividad y Divide y Vencerás)
        System.out.println("\n▶️ 5. Pruebas de Algoritmos:");

        // Convertimos los paquetes actuales a una Lista para enviarlos a los algoritmos
        List<Paquete> listaPaquetes = new ArrayList<>(cd.getTodosLosPaquetes());

        // E.1 Recursividad: Peso total
        double pesoArmenia = cd.calcularPesoTotalPorMunicipio(listaPaquetes, "Armenia");
        System.out.printf("Recursividad - Peso total Armenia: %.2f kg\n", pesoArmenia);

        // E.2 Recursividad: Prioridad mínima
        int paquetesPrioritarios = cd.contarPaquetesPorPrioridadMinima(listaPaquetes, 4);
        System.out.println("Recursividad - Paquetes con prioridad >= 4: " + paquetesPrioritarios);

        // E.3 Divide y Vencerás: Mayor peso
        Paquete masPesado = cd.obtenerPaqueteMasPesado(listaPaquetes);
        System.out.println("Divide y Vencerás - Paquete más pesado: " + masPesado.getCodigo() + " (" + masPesado.getPeso() + "kg)");

        // E.4 Búsqueda Binaria (Requiere ordenar por código primero)
        listaPaquetes.sort(Paquete::compareTo);
        Paquete encontrado = cd.busquedaBinariaPorCodigo(listaPaquetes, "PQ318");
        System.out.println("Búsqueda Binaria - Buscando PQ318: " + (encontrado != null ? encontrado.toString() : "No encontrado"));
    }
}