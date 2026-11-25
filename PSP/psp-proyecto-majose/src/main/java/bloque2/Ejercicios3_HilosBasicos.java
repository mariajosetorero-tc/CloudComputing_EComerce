package bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Ejercicios3_HilosBasicos {

    public static void ejecutar(Scanner sc) {

        int opcion;

        do {
            System.out.println("\n--- ECOFLEET: CALCULO DE RUTAS ---");
            System.out.println("1. Procesamiento secuencial");
            System.out.println("2. FixedThreadPool");
            System.out.println("3. CachedThreadPool");
            System.out.println("4. Comparar tiempos");
            System.out.println("5. Control de hilos");
            System.out.println("0. Volver");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    procesarSecuencial();
                    break;
                case 2:
                    procesarPedidos(Executors.newFixedThreadPool(5),
                            "FixedThreadPool");
                    break;
                case 3:
                    procesarPedidos(Executors.newCachedThreadPool(),
                            "CachedThreadPool");
                    break;
                case 4:
                    compararTiempos();
                    break;
                case 5:
                    controlarHilos();
                    break;
                case 0:
                    System.out.println("Volviendo al menu.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    // Crear los 500 pedidos.
    private static List<Pedido> crearPedidos() {

        List<Pedido> pedidos = new ArrayList<>();

        for (int i = 1; i <= 500; i++) {
            Pedido pedido = new Pedido(i, "Centro A", "Centro B");
            pedidos.add(pedido);
        }

        return pedidos;
    }

    // Procesamiento secuencial.
    private static long procesarSecuencial() {

        List<Pedido> pedidos = crearPedidos();

        long inicio = System.currentTimeMillis();

        try {
            for (Pedido pedido : pedidos) {
                calcularRuta(pedido);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Procesamiento interrumpido.");
        }

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;

        System.out.println("Pedidos procesados: " + pedidos.size());
        System.out.println("Tiempo secuencial: " + tiempo + " ms");

        return tiempo;
    }

    // Procesamiento utilizando varios hilos.
    private static long procesarPedidos(ExecutorService executor,
            String nombre) {

        List<Pedido> pedidos = crearPedidos();
        List<Future<ResultadoRuta>> resultados = new ArrayList<>();

        long inicio = System.currentTimeMillis();

        try {
            for (Pedido pedido : pedidos) {

                Callable<ResultadoRuta> tarea = () -> calcularRuta(pedido);

                Future<ResultadoRuta> resultado = executor.submit(tarea);

                resultados.add(resultado);
            }

            // Recoger los resultados.
            for (int i = 0; i < resultados.size(); i++) {
                ResultadoRuta ruta = resultados.get(i).get();

                if (i < 3) {
                    System.out.println(ruta);
                }
            }

        } catch (Exception e) {
            System.out.println("Error calculando rutas: " + e.getMessage());

        } finally {
            executor.shutdown();

            try {
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;

        System.out.println("Pedidos procesados: " + resultados.size());
        System.out.println("Tiempo con " + nombre + ": " + tiempo + " ms");

        return tiempo;
    }

    // Calcular la ruta y el coste.
    private static ResultadoRuta calcularRuta(Pedido pedido)
            throws InterruptedException {

        Thread.sleep(5);

        double distancia = (pedido.getId() * 7) % 300 + 50;
        double coste = distancia * 1.5;

        return new ResultadoRuta(
                pedido.getId(), distancia, coste);
    }

    private static void compararTiempos() {

        long secuencial = procesarSecuencial();

        long paralelo = procesarPedidos(
                Executors.newFixedThreadPool(5), "FixedThreadPool");

        System.out.println("Diferencia: " + (secuencial - paralelo) + " ms");
    }

    // Control de hilos.
    private static void controlarHilos() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Callable<String> tarea = () -> {

                for (int i = 1; i <= 5; i++) {

                    if (Thread.currentThread().isInterrupted()) {
                        return "Tarea interrumpida.";
                    }

                    System.out.println("Procesando tarea " + i);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return "Tarea interrumpida.";
                    }
                }

                return "Tarea finalizada.";
            };

            Future<String> resultado = executor.submit(tarea);

            Thread.sleep(2000);

            resultado.cancel(true);

            System.out.println("Tarea cancelada: "
                    + resultado.isCancelled());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally {
            executor.shutdownNow();

            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
