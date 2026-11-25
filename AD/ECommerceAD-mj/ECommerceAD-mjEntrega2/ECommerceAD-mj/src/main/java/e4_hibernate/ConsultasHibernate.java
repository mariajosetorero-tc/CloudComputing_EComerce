package e4_hibernate;

import jakarta.persistence.EntityManager;
import java.util.List;

public class ConsultasHibernate {

    public static void main(String[] args) {

        EntityManager em = HibernateUtil.getEntityManager();

        try {

            String consulta = "SELECT c.nombre, SUM(d.cantidad), "
                    + "SUM(d.cantidad * d.precioUnitario) "
                    + "FROM DetallePedido d "
                    + "JOIN d.producto p "
                    + "JOIN p.categoria c "
                    + "GROUP BY c.nombre";

            List<Object[]> resultados = em.createQuery(consulta, Object[].class)
                    .getResultList();

            System.out.println("ESTADISTICAS DE VENTAS POR CATEGORIA");

            for (Object[] fila : resultados) {
                System.out.println("Categoria: " + fila[0]);
                System.out.println("Unidades vendidas: " + fila[1]);
                System.out.println("Total vendido: " + fila[2]);
                System.out.println("----------------------");
            }

        } finally {
            em.close();
            HibernateUtil.cerrar();
        }
    }
}
