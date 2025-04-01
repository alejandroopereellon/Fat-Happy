package pedido.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pedido.modelo.Pedido;
import pool.HibernateUtil;

public class PedidoDaoHibernateImpl implements PedidoDao {

	// Crear el logger
	static Logger logger = LogManager.getLogger(PedidoDaoHibernateImpl.class);

	@Override
	public boolean insertarPedido(Pedido pedido) {
		// Comprobamos si el objeto pedido es nulo
		if (pedido == null) {
			logger.warn("El objeto pedido es null, no se puede persistir");
			return false;
		}

		// Realizamos la persistencia del objeto pedido
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para insertar el objeto pedido con ID {}",
					pedido.getId());

			// Iniciamos la transaccion
			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");
			// Persistimos el pedido
			session.persist(pedido);
			// Confirmamos la persistencia
			transaction.commit();
			logger.debug("Se persistido el objeto pedido id {}", pedido.getId());
			return true;
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto pedido con ID " + pedido.getId(), e);
			if (transaction != null && transaction.isActive()) {
				logger.warn("Se va a realizar un rollback de la base de datos");
				transaction.rollback();
			}
		}
		return false;
	}

}
