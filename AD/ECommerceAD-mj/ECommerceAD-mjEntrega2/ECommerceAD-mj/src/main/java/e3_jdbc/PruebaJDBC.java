package e3_jdbc;

public class PruebaJDBC {

    public static void main(String[] args) {

        System.out.println("PRODUCTOS:");
        GestionProductos.mostrarProductos();

        System.out.println("\nMETADATOS:");
        MetadatosBD.mostrarMetadatos();

        System.out.println("\nPEDIDO:");
        int[] productos = {1, 2};
        int[] cantidades = {2, 1};

        Transacciones.realizarPedido(1, productos, cantidades);
    }

}
