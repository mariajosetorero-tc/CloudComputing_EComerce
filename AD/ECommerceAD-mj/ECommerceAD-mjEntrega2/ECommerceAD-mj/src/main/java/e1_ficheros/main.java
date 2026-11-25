/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package e1_ficheros;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author 34625
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            ConfiguracionManager cm = new ConfiguracionManager();
            Properties config = cm.cargarConfiguracion();

            String ruta = config.getProperty("path.catálogo");

            ProductoRAFDAO dao = new ProductoRAFDAO(ruta);
            CSVManager csv = new CSVManager(dao);

            int opcion;

            do {
                System.out.println("\n--- E-COMMERCE ---");
                System.out.println("1. Importar CSV");
                System.out.println("2. Buscar producto");
                System.out.println("3. Modificar stock");
                System.out.println("4. Borrar producto");
                System.out.println("5. Exportar CSV");
                System.out.println("6. Mostrar productos");
                System.out.println("7. Exportar XML");//entrega2
                System.out.println("8. Exportar JSON");//entrega2
                System.out.println("9. Importar JSON");//entrega2
                System.out.println("0. Salir");
                System.out.print("Opción: ");

                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {

                    case 1:
                        System.out.print("Ruta del CSV: ");
                        File origen = new File(sc.nextLine());

                        int total = csv.importarCSV(origen);
                        System.out.println("Insertados: " + total);
                        break;

                    case 2:
                        System.out.print("ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        Producto p = dao.buscarPorId(id);

                        if (p != null) {
                            System.out.println(p);
                        } else {
                            System.out.println("No encontrado.");
                        }
                        break;

                    case 3:
                        System.out.print("ID: ");
                        int idStock = Integer.parseInt(sc.nextLine());

                        System.out.print("Nuevo stock: ");
                        int stock = Integer.parseInt(sc.nextLine());

                        if (dao.modificarStock(idStock, stock)) {
                            System.out.println("Stock modificado.");
                        } else {
                            System.out.println("No se pudo modificar.");
                        }
                        break;

                    case 4:
                        System.out.print("ID: ");
                        int idBorrar = Integer.parseInt(sc.nextLine());

                        if (dao.borradoLogico(idBorrar)) {
                            System.out.println("Producto eliminado.");
                        } else {
                            System.out.println("No se pudo eliminar.");
                        }
                        break;

                    case 5:
                        System.out.print("Ruta del CSV de salida: ");
                        File destino = new File(sc.nextLine());

                        csv.exportarCSV(destino);
                        System.out.println("CSV exportado.");
                        break;

                    case 6:
                        long cantidad = dao.numeroRegistros();

                        for (int i = 0; i < cantidad; i++) {
                            Producto producto = dao.buscarPorId(i);
                            System.out.println(producto);

                        }
                        break;
                    case 7:
                        List<Producto> productos = new ArrayList<>();

                        long totalProductos = dao.numeroRegistros();

                        for (int i = 0; i < totalProductos; i++) {
                            Producto producto = dao.buscarPorId(i);

                            if (producto != null && producto.isActivo()) {
                                productos.add(producto);
                            }
                        }

                        try {
                            GestorIntercambios.exportarXML(productos);
                        } catch (Exception e) {
                            System.out.println("Error al exportar XML: " + e.getMessage());
                        }

                        break;
                    case 8:
                        // Recuperamos los productos activos del catálogo
                        List<Producto> productosJSON = new ArrayList<>();

                        long totalJSON = dao.numeroRegistros();

                        for (int i = 0; i < totalJSON; i++) {
                            Producto producto = dao.buscarPorId(i);

                            if (producto != null && producto.isActivo()) {
                                productosJSON.add(producto);
                            }
                        }

                        // Exportamos el catálogo a JSON
                        try {
                            GestorIntercambios.exportarJSON(productosJSON);
                        } catch (IOException e) {
                            System.out.println("Error al exportar JSON: " + e.getMessage());
                        }

                        break;

                    case 9:

                        System.out.print("Ruta del archivo XML: ");
                        String rutaXML = sc.nextLine();

                        try {
                            GestorIntercambios.importarXMLSAX(rutaXML);
                        } catch (Exception e) {
                            System.out.println("Error al importar XML: " + e.getMessage());
                        }

                        break;

                    case 0:
                        System.out.println("Fin del programa.");
                        break;

                    default:
                        System.out.println("Opción incorrecta.");
                }

            } while (opcion != 0);

        } catch (IOException e) {
            System.out.println("Error de archivos: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Debes introducir un número válido.");
        }

        sc.close();
    }

}
