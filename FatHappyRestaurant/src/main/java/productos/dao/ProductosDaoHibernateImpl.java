package productos.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import auxiliares.singleton.ClasesEstaticas;
import caja.modelo.Operacion;
import pool.HibernateUtil;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Extra;
import productos.modelo.Hamburguesa;
import productos.modelo.Ingrediente;
import productos.modelo.Postre;
import productos.modelo.Producto;
import productos.modelo.ProductoVendido;
import productos.modelo.Salsa;
import restaurante.modelo.Restaurante;

/**
 * Esta modelo utilizará Hibernate para acceder a los datos de todos los
 * productos de la base de datos.
 * 
 * @author Alejandro Perellón López
 *
 */
public class ProductosDaoHibernateImpl implements ProductosDAO {

	private int idRestaurante;

	// Crear el logger
	static Logger logger = LogManager.getLogger(ProductosDaoHibernateImpl.class);

	@Override
	public List<Producto> listarProductos() {
		// Iniciamos una lista de productos vacia
		List<Producto> listaProductos = new ArrayList<>();
		logger.debug("Se ha iniciado una nueva lista de productos vacia");

		// Abrimos la sesion de productos
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado la session de hibernate");

			// Hacemos una query que cargue los productos de la base de datos
			listaProductos = session.createQuery("FROM Producto", Producto.class).list();
			logger.debug("Se han cargado {} productos en la lista", listaProductos.size());

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al listar los productos", e);
		}

