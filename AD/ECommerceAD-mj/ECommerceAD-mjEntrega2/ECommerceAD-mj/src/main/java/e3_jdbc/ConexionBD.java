
package e3_jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public static Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/ecomarket_db",
                    "root",
                    ""
            );

            System.out.println("Conexión realizada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }

        return conexion;
    }
}
