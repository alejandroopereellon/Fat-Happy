package productos.util.hiloActualizacionProductos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;
import productos.modelo.ListaProductos;
import productos.modelo.Producto;

/**
 * Metodo que constuye el objeto {@link ListaProductos} realizando varias
 * consultas DAO para rellenar cada lista por sus categorias
 * 
 * @author Alejandro Perellón López
 */
public class ListaProductosBuilder {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ListaProductosBuilder.class);
	// Establecemos el dao de productos
	private ProductosDAO dao = ProductosDaoGlobal.get();
	// Recuperamos el objeto ListaProductos
	private ListaProductos lista = new ListaProductos();

	public void crearListaProductos() {
		// Establecemos la lista de hamburguesas
		lista.setListaHamburguesas(obtenerComprobarStockProducto("hamburguesa"));
		logger.info("Se ha cargado la lista de hamburguesas");
		// Establecemos la lista de complementos
		lista.setListaComplementos(obtenerComprobarStockProducto("complemento"));
		logger.info("Se ha cargado la lista de complementos");
		// Establecemos la lista de bebidas
		lista.setListaBebidas(obtenerComprobarStockProducto("bebida"));
		logger.info("Se ha cargado la lista de bebidas");
		// Establecemos la lista de postres
		lista.setListaPostres(obtenerComprobarStockProducto("postre"));
		logger.info("Se ha cargado la lista de postres");

		// Almacenamos la lista de productos en el singleton
		ClasesEstaticas.setListaProductos(lista);
		logger.info("Se ha cargado en singleton las listas de productos");
	}

	private List<Producto> obtenerComprobarStockProducto(String categoria) {
		// Obtenemos la lista de productos de la categoria
		List<Producto> lista = dao.obtenerListaProductosCategoria(categoria);
		logger.info("Se ha cargado la lista de productos de la categoria {}, se va a comprobar su stock", categoria);
		/*
		 * Repasamos la lista, y comprobamos si el objeto tiene stock o no en el
		 * restaurante
		 */
		for (Producto producto : lista) {
			producto.setStockDisponible(dao.consultarStockProducto(producto));
		}
		logger.info("Se ha comprobado el stock de cada producto de la categoria {}", categoria);
		return lista;
	}

}
