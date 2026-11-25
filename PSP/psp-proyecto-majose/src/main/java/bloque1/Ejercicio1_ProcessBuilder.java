package bloque1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Ejercicio1_ProcessBuilder {

    public static void ejecutar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n--- ENTREGA 1: GESTION DE PROCESOS ---");
            System.out.println("1. Mostrar contenido del directorio");
            System.out.println("2. Hacer ping");
            System.out.println("3. Abrir la calculadora");
            System.out.println("0. Volver al menu principal");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
                opcion = -1;
                continue;
            }

            try {
                ProcessBuilder pb;

                switch (opcion) {

                    case 1:
                        pb = new ProcessBuilder("cmd", "/c", "dir");
                        ejecutarProceso(pb);
                        break;

                    case 2:
                        System.out.print("Introduce una direccion IP: ");
                        String direccion = sc.nextLine();

                        pb = new ProcessBuilder(
                                "ping", "-n", "4", direccion
                        );

                        ejecutarProceso(pb);
                        break;

                    case 3:
                        new ProcessBuilder("calc").start();
                        System.out.println("Calculadora iniciada.");
                        break;

                    case 0:
                        System.out.println("Volviendo al menu principal.");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (IOException e) {
                System.out.println(
                        "Error al iniciar el proceso: " + e.getMessage()
                );
            }

        } while (opcion != 0);
    }

    private static void ejecutarProceso(ProcessBuilder pb)
            throws IOException {

        pb.inheritIO();

        try {
            Process proceso = pb.start();

            proceso.waitFor();

            int codigoSalida = proceso.exitValue();

            System.out.println("Codigo de salida: " + codigoSalida);

            if (codigoSalida == 0) {
                System.out.println("Proceso finalizado correctamente.");
            } else {
                System.out.println("El proceso termino con errores.");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("La espera del proceso fue interrumpida.");
        }
    }
}
