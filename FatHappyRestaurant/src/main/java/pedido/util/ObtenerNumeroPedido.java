package pedido.util;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pedido.modelo.NumeroPedido;
import pool.HibernateUtil;
import restaurante.modelo.Restaurante;

/**
 * Clase encargada de obtener y reservar un numero de pedido unico por dia para
 * un restaurante especifico.
 *
 * Al obtener el numero, este se registra automaticamente en la base de datos
 * para asegurar que no pueda ser utilizado por otro cliente del sistema.
 */
public class ObtenerNumeroPedido {

	private static final Logger logger = LogManager.getLogger(ObtenerNumeroPedido.class);

	/**
	 * Metodo que obtiene el siguiente numero de pedido para el restaurante dado,
	 * registrandolo inmediatamente en la base de datos para evitar duplicados.
	 *
	 * @param idRestaurante ID del restaurante para el cual se va a generar el
	 *                      numero
	 * @return El numero de pedido generado y reservado
	 */
	public int obtenerYReservarNumeroPedido(int idRestaurante) {
		Transaction transaction = null;

		// Iniciamos una sesion para consultar e insertar en la base de datos
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se inicia sesion para obtener el numero de pedido para el restaurante {}", idRestaurante);

			// Comenzamos la transaccion
			transaction = session.beginTransaction();

			// Consulta para obtener el ultimo numero de pedido registrado hoy para el
			// restaurante
			Integer ultimo = session
					.createQuery(
							"SELECT MAX(n.numeroPedido) FROM NumeroPedido n "
									+ "WHERE n.fecha = CURRENT_DATE AND n.restaurante.id = :idRestaurante",
							Integer.class)
					.setParameter("idRestaurante", idRestaurante).uniqueResult();

			// Si no hay resultados previos, se empieza desde 1
			int siguiente;
			if (ultimo != null) {
				siguiente = ultimo + 1;
				logger.debug("Ultimo numero de pedido encontrado: {}. Siguiente sera: {}", ultimo, siguiente);
			} else {
				siguiente = 1;
				logger.debug("No se encontraron pedidos previos hoy. Se asignara el numero 1");
			}

			// Creamos y persistimos el nuevo numero de pedido para reservarlo
			NumeroPedido nuevo = new NumeroPedido();
			nuevo.setNumeroPedido(siguiente);
			nuevo.setFecha(LocalDate.now());
			nuevo.setRestaurante(session.get(Restaurante.class, idRestaurante));

			session.persist(nuevo);

			// Confirmamos la transaccion
			transaction.commit();

			logger.info("Se ha reservado el numero de pedido {} para el restaurante {}", siguiente, idRestaurante);
			return siguiente;
		} catch (Exception e) {
			// En caso de error, revertimos la transaccion
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
				logger.warn("Se ha hecho rollback por un error al reservar el numero de pedido para el restaurante {}",
						idRestaurante);
			}
			logger.error("Error al obtener y reservar el numero de pedido", e);
			throw e;
		}
	}
}
