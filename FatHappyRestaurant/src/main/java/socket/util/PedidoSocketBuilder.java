package socket.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import socket.modelo.PedidoSocket;

/**
 * Clase encarga de crear el pedido que se va a enviar al servidor para que lo
 * mande al resto de usuarios
 * 
 * @author Alejandro Perellón López
 */
public class PedidoSocketBuilder {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PedidoSocketBuilder.class);

	public PedidoSocket crearPedido(Pedido pedido) {
		PedidoSocket pedidoSocket = new PedidoSocket(pedido.getNumeroPedido(), procesarEntregaPedido(pedido));

		// Anadimos todos los productos al pedidoSocket
		anadirProductosPedido(pedidoSocket.getListaProductos(), pedido.getOrden());

		logger.debug("Se va a retornar el pedido listo para el servidor");
		return pedidoSocket;

	}

	/**
	 * Metodo que se encarga de recuperar todos los producto del {@link Pedido} y
	 * anadirlos individualmente al {@link PedidoSocket} para enviar al servidor
	 * 
	 * @param listaProductos es la lista de {@link Productos}s a la que se le van a
	 *                       anadir los {@link Producto}s
	 * @param ordenPedido    es la {@link OrdenPedido} de la que se van a extraer
	 *                       los {@link Producto}s
	 */
	private void anadirProductosPedido(List<Producto> listaProductos, OrdenPedido orden) {
		// Almacenamos los productos sueltos
		for (Producto pro : orden.getListaProductos()) {
			listaProductos.add(pro);
		}
		logger.debug("Se han procesado todos los productos sueltos");

		// Almacenamos los productos de los menus
		for (MenuPedido menu : orden.getListaMenus()) {
			// Almacenamos la hamburguesa
			listaProductos.add(menu.getHamburguesa());
			// Almacenamos el complementos
			listaProductos.add(menu.getComplemento());
			// Almacenamos la bebida
			listaProductos.add(menu.getBebida());
			// Almacenamos el postre si existe
			if (menu.getPostre() != null) {
				listaProductos.add(menu.getPostre());
			}
		}
		logger.debug("Se han procesado todos los productos de los menus");
	}

	/**
	 * Metodo que segun el tipo de entrega de los productos se va a informar
	 * 
	 * -1 LLEVAR 0 TOMAR SIN MESA !0 LLevar a mesa al numer indicado
	 */
	private int procesarEntregaPedido(Pedido pedido) {
		if (pedido.getOrden().isLlevar()) {
			return -1;
		} else {
			return pedido.getOrden().getNumeroMesa();
		}
	}
}
