package pedido.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.modelo.Pedido;
import productos.dao.ProductosDaoGlobal;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;

public class ModificarOrdenPedido {
	private Pedido pedido;

	// Crear el logger
	static Logger logger = LogManager.getLogger(ModificarOrdenPedido.class);

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
		Producto objetoProducto = ProductosDaoGlobal.get().obtenerProducto(pro.getCodigo());

		if (objetoProducto != null) {
			pedido.getOrden().getListaProductos().add(objetoProducto);
			new CalcularImporte(pedido).aplicarDescuentoPedido(pedido.getDescuento());
			logger.info("Se ha añadido el producto con id {} en la lista", objetoProducto.getCodigo());
		}

	}

	/**
	 * Metodo que añade un menu a la orden de pedido y realiza todos los calculos
	 * necesarios para actualizar el pedido
	 * 
	 * @param menu producto que se va a añadir a pedido
	 */
	public void anadirProducto(MenuPedido menu) {
		pedido.getOrden().getListaMenus().add(menu);
		new CalcularImporte(pedido).aplicarDescuentoPedido(pedido.getDescuento());
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
		actualizarImporte();
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
		actualizarImporte();
	}

	/**
	 * Metodo que llamar al metodo principal de actualizar el importe de la clase
	 * {@link CalcularImporte}
	 */
	public void actualizarImporte() {
		new CalcularImporte(pedido).aplicarDescuentoPedido(pedido.getDescuento());
	}

}
