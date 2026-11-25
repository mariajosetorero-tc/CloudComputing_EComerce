package bloque1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.Scanner;

public class Ejercicio2_Comunicacion {

    public static void ejecutar(Scanner sc) {

        System.out.println("\n--- ENTREGA 2: COMUNICACION ENTRE PROCESOS ---");
        System.out.print("Escribe un mensaje para el proceso hijo: ");
        String mensaje = sc.nextLine();

        String java = Path.of(
                System.getProperty("java.home"),
                "bin",
                System.getProperty("os.name").toLowerCase().contains("win")
                ? "java.exe" : "java"
        ).toString();

        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(
                java,
                "-cp",
                classpath,
                Ejercicio2_Comunicacion.class.getName(),
                "hijo"
        );

        pb.redirectErrorStream(true);

        try {
            Process proceso = pb.start();

            // Enviar el mensaje al proceso hijo.
            try (BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream()))) {

                escritor.write(mensaje);
                escritor.newLine();
            }

            // Recibir la respuesta del proceso hijo.
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {

                String respuesta;

                while ((respuesta = lector.readLine()) != null) {
                    System.out.println("Respuesta del proceso hijo: "
                            + respuesta);
                }
            }

            int codigoSalida = proceso.waitFor();
            System.out.println("Codigo de salida: " + codigoSalida);

        } catch (IOException e) {
            System.out.println("Error de comunicacion: " + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("La comunicacion fue interrumpida.");
        }
    }

    public static void main(String[] args) throws IOException {

        // Si recibe el argumento "hijo", actua como proceso hijo.
        if (args.length > 0 && args[0].equals("hijo")) {

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(System.in))) {

                String mensaje = lector.readLine();

                if (mensaje != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                } else {
                    System.out.println("No se ha recibido ningun mensaje.");
                }
            }
        }
    }
}
