package caja.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import caja.modelo.Caja;
import pool.HibernateUtil;

public class CajasDaoHibernateImpl implements CajasDao {

	// Crear el logger
	static Logger logger = LogManager.getLogger(CajasDaoHibernateImpl.class);

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

	@Override
	public Caja recuperarCaja() {
		Caja nuevaCaja;
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para recuperar el objeto caja");
			// Obtenemos la caja
			nuevaCaja = session.createQuery(
					"FROM Caja WHERE idRestaurante = :idRest and numeroCaja = :numCaja and fecha_hora_final IS NULL",
					Caja.class).setParameter("idRest", ConfiguracionInicial.get().getCodigoRestaurante())
					.setParameter("numCaja", ConfiguracionInicial.get().getNumeroCaja()).uniqueResult();

			// Si la caja es correcta se retorna
			if (nuevaCaja != null) {
				logger.info("Se ha recuperado la caja con id {} correctamente", nuevaCaja.getId());
				return nuevaCaja;
			}

			// Notificamos que la caja es nula y no existe
			logger.info("La caja solicitada no existe en la base de datos");
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto caja", e);
		}
		return null;
	}

	@Override
	public int obtenerSiguienteNumeroSesion() {
		int numeroSesion = 0;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se inicia sesion de hibernate para contar sesiones de caja activas hoy");

			Long count = session
					.createQuery("SELECT COUNT(c) FROM Caja c "
							+ "WHERE c.idRestaurante = :idRest AND c.numeroCaja = :numCaja "
							+ "AND DATE(c.fechaHoraInicio) = CURRENT_DATE", Long.class)
					.setParameter("idRest", ConfiguracionInicial.get().getCodigoRestaurante())
					.setParameter("numCaja", ConfiguracionInicial.get().getNumeroCaja()).uniqueResult();

			if (count != null) {
				// Modificamos el long por un int y le sumamos uno
				numeroSesion = count.intValue() + 1;
			} else {
				// Retornamos la sesion 1 al no haber mas anteriores
				numeroSesion = 1;
			}

			logger.info("Se ha obtenido el siguiente numero de sesion: {}", numeroSesion);
		} catch (Exception e) {
			logger.error("Error al obtener el numero de sesion", e);
		}

		return numeroSesion;
	}

}
