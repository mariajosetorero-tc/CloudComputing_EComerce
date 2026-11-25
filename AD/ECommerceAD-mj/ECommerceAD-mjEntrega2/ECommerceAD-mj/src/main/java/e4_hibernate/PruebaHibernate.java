
package e4_hibernate;



import jakarta.persistence.EntityManager;

public class PruebaHibernate {

    public static void main(String[] args) {

        EntityManager em = null;

        try {
            em = HibernateUtil.getEntityManager();

            System.out.println("Conexión con Hibernate realizada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al conectar con Hibernate: " + e.getMessage());

        } finally {
            if (em != null) {
                em.close();
            }

            HibernateUtil.cerrar();
        }
    }
}
