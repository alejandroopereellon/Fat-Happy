package pedido.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.modelo.Pedido;
import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;
import productos.interfazPedido.SeleccionProductos.ProductoSeleccionado.PanelProductoSeleccionado;
import productos.interfazPedido.SeleccionProductos.ProductoSeleccionado.PanelProductoSeleccionadoMetodos;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;

public class ModificarOrdenPedido {
	private Pedido pedido;

	// Crear el logger
	static Logger logger = LogManager.getLogger(ModificarOrdenPedido.class);
	// Establecemos el dao
	private ProductosDAO dao = ProductosDaoGlobal.get();

	public ModificarOrdenPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Metodo que añade un producto a la orden de pedido y realiza todos los
	 * calculos necesarios para actualizar el pedido
	 * 
	 * @param pro producto que se va a añadir a pedido
	 */
	public void anadirProducto(Producto pro) {
		// Obtenemos el producto del dao y generamos un nuevo objeto
		Producto objetoProducto = dao.obtenerProducto(pro.getCodigo());

		if (objetoProducto != null) {
			// Anadimos el producto en la orden de pedido
			pedido.getOrden().getListaProductos().add(objetoProducto);
			// Actualizamos el importe del pedido
			pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
			logger.info("Se ha añadido el producto con id {} en la lista", objetoProducto.getCodigo());

			// Anadimos el producto pedido en la casilla del producto
			PanelProductoSeleccionado panel =  new PanelProductoSeleccionado(objetoProducto);
			new PanelProductoSeleccionadoMetodos(panel).iniciarPanel();
		}

	}

	/**
	 * Metodo que añade un menu a la orden de pedido y realiza todos los calculos
	 * necesarios para actualizar el pedido
	 * 
	 * @param menu producto que se va a añadir a pedido
	 */
	public void anadirMenu(MenuPedido menu) {
		pedido.getOrden().getListaMenus().add(menu);
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
		logger.info("Se ha añadido el menu en la lista");
	}

	/**
	 * Metodo que elimina de la orden de pedido el producto que se situa en la
	 * posicion
	 * 
	 * @param posicion es la posicion del producto que se va a eliminar
	 */
	public void retirarProducto(int posicion) {
		List<Producto> lista = pedido.getOrden().getListaProductos();
		// Si el numero de elementos es menor o igual que la posicion se elimina el
		// objeto
		if (posicion >= 0 && posicion < lista.size()) {
			lista.remove(posicion);
			logger.info("Se ha retirado el producto de la posicion {}", posicion);
		} else {
			logger.error("La posicion es mayor que el numero de elementos en la lista", lista);
		}
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
	}

	/**
	 * Metodo que elimina de la orden de pedido el menu que se situa en la posicion
	 * indicada
	 * 
	 * @param posicion
	 */
	public void retirarMenu(int posicion) {
		List<MenuPedido> lista = pedido.getOrden().getListaMenus();
		// Si el numero de elementos es menor o igual que la posicion se elimina el
		// objeto
		if (posicion >= 0 && posicion < lista.size()) {
			lista.remove(posicion);
			logger.info("Se ha retirado el Menu de la posicion {}", posicion);
		} else {
			logger.error("La posicion es mayor que el numero de elementos en la lista", lista);
		}
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
	}

}