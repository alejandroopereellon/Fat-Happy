package restaurante.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import pool.HibernateUtil;
import restaurante.modelo.Restaurante;

public class RestauranteDaoHibernateImpl implements RestauranteDao {

	// Crear el logger
	static Logger logger = LogManager.getLogger(RestauranteDaoHibernateImpl.class);

	@Override
	public Restaurante obtenerRestaurante(int id) {

		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto restaurante con ID {}", id);

			// Obtenemos el objeto restaurante
			Restaurante restaurante = session.find(Restaurante.class, id);
			logger.debug("Se han cargado los datos en el objeto restaurante con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (restaurante == null) {
				logger.error("El objeto restaurante con id {} no existe en la base de datos", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto restaurante con ID {}, retornando el objeto", id);
			return restaurante;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto restaurante con ID " + id, e);
		}
		return null;
	}
}
