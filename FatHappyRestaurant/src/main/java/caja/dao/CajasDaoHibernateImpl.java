package caja.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import caja.modelo.Caja;
import caja.modelo.CajaDatos;
import caja.modelo.Operacion;
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
			logger.debug("Se ha iniciado una sesion de hibernate para insertar el objeto caja");

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
					"FROM Caja WHERE restaurante.id = :idRest and numeroCaja = :numCaja and momentoCierre IS NULL",
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

	@Override
	public boolean insertarOperacion(Operacion operacion) {
		// Comprobamos si el objeto operacion es nulo
		if (operacion == null) {
			logger.warn("El objeto operacion es null, no se puede persistir");
			return false;
		}

		// Realizamos la persistencia del objeto operacion
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para insertar el objeto operacion con ID {}",
					operacion.getId());

			// Iniciamos la transaccion
			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");
			// Persistimos la caja
			session.persist(operacion);
			// Confirmamos la persistencia
			transaction.commit();
			logger.debug("Se persistido el objeto operacion id {}", operacion.getId());
			return true;
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto operacion con ID " + operacion.getId(), e);
			if (transaction != null && transaction.isActive()) {
				logger.warn("Se va a realizar un rollback de la base de datos");
				transaction.rollback();
			}
		}
		return false;
	}

	@Override
	public List<Operacion> listarOperaciones() {
		// Creamos una lista de operaciones
		List<Operacion> listaOperaciones = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se inicia sesion de hibernate obtener la lista de operaciones");

			// Obtenemos la lista de operaciones a la que pertenece la caja
			listaOperaciones = session.createQuery("FROM Operacion WHERE caja.id = :idCaja", Operacion.class)
					.setParameter("idCaja", CajaDatos.get().getId()).getResultList();

			logger.info("Se ha obtenido la lista de operaciones");
		} catch (Exception e) {
			logger.error("Error al obtener la lista de operaciones", e);
		}
		return listaOperaciones;
	}

	@Override
	public boolean cerrarCaja() {
		Caja caja = CajaDatos.get();
		Caja cajaActualizada = null;
		// Realizamos la persistencia del objeto operacion
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para actualizar el objeto caja con ID {}",
					caja.getId());

			// Iniciamos la transaccion de actualizacion
			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");

			// Obtenemos el objeto Caja de la base de datos
			cajaActualizada = session.get(caja.getClass(), caja.getId());

			// Comprobamos si la caja existe
			if (cajaActualizada != null) {
				logger.info("Se ha recuperado la caja con ID {}", cajaActualizada.getId());
				// Actualizamos el importe final
				cajaActualizada.setImporteFinal(caja.getImporteFinal());
				logger.info("Se ha insertado el total de operaciones");
				// Actualizamos la hora de cierre
				cajaActualizada.setMomentoCierre(caja.getMomentoCierre());
				logger.info("Se ha insertado el momento de cierre {}", LocalDateTime.now());
			} else {
				logger.error("No se ha podido encontrar la caja en el DAO, no se realizaran cambios");
				return false;
			}

			// Confirmamos la actualizacion
			transaction.commit();
			logger.debug("Se ha actualizado el objeto caja ID {}", caja.getId());
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
