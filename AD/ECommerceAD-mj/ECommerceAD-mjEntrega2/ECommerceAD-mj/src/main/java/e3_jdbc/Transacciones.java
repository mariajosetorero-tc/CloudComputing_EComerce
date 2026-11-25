
package e3_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Transacciones {
  public static void realizarPedido(int idCliente, int[] productos, int[] cantidades) {

    Connection conexion = null;

    try {
        conexion = ConexionBD.conectar();

        if (conexion == null) {
            return;
        }

        conexion.setAutoCommit(false);

        String sql = "INSERT INTO pedidos (cliente_id, total) VALUES (?, 0)";
        PreparedStatement sentencia = conexion.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS);

        sentencia.setInt(1, idCliente);
        sentencia.executeUpdate();

        var resultado = sentencia.getGeneratedKeys();
        resultado.next();
        int idPedido = resultado.getInt(1);

        double total = 0;

        for (int i = 0; i < productos.length; i++) {

            String consulta = "SELECT precio, stock FROM productos WHERE id = ?";
            PreparedStatement buscar = conexion.prepareStatement(consulta);
            buscar.setInt(1, productos[i]);

            var datos = buscar.executeQuery();

            if (!datos.next() || datos.getInt("stock") < cantidades[i]) {
                throw new SQLException("Producto no encontrado o stock insuficiente.");
            }

            double precio = datos.getDouble("precio");

            String detalleSQL = "INSERT INTO detalle_pedido "
                    + "(pedido_id, producto_id, cantidad, precio) VALUES (?, ?, ?, ?)";

            PreparedStatement detalle = conexion.prepareStatement(detalleSQL);
            detalle.setInt(1, idPedido);
            detalle.setInt(2, productos[i]);
            detalle.setInt(3, cantidades[i]);
            detalle.setDouble(4, precio);
            detalle.executeUpdate();

            String stockSQL = "UPDATE productos SET stock = stock - ? WHERE id = ?";
            PreparedStatement actualizar = conexion.prepareStatement(stockSQL);
            actualizar.setInt(1, cantidades[i]);
            actualizar.setInt(2, productos[i]);
            actualizar.executeUpdate();

            total += precio * cantidades[i];
        }

        String totalSQL = "UPDATE pedidos SET total = ? WHERE id = ?";
        PreparedStatement actualizarTotal = conexion.prepareStatement(totalSQL);
        actualizarTotal.setDouble(1, total);
        actualizarTotal.setInt(2, idPedido);
        actualizarTotal.executeUpdate();

        conexion.commit();

        System.out.println("Pedido realizado correctamente.");

    } catch (SQLException e) {

        try {
            if (conexion != null) {
                conexion.rollback();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Error en el pedido: " + e.getMessage());

    } finally {
        try {
            if (conexion != null) {
                conexion.setAutoCommit(true);
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
}
