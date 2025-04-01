package pedido.util;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pedido.modelo.NumeroPedido;
import pool.HibernateUtil;
import restaurante.modelo.Restaurante;

public class ObtenerNumeroPedido {

    private static final Logger logger = LogManager.getLogger(ObtenerNumeroPedido.class);

    public int obtenerYReservarNumeroPedido(int idRestaurante) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.debug("Se inicia sesion para obtener el numero de pedido para el restaurante {}", idRestaurante);

            transaction = session.beginTransaction();

            Integer ultimo = session.createQuery(
                "SELECT MAX(n.numeroPedido) FROM NumeroPedido n " +
                "WHERE n.fecha = CURRENT_DATE AND n.restaurante.id = :idRestaurante",
                Integer.class)
                .setParameter("idRestaurante", idRestaurante)
                .uniqueResult();

            int siguiente;
            if (ultimo != null) {
                siguiente = ultimo + 1;
                logger.debug("Ultimo numero de pedido encontrado: {}. Siguiente sera: {}", ultimo, siguiente);
            } else {
                siguiente = 1;
                logger.debug("No se encontraron pedidos previos hoy. Se asignara el numero 1");
            }

            NumeroPedido nuevo = new NumeroPedido();
            nuevo.setNumeroPedido(siguiente);
            nuevo.setFecha(LocalDate.now());
            nuevo.setRestaurante(session.get(Restaurante.class, idRestaurante));

            session.persist(nuevo);
            transaction.commit();

            logger.info("Se ha reservado el numero de pedido {} para el restaurante {}", siguiente, idRestaurante);
            return siguiente;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                logger.warn("Se ha hecho rollback por un error al reservar el numero de pedido para el restaurante {}", idRestaurante);
            }
            logger.error("Error al obtener y reservar el numero de pedido", e);
            throw e;
        }
    }
}
