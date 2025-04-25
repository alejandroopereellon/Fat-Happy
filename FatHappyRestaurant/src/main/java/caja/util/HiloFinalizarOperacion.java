package caja.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import caja.modelo.Operacion;
import pedido.dao.PedidoDao;
import pedido.dao.PedidoDaoHibernateImpl;
import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;
import pedido.util.AlmacenarOrdenPedidoJson;
import pedido.util.CalcularImporte;
import pedido.util.PedidoBuilder;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import productos.modelo.ProductoVendido;
import productos.util.ProcesarProductoVendido;

/**
 * Clase que maneja un hilo que realiza las operaciones necesarias tras el cobro
 * de un {@link Pedido}, inserta los datos del {@link Pedido} en base de datos y
 * en json, almacena la {@link Operacion} en la base de datos y cada
 * {@link ProductoVendido} en la base de datos
 * 
 * @author Alejandro Perellón López
 */
public class HiloFinalizarOperacion extends Thread {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PedidoBuilder.class);
	// Creamos el objeto pedido
	private final Pedido pedido;

	// Creamos el dao
	PedidoDao dao = new PedidoDaoHibernateImpl();

	public HiloFinalizarOperacion(Pedido pedido) {
		this.pedido = pedido;
	}

	public void run() {
		// Restablecemos el importe original del pedido
		new CalcularImporte(pedido).obtenerImporteDescuento();

		// Insertamos la operacion en la base de datos
		dao.insertarPedido(pedido);

		// Almacenamos el pedido en fichero json
		new AlmacenarOrdenPedidoJson(pedido).almacenarOrdenPedido();

		// Insertamos la operacion en la base de datos
		OperacionBuilder operacion = new OperacionBuilder();
		operacion.GenerarOperacion(pedido, "cobro", "efectivo");

		// Almacenamos los productos vendidos en la base de datos
		almacenarProductosVendidos(operacion.getOperacion());
	}

	/**
	 * Metodo que recorre todo el pedido y
	 * 
	 * @param operacion es la {@link Operacion} de la que se extrae el
	 *                  {@link Pedido} y a traves de la {@link OrdenPedido} se
	 *                  almacena cada producto individualmente
	 */
	private void almacenarProductosVendidos(Operacion operacion) {
		OrdenPedido orden = operacion.getPedido().getOrden();
		Boolean bandera = true;

		// Almacenamos los productos sueltos
		for (Producto pro : orden.getListaProductos()) {
			bandera = new ProcesarProductoVendido(pro, operacion).procesar();
		}
		logger.debug("Se han procesado todos los productos sueltos la bandera es: {}", bandera);

		// Almacenamos los productos de los menus
		for (MenuPedido menu : orden.getListaMenus()) {
			// Almacenamos la hamburguesa
			bandera = new ProcesarProductoVendido(menu.getHamburguesa(), operacion).procesar();
			// Almacenamos el complementos
			bandera = new ProcesarProductoVendido(menu.getComplemento(), operacion).procesar();
			// Almacenamos la bebida
			bandera = new ProcesarProductoVendido(menu.getBebida(), operacion).procesar();
			// Almacenamos el postre si existe
			if (menu.getPostre() != null) {
				bandera = new ProcesarProductoVendido(menu.getPostre(), operacion).procesar();
			}
		}
		logger.debug("Se han procesado todos los productos de los menus la bandera es: {}", bandera);

		if (bandera) {
			logger.info("Todos los productos se han procesado correctamente");
		} else {
			logger.info("Hay productos que NO se han procesado correctamente");
		}
	}
}
