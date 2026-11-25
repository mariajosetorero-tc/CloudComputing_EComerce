package e3_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionProductos {

    public static void insertarProducto(int id, String nombre, double precio, int stock, boolean activo) {

        String sql = "INSERT INTO productos (id, nombre, precio, stock, activo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            sentencia.setString(2, nombre);
            sentencia.setDouble(3, precio);
            sentencia.setInt(4, stock);
            sentencia.setBoolean(5, activo);

            sentencia.executeUpdate();

            System.out.println("Producto insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void mostrarProductos() {

        String sql = "SELECT * FROM productos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                System.out.println(
                        resultado.getInt("id") + " - "
                        + resultado.getString("nombre") + " - "
                        + resultado.getDouble("precio") + " € - "
                        + resultado.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void modificarStock(int id, int nuevoStock) {

        String sql = "UPDATE productos SET stock = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, nuevoStock);
            sentencia.setInt(2, id);

            sentencia.executeUpdate();

            System.out.println("Stock modificado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void eliminarProducto(int id) {

        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            sentencia.executeUpdate();

            System.out.println("Producto eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
