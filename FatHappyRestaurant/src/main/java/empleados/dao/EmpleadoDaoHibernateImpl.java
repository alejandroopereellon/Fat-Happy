/**
 * 
 */
package empleados.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import empleados.modelo.Empleado;
import empleados.modelo.MovimientosEmpleado;
import pool.HibernateUtil;
import restaurante.dao.RestauranteDaoHibernateImpl;

/**
 * 
 */
public class EmpleadoDaoHibernateImpl implements EmpleadosDao {

	private int idRestaurante;

	/**
	 * Este constructor solicita el id del restaurante con el que se va a trabajar
	 * para obtener la informacion de los empleados que pertenecen a esa plantilla
	 * 
	 * @param idRestaurante
	 */
	public EmpleadoDaoHibernateImpl(int idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	// Crear el logger
	static Logger logger = LogManager.getLogger(RestauranteDaoHibernateImpl.class);

	@Override
	public Empleado obtenerEmpleado(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto empleado con ID {}", id);

			// Obtenemos el objeto empleado
			Empleado empleado = session
					.createQuery("FROM Empleado WHERE idRestaurante = :idRest and idRestauranteEmpleado = :idEmp",
							Empleado.class)
					.setParameter("idRest", idRestaurante).setParameter("idEmp", id).uniqueResult();
			logger.debug("Se han cargado los datos en el objeto empleado con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (empleado == null) {
				logger.error("El objeto empleado con id {} no existe en la base de datos", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto empleado con ID {}, retornando el objeto", id);
			return empleado;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto empleado con ID " + id, e);
		}
		return null;
	}

	@Override
	public boolean comprobarEmpleadoExiste(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto empleado con ID {}", id);

			// Obtenemos el objeto empleado
			Empleado empleado = session
					.createQuery("FROM Empleado WHERE idRestaurante = :idRest and idRestauranteEmpleado = :idEmp",
							Empleado.class)
					.setParameter("idRest", idRestaurante).setParameter("idEmp", id).uniqueResult();
			logger.debug("Se han cargado los datos en el objeto empleado con id {}", id);

			// Comprobacion de si el objeto existe
			if (empleado != null) {
				logger.debug("Se ha encontrado el objeto empleado con ID {}, retornando el objeto", id);
				return true;
			}

			// Si no se ha retornado TRUE indica un error con el empleado y retorna false
			logger.error("El objeto empleado con id {} no existe en la base de datos", id);

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto empleado con ID " + id, e);
		}
		return false;
	}

	@Override
	public boolean pedirAutorizacionEmpleado(int id, int nivelPermisoMinimo, String descripcionAutorizacion) {
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto empleado con ID {}", id);

			// Obtenemos el objeto empleado
			Empleado empleado = session
					.createQuery("FROM Empleado WHERE idRestaurante = :idRest and idRestauranteEmpleado = :idEmp",
							Empleado.class)
					.setParameter("idRest", idRestaurante).setParameter("idEmp", id).uniqueResult();
			logger.debug("Se han cargado los datos en el objeto empleado con id {}", id);

			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");
			// Comprobacion de si el objeto existe
			if (empleado != null) {
				logger.debug("Se ha encontrado el objeto empleado con ID {}", id);
				// Comprobamos si el empleado tiene los pemisos necesarios
				if (empleado.getPermisos() >= nivelPermisoMinimo) {
					logger.debug("El objeto empleado con ID {} tiene permisos de acceso", id);
					session.persist(new MovimientosEmpleado(empleado, descripcionAutorizacion,
							"Empleado tiene permisos", true));
					return true;
				} else {
					logger.debug("El objeto empleado con ID {} no tiene permisos de acceso", id);
					session.persist(new MovimientosEmpleado(empleado, descripcionAutorizacion,
							"Empleado no tiene permisos", false));
				}
			} else {
				session.persist(
						new MovimientosEmpleado(empleado, descripcionAutorizacion, "Empleado no existente", false));
				logger.error("El objeto empleado con id {} no existe en la base de datos", id);
			}

			transaction.commit();
			logger.debug("Se hecho commit de la informacion");

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto empleado con ID " + id, e);
			if (transaction != null && transaction.isActive()) {
				logger.warn("Se va a realizar un rollback de la base de datos");
				transaction.rollback();
			}
		}
		return false;
	}

}
