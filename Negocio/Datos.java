package Negocio;


import Modelo.Paquete;
import Modelo.Repartidor;
import java.util.Random;

/**
 * Clase utilitaria para poblar el sistema mediante Random o datos de prueba.
 */
public class Datos {

    private Datos() {}

    public static void cargarPaquetesAleatorios(GestorDistribucion gestor, int cantidad) {
        Random random = new Random();
        String[] municipios = {"Armenia", "Calarcá", "Salento", "Montenegro", "Quimbaya", "Circasia"};

        for (int i = 1; i <= cantidad; i++) {
            String codigo = String.format("PQ03d", i);
            String destino = municipios[random.nextInt(municipios.length)];
            double peso = 1.0 + (14.0 * random.nextDouble());
            int prioridad = 1 + random.nextInt(5); // Estrictamente entre 1 y 5
            int tiempo = 10 + random.nextInt(50);

            try {
                gestor.registrarPaquete(new Paquete(codigo, destino, peso, prioridad, tiempo));
            } catch (Exception ignored) {}
        }

        gestor.registrarRepartidor(new Repartidor("R001", "Carlos Pérez", "Armenia"));
        gestor.registrarRepartidor(new Repartidor("R002", "Ana Gómez", "Calarcá"));
        gestor.registrarRepartidor(new Repartidor("R003", "Luis Ramírez", "Salento"));
        gestor.registrarRepartidor(new Repartidor("R004", "María Torres", "Montenegro"));
        gestor.registrarRepartidor(new Repartidor("R005", "Jorge Díaz", "Quimbaya"));
        gestor.registrarRepartidor(new Repartidor("R006", "Lucía Rojas", "Circasia"));
    }
}