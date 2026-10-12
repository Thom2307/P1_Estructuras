package co.edu.uniquindio.poo.util;

import co.edu.uniquindio.poo.model.CentroDistribucion;
import co.edu.uniquindio.poo.model.Paquete;
import co.edu.uniquindio.poo.model.Repartidor;

import java.util.Random;

public class Datos {

    private Datos() {}

    public static void cargarPaquetesPrueba(CentroDistribucion gestor) {
        Random random = new Random();
        String[] municipios = {"Armenia", "Calarcá", "Salento", "Montenegro", "Quimbaya", "Circasia", "La Tebaida"};

        // Genera 30 paquetes con códigos únicos y no consecutivos (Ej: PQ107, PQ114, PQ121...)
        for (int i = 1; i <= 30; i++) {
            String codigo = String.format("PQ%03d", (i * 7) + 100);
            String destino = municipios[random.nextInt(municipios.length)];
            double peso = Math.round((random.nextDouble() * 15.0 + 1.0) * 10.0) / 10.0;
            int prioridad = random.nextInt(5) + 1; // Prioridad entre 1 y 5
            int tiempo = random.nextInt(50) + 10;

            gestor.registrarPaquete(new Paquete(codigo, destino, peso, prioridad, tiempo));
        }

        // Registro de repartidores para cubrir las zonas (Agregamos true para 'disponible')
        gestor.registrarRepartidor(new Repartidor("R001", "Carlos Perez", "Armenia", true));
        gestor.registrarRepartidor(new Repartidor("R002", "Ana Gomez", "Calarcá", true));
        gestor.registrarRepartidor(new Repartidor("R003", "Luis Ramirez", "Salento", true));
        gestor.registrarRepartidor(new Repartidor("R004", "Maria Torres", "Montenegro", true));
        gestor.registrarRepartidor(new Repartidor("R005", "Jorge Diaz", "Quimbaya", true));
        gestor.registrarRepartidor(new Repartidor("R006", "Sofia Castro", "Circasia", true));
        gestor.registrarRepartidor(new Repartidor("R007", "Andres Ruiz", "La Tebaida", true));
    }
}