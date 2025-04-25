package pedido.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.metodosBigDecimal.OperacionesBigDecimal;
import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import productos.util.CalcularImporteMenu;

/**
 * Metodo que realiza modificaciones en el importe del pedido.
 * 
 * Calcular el importe total del pedido, realizar descuentos etc
 */
public class CalcularImporte {
	// Crear el logger
	static Logger logger = LogManager.getLogger(CalcularImporte.class);

	private Pedido pedido;

	// Constructor
	public CalcularImporte(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Metodo que calcula el importe original del pedido, para ello realiza un
	 * recorrido de todos los productos sumando el precio total de todos los
	 * componentes del pedido
	 * 
	 * @return {@link BigDecimal} con el importe original del pedido
	 */
	public BigDecimal obtenerImporteOriginal() {
		BigDecimal importe = new BigDecimal("0.00");
		logger.debug("Establecemos el importe en 0");

		// Recorremos toda la lista de menus y sumamos la cantidad al importe total
		for (MenuPedido menu : pedido.getOrden().getListaMenus()) {
			// Sumamos el precio del menu
			importe = new OperacionesBigDecimal().sumar(importe, menu.getPrecioMenu());
			// Sumamos el precio del postre si lo tiene
			if (menu.getPostre() != null) {
				importe = new OperacionesBigDecimal().sumar(importe, menu.getPostre().getPrecioVenta());
			}
			logger.debug("Se ha sumado el importe del menu {}. Total parcial: {}", menu, importe);
		}
		logger.debug("Se ha recorrido todos los elementos de menu");

		// Recorremos toda la lista de productos y sumamos la cantidad al importe total
		for (Producto producto : pedido.getOrden().getListaProductos()) {
			importe = new OperacionesBigDecimal().sumar(importe, producto.getPrecioVenta());
			logger.debug("Se ha calculado sumado el importe del producto {}. Total parcial: {}", producto, importe);
		}

		logger.info("Se ha recorrido todos los productos, importe total del pedido: {}", importe);
		return importe.setScale(2, RoundingMode.HALF_UP);

	}

	/**
	 * Método que calcula el importe total de los {@link Producto}s de una
	 * {@link OrdenPedido}
	 * 
	 * Este metodo tiene en cuenta si el {@link Producto} esta promocionado o tiene
	 * opcion de descuentos disponible.
	 * 
	 * En caso de los menus realiza el calculo el precio del menu y realiza el
	 * descuento
	 * 
	 * @return {@link BigDecimal} con el importe actualizado con el descuento
	 *         aplicado
	 */
	public BigDecimal obtenerImporteDescuento() {
		BigDecimal importe = new BigDecimal("0.00");
		logger.debug("Establecemos el importe en 0");

		// Calculamos el importe de todos los productos
		for (Producto pro : pedido.getOrden().getListaProductos()) {
			importe = new OperacionesBigDecimal().sumar(importe, calcularDescuentoProducto(pro));
		}

		// Calculamos el importe de todos los menus
		for (MenuPedido menu : pedido.getOrden().getListaMenus()) {
			importe = new OperacionesBigDecimal().sumar(importe, new CalcularImporteMenu(menu).calcularImporte());
		}

		pedido.setImporteTotal(importe);

		logger.info("Se aplicó un descuento de {}% al pedido. Importe con descuento: {}", pedido.getDescuento(),
				importe);

		return importe.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Metodo que en caso de tener descuento aplicable, se va a restar el importe,
	 * en caso de estar promocionado se va a poner a un precio de 0 Eur
	 * 
	 * @param pro        es el {@link Producto} que se va a retornar
	 * @param porcentaje porcentaje que se va reducir el precio
	 * @return {@link BigDecimal} con el coste del producto con descuento
	 */
	private BigDecimal calcularDescuentoProducto(Producto pro) {
		// Si el producto esta promocionado ponemos importe 0
		if (pro.isProductoPromocionado()) {
			logger.debug("El producto esta promocionado, el coste es de 0");
			return BigDecimal.ZERO.setScale(2);
		}

		// Si el producto admite descuentos calculamos el descuento
		if (pro.isOpcionDescuento()) {
			logger.debug("El productos tiene opcion de descuento y no esta promocionado, se aplicará el descuento");
			return new OperacionesBigDecimal().aplicarDescuento(pro.getPrecioVenta(), pedido.getDescuento());
		}

		// Si el producto no esta promocionado ni se ha aplicado descuento
		logger.info("El producto con ID {} no tiene opcion de descuento ni esta promocionado", pro.getCodigo());
		return pro.getPrecioVenta();
	}

}
