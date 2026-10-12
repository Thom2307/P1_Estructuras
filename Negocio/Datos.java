
package Negocio;

import Modelo.Paquete;
import Modelo.Repartidor;
import java.util.Random;



public class Datos {

    private Datos() {}

    public static void cargarPaquetesPrueba(GestorDistribucion gestor) {
        Random random = new Random();
        String[] municipios = {"Armenia", "Calarca", "Salento", "Montenegro", "Quimbaya", "Circasia", "La Tebaida"};

        // Genera 30 paquetes con codigos unicos de PQ001 a PQ030
        for (int i = 1; i <= 30; i++) {
            String codigo = String.format("PQ%03d", i);
            String destino = municipios[random.nextInt(municipios.length)];
            double peso = Math.round((random.nextDouble() * 15.0 + 1.0) * 10.0) / 10.0;
            int prioridad = random.nextInt(5) + 1;
            int tiempo = random.nextInt(50) + 10;

            gestor.registrarPaquete(new Paquete(codigo, destino, peso, prioridad, tiempo));
        }

        // Registro de repartidores para cubrir las zonas
        gestor.registrarRepartidor(new Repartidor("R001", "Carlos Perez", "Armenia"));
        gestor.registrarRepartidor(new Repartidor("R002", "Ana Gomez", "Calarca"));
        gestor.registrarRepartidor(new Repartidor("R003", "Luis Ramirez", "Salento"));
        gestor.registrarRepartidor(new Repartidor("R004", "Maria Torres", "Montenegro"));
        gestor.registrarRepartidor(new Repartidor("R005", "Jorge Diaz", "Quimbaya"));
        gestor.registrarRepartidor(new Repartidor("R006", "Sofia Castro", "Circasia"));
        gestor.registrarRepartidor(new Repartidor("R007", "Andres Ruiz", "La Tebaida"));
    }
}