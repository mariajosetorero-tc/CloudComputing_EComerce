
package e1_ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CSVManager {
      private ProductoRAFDAO dao;

    public CSVManager(ProductoRAFDAO dao) {
        this.dao = dao;
    }

    public int importarCSV(File archivoCSV) throws IOException {//metodo para leer el fichero linea por liena y guardar los productos

        int contador = 0;

        try (BufferedReader br =
                new BufferedReader(new FileReader(archivoCSV))) {

            String linea = br.readLine(); // Saltar cabecera

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",", -1);

                if (datos.length != 4) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    double precio = Double.parseDouble(datos[2].trim());
                    int stock = Integer.parseInt(datos[3].trim());

                    if (id < 1 || precio < 0 || stock < 0) {
                        continue;
                    }

                    Producto p = new Producto(
                            id, nombre, precio, stock, true
                    );

                    if (dao.insertar(p)) {
                        contador++;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Línea incorrecta: " + linea);
                }
            }
        }

        return contador;
    }

    public void exportarCSV(File destino) throws IOException {

        File carpeta = destino.getParentFile();

        if (carpeta != null) {
            carpeta.mkdirs();
        }

        try (BufferedWriter bw =
                new BufferedWriter(new FileWriter(destino))) {

            bw.write("id,nombre,precio,stock");
            bw.newLine();

            long total = dao.numeroRegistros();

            for (int i = 0; i < total; i++) {

                Producto p = dao.leerPorIndice(i);

                if (p != null && p.isActivo()) {
                    bw.write(p.getId() + ","
                            + p.getNombre() + ","
                            + p.getPrecio() + ","
                            + p.getStock());

                    bw.newLine();
                }
            }
        }
    }
    
}
