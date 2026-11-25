package bloque2;

public class Ejercicio4_Sincronizacion {

    public static void ejecutar() {

        Almacen almacen = new Almacen();

        BrazoRobot robot1 = new BrazoRobot(almacen, 1);
        BrazoRobot robot2 = new BrazoRobot(almacen, 2);

        CamionReparto camion1 = new CamionReparto(almacen, 1);
        CamionReparto camion2 = new CamionReparto(almacen, 2);

        robot1.start();
        robot2.start();

        camion1.start();
        camion2.start();
        try {
            robot1.join();
            robot2.join();
            camion1.join();
            camion2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Todos los paquetes han sido procesados.");
    }
}
