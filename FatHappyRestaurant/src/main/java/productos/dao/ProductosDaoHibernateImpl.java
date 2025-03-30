package productos.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import pool.HibernateUtil;
import productos.modelos.Bebida;
import productos.modelos.Complemento;
import productos.modelos.Extra;
import productos.modelos.Hamburguesa;
import productos.modelos.Ingrediente;
import productos.modelos.Postre;
import productos.modelos.Producto;
import productos.modelos.Salsa;

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
			// Si la producto no esta activa retornamos nulo
			if (!producto.isProductoActivo()) {
				logger.warn("El objeto producto {} esta inactivo, se va a retornar nulo", id);
				return null;
			}

			logger.debug("Se ha encontrado el objeto producto con ID {}, retornando el objeto", id);
			return producto;

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
					Boolean.class).setParameter("idRest", idRestaurante).setParameter("idProd", pro.getCodigo()).uniqueResult();
			logger.debug(
					"Se han cargado los datos en el objetostock_restaurante con numero de restaurante {} y numero producto {}",
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

}