		logger.debug("Se retorna la lista con {} productos", listaProductos.size());
		return listaProductos;
	}

	@Override
	public Bebida obtenerBebida(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto bebida con ID {}", id);

			// Obtenemos el objeto bebida
			Bebida bebida = session.find(Bebida.class, id);
			logger.debug("Se han cargado los datos en el objeto bebida con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (bebida == null) {
				logger.error("El objeto bebida con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si la bebida no esta activa retornamos nulo
			if (!bebida.isProductoActivo()) {
				logger.warn("El objeto bebida {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto bebida con ID {}, retornando el objeto", id);
			return bebida;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto bebida con ID " + id, e);
		}
		return null;
	}

	@Override
	public Complemento obtenerComplemento(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto complemento con ID {}", id);

			// Obtenemos el objeto complemento
			Complemento complemento = session.find(Complemento.class, id);
			logger.debug("Se han cargado los datos en el objeto complemento con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (complemento == null) {
				logger.error("El objeto complemento con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si la complemento no esta activa retornamos nulo
			if (!complemento.isProductoActivo()) {
				logger.warn("El objeto complemento {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto complemento con ID {}, retornando el objeto", id);
			return complemento;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto complemento con ID " + id, e);
		}
		return null;
	}

	@Override
	public Extra obtenerExtra(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto extra con ID {}", id);

			// Obtenemos el objeto extra
			Extra extra = session.find(Extra.class, id);
			logger.debug("Se han cargado los datos en el objeto extra con id {}", id);

			// Comprobacion de si el objeto existe
			if (extra == null) {
				logger.error("El objeto extra con id {} no existe en la base de datos", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto extra con ID {}, retornando el objeto", id);
			return extra;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto extra con ID " + id, e);
		}
		return null;
	}

	@Override
	public Hamburguesa obtenerHamburguesa(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto hamburguesa con ID {}", id);

			// Obtenemos el objeto hamburguesa
			Hamburguesa hamburguesa = session.find(Hamburguesa.class, id);
			logger.debug("Se han cargado los datos en el objeto hamburguesa con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (hamburguesa == null) {
				logger.error("El objeto hamburguesa con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si la hamburguesa no esta activa retornamos nulo
			if (!hamburguesa.isProductoActivo()) {
				logger.warn("El objeto hamburguesa {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto hamburguesa con ID {}, retornando el objeto", id);
			return hamburguesa;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto hamburguesa con ID " + id, e);
		}
		return null;
	}

	@Override
	public Postre obtenerPostre(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto postre con ID {}", id);

			// Obtenemos el objeto postre
			Postre postre = session.find(Postre.class, id);
			logger.debug("Se han cargado los datos en el objeto postre con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (postre == null) {
				logger.error("El objeto postre con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si la postre no esta activa retornamos nulo
			if (!postre.isProductoActivo()) {
				logger.warn("El objeto postre {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto postre con ID {}, retornando el objeto", id);
			return postre;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto postre con ID " + id, e);
		}
		return null;
	}

	@Override
	public Producto obtenerProducto(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto producto con ID {}", id);

			// Obtenemos el objeto producto
			Producto producto = session.find(Producto.class, id);
			logger.debug("Se han cargado los datos en el objeto producto con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (producto == null) {
				logger.error("El objeto producto con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si el producto no esta activo retornamos nulo
			if (!producto.isProductoActivo()) {
				logger.info("El objeto producto {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			// Comprobamos que el producto este en stock en el restaurante
			logger.debug("Se ha encontrado el objeto producto con ID {}, comprobando si esta en stock", id);
			boolean enStock = session.createNativeQuery(
					"SELECT activo FROM stock_restaurante WHERE id_restaurante = :idRest and id_producto = :idProd",
					boolean.class).setParameter("idRest", ClasesEstaticas.getRestaurante().getIdRestaurante())
					.setParameter("idProd", producto.getCodigo()).uniqueResult();
			if (enStock) {
				logger.info("El objeto producto con ID {} se ha encontrado y esta en stock", id);
				return producto;
			}

			return null;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto producto con ID " + id, e);
		}
		return null;
	}

	@Override
	public Salsa obtenerSalsa(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto salsa con ID {}", id);

			// Obtenemos el objeto salsa
			Salsa salsa = session.find(Salsa.class, id);
			logger.debug("Se han cargado los datos en el objeto salsa con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (salsa == null) {
				logger.error("El objeto salsa con id {} no existe en la base de datos", id);
				return null;
			} else
			// Si la salsa no esta activa retornamos nulo
			if (!salsa.isProductoActivo()) {
				logger.warn("El objeto salsa {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto salsa con ID {}, retornando el objeto", id);
			return salsa;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto salsa con ID " + id, e);
		}
		return null;
	}

	@Override
	public Ingrediente obtenerIngrediente(int id) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener el objeto ingrediente con ID {}", id);

			// Obtenemos el objeto ingrediente
			Ingrediente ingrediente = session.find(Ingrediente.class, id);
			logger.debug("Se han cargado los datos en el objeto ingrediente con id {}", id);

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (ingrediente == null) {
				logger.error("El objeto ingrediente con id {} no existe en la base de datos", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto ingrediente con ID {}, retornando el objeto", id);
			return ingrediente;

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al obtener el objeto ingrediente con ID " + id, e);
		}
		return null;
	}

	@Override
	public boolean consultarStockProducto(Producto pro) {
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug(
					"Se ha iniciado una sesion de hibernate para obtener el objeto stock_restaurante con numero de restaurante {} y numero producto {}",
					idRestaurante, pro.getCodigo());

			// Obtenemos el objeto stock
			Boolean esActivo = session.createNativeQuery(
					"Select activo FROM stock_restaurante WHERE id_restaurante = :idRest and id_producto = :idProd",
					Boolean.class).setParameter("idRest", idRestaurante).setParameter("idProd", pro.getCodigo())
					.uniqueResult();
			logger.debug(
					"Se han cargado los datos en el objeto stock_restaurante con numero de restaurante {} y numero producto {}",
					idRestaurante, pro.getCodigo());

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (esActivo == null) {
				logger.error(
						"El objeto stock_restaurante con numero de restaurante {} y numero producto {} NO existe en la base de datos",
						idRestaurante, pro.getCodigo());
				pro.setStockDisponible(false);
				return false;
			}

			logger.debug(
					"Se ha encontrado el objeto stock_restaurante con numero de restaurante {} y numero producto {}",
					idRestaurante, pro.getCodigo());
			pro.setStockDisponible(esActivo);
			return esActivo;

		} catch (Exception e) {
			logger.error(
					"Ha ocurrido un error al obtener el objeto stock_restaurante con numero de restaurante {} y numero producto {}",
					idRestaurante, pro.getCodigo());
		}
		return false;

	}

	@Override
	public List<Producto> obtenerListaProductosCategoria(String categoria) {
		List<Producto> lista = new ArrayList<>();
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener la lista de productos con categoria {}",
					categoria);

			// Obtenemos el objeto
			lista = session
					.createQuery("FROM Producto WHERE categoria = :categoria ORDER BY tipoProducto, nombreProducto ASC",
							Producto.class)
					.setParameter("categoria", categoria).getResultList();

			logger.info("Se ha cargado la lista de productos de la categoria {}, con un total de {} productos",
					categoria, lista.size());

			// Comprobacion de si el objeto existe, y en caso de existir si esta activo o no
			if (lista.size() == 0) {
				logger.error("La lista de productos de la categoria {}, ha retornado 0 objetos", categoria);
			} else {
				logger.info("La lista de productos de la categoria {}, ha retornado {} objetos", categoria,
						lista.size());
			}
		} catch (Exception e) {
			logger.error(
					"Ha ocurrido un error al obtener el listado de productos pertenecientes a la categoria {} del metodo dao",
					categoria);
		}
		return lista;
	}

	@Override
	public LocalDateTime obtenerUltimaActualizacionProductos() {
		LocalDateTime tiempo = null;
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para obtener la fecha de actualizacion del stock");

			// Ejecutamos la consulta y verificamos el resultado
			Object resultado = session.createNativeQuery(
					"SELECT MAX(fecha_actualizacion) FROM actualizaciones_stock WHERE id_restaurante = :idRestaurante",
					Object.class).setParameter("idRestaurante", ClasesEstaticas.getRestaurante().getIdRestaurante())
					.getSingleResult();

			// Si el resultado no es nulo
			if (resultado != null) {
				// Convertimos la fecha y hora en localdatetime
				tiempo = ((java.sql.Timestamp) resultado).toLocalDateTime();
				logger.info("Se ha cargado la ultima fecha de actualizacion: {}", tiempo);
			} else {
				logger.warn("No existe ninguna fecha de actualizacion registrada para el restaurante");
			}

		} catch (Exception e) {
			logger.error("Error al obtener la fecha de la ultima actualizacion del stock", e);
		}

		return tiempo;
	}

	@Override
	public boolean insertarProductoVendido(ProductoVendido productoVendido) {
		// Comprobamos si el objeto pedido es nulo
		if (productoVendido == null) {
			logger.warn("El objeto producto es nulo, no se puede persistir");
			return false;
		}

		// Realizamos la persistencia del objeto pedido
		Transaction transaction = null;
		logger.debug("Se ha iniciado la transaccion");
		// Iniciamos una sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			logger.debug("Se ha iniciado una sesion de hibernate para insertar el objeto productoVendido con ID {}",
					productoVendido.getId());

			// Iniciamos la transaccion
			transaction = session.beginTransaction();
			logger.debug("Se ha asignado la sesion a la transaccion");

			// Insertamos el restaurante en el pedidoVendido
			productoVendido.setRestaurante(obtenerRestaurante(session));
			// Insertamos el producto el pedidoVendido
			productoVendido.setProducto(obtenerProducto(session, productoVendido.getProducto()));
			// Insertamos el la operacion
			productoVendido.setOperacion(obtenerOperacion(session, productoVendido.getOperacion()));

			// Persistimos el pedido
			session.persist(productoVendido);
			// Confirmamos la persistencia
			transaction.commit();
			logger.debug("Se ha persistido el objeto productoVendido id {}", productoVendido.getId());
			return true;
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al insertar el productoVendido con ID " + productoVendido.getId(), e);
			if (transaction != null && transaction.isActive()) {
				logger.warn("Se va a realizar un rollback de la base de datos");
				transaction.rollback();
			}
		}
		return false;
	}

	/**
	 * Metodo que recupera la {@link Operacion} de hibernate
	 * 
	 * @param operacion es la {@link Operacion} de hibernate
	 * @param session   es la sesion de hibernate
	 * @return {@link Operacion} obtenida de hibernate
	 */
	private Operacion obtenerOperacion(Session session, Operacion operacion) {
		logger.info("Se esta recuperando el la operacion con ID {}", operacion.getId());
		return session.find(operacion.getClass(), operacion.getId());
	}

	/**
	 * Metodo que recupera de la sesion de hibernate el producto
	 * 
	 * @param producto es el {@link Producto} de hibernate
	 * @param session  es la sesion de hibernate
	 * @return {@link Producto} obtenido de hibernate
	 */
	private Producto obtenerProducto(Session session, Producto pro) {
		logger.info("Se esta recuperando el producto con ID {}", pro.getCodigo());
		return session.find(Producto.class, pro.getCodigo());
	}

	/**
	 * Metodo que recupera de la sesion de hibernate el restaurante
	 * 
	 * @param sesion es la sesion de hibernate
	 * @return {@link Restaurante} obtenido de hibernate
	 */
	private Restaurante obtenerRestaurante(Session sesion) {
		Restaurante res = ClasesEstaticas.getRestaurante();
		logger.info("Se esta recuperando el restaurante con ID {}", res.getIdRestaurante());
		return sesion.find(Restaurante.class, res.getIdRestaurante());
	}

	@Override
	public boolean modificarStockProducto(Producto producto, boolean nuevoEstado) {
		// Abrimos sesión con try-with-resources para que se cierre sola
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			Transaction transaction = session.beginTransaction(); // 1. comienza la tx
			try {
				// 2. consulta SQL nativa → createNativeMutationQuery
				MutationQuery sentencia = session
						.createNativeMutationQuery("UPDATE stock_restaurante " + "SET activo = :estado "
								+ "WHERE id_restaurante = :restaurante " + "  AND id_producto    = :producto");

				sentencia.setParameter("estado", nuevoEstado)
						.setParameter("restaurante", ClasesEstaticas.getRestaurante().getIdRestaurante())
						.setParameter("producto", producto.getCodigo());

				sentencia.executeUpdate();
				transaction.commit();
				return true;

			} catch (Exception ex) { // ⇒ cualquier error → rollback
				transaction.rollback();
				logger.error("Error actualizando stock del producto {}", producto.getCodigo(), ex);
				return false;
			}
		}
	}

}
