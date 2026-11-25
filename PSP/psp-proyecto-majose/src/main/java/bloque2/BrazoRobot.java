
package bloque2;


public class BrazoRobot extends Thread{
    
    private Almacen almacen;
    private int id;

    public BrazoRobot(Almacen almacen, int id) {
        this.almacen = almacen;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 5; i++) {

                String paquete = "Paquete " + i + " del robot " + id;

                almacen.dejarPaquete(paquete);

                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
