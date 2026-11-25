package bloque2;

public class Almacen {

    private String[] paquetes = new String[5];
    private int contador = 0;

    // Los robots dejan paquetes en el muelle.
    public synchronized void dejarPaquete(String paquete)
            throws InterruptedException {

        while (contador == paquetes.length) {
            wait();
        }

        paquetes[contador] = paquete;
        contador++;

        System.out.println("Paquete añadido: " + paquete);

        notifyAll();
    }

    // Los camiones recogen paquetes del muelle.
    public synchronized String recogerPaquete()
            throws InterruptedException {

        while (contador == 0) {
            wait();
        }

        String paquete = paquetes[0];

        for (int i = 0; i < contador - 1; i++) {
            paquetes[i] = paquetes[i + 1];
        }

        contador--;

        System.out.println("Paquete recogido: " + paquete);

        notifyAll();

        return paquete;
    }
}
