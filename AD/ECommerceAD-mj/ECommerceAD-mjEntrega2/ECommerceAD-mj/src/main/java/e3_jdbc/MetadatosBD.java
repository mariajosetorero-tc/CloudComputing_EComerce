
package e3_jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MetadatosBD {
public static void mostrarMetadatos() {

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return;
            }

            DatabaseMetaData datos = conexion.getMetaData();

            System.out.println("Nombre de la base de datos: "
                    + datos.getDatabaseProductName());

            System.out.println("Versión: "
                    + datos.getDatabaseProductVersion());

            System.out.println("Usuario: "
                    + datos.getUserName());

            System.out.println("Tablas de la base de datos:");

            ResultSet tablas = datos.getTables(
                    "ecomarket_db", null, "%", new String[]{"TABLE"});

            while (tablas.next()) {
                System.out.println(tablas.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
