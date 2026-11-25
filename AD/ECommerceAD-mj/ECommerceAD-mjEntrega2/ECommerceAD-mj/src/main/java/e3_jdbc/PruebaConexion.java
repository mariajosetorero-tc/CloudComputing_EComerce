
package e3_jdbc;

import java.sql.Connection;
import java.sql.SQLException;


public class PruebaConexion {
public static void main(String[] args) {
        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
                MetadatosBD.mostrarMetadatos();
                System.out.println("muestra de metadatos");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
