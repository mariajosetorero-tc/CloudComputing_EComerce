
package bloque2;


public class CamionReparto extends Thread {
private Almacen almacen;
    private int id;

    public CamionReparto(Almacen almacen, int id) {
        this.almacen = almacen;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 5; i++) {

                String paquete = almacen.recogerPaquete();

                System.out.println("Camion " + id
                        + " lleva: " + paquete);

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
