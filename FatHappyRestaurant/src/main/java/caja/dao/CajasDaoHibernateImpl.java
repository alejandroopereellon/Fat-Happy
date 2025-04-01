package caja.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import caja.modelo.Caja;
import pool.HibernateUtil;

public class CajasDaoHibernateImpl implements CajasDao {

	// Crear el logger
	static Logger logger = LogManager.getLogger(Caja.class);

	@Override
	public boolean insertarCaja(Caja caja) {
		// Comprobamos si el objeto caja es nulo
		if (caja == null) {
			logger.warn("El objeto caja es null, no se puede persistir");
			return false;
		}

		// Realizamos la persistencia del objeto caja
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para insertar el objeto caja con ID {}", caja.getId());

			// Iniciamos la transaccion
			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");
			// Persistimos la caja
			session.persist(caja);
			// Confirmamos la persistencia
			transaction.commit();
			logger.debug("Se persistido el objeto caja id {}", caja.getId());
			return true;
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto caja con ID " + caja.getId(), e);
			if (transaction != null && transaction.isActive()) {
				logger.warn("Se va a realizar un rollback de la base de datos");
				transaction.rollback();
			}
		}
		return false;
	}

}
