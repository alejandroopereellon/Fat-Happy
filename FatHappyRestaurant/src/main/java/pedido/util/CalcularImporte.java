package pedido.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;

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
			importe = sumarCantidades(importe, menu.getPrecioMenu());
			logger.debug("Se ha sumado el importe del menu {}. Total parcial: {}", menu, importe);
		}
		logger.debug("Se ha recorrido todos los elementos de menu");

		// Recorremos toda la lista de productos y sumamos la cantidad al importe total
		for (Producto producto : pedido.getOrden().getListaProductos()) {
			importe = sumarCantidades(importe, producto.getPrecioVenta());
			logger.debug("Se ha calculado sumado el importe del producto {}. Total parcial: {}", producto, importe);
		}

		logger.info("Se ha recorrido todos los productos, importe total del pedido: {}", importe);
		return importe;

	}

	/**
	 * Método que realiza un descuento sobre el precio original del pedido. El
	 * descuento se calcula y se aplica directamente al pedido.
	 * 
	 * @return {@link BigDecimal} con el importe actualizado con el descuento
	 *         aplicado
	 */
	public BigDecimal obtenerImporteDescuento() {
		BigDecimal importe = new BigDecimal("0.00");
		logger.debug("Establecemos el importe en 0");

		// Calculamos el importe de todos los productos
		for (Producto pro : pedido.getOrden().getListaProductos()) {
			importe = sumarCantidades(importe, calcularDescuentoProducto(pro, pedido.getDescuento()));
		}

		// Calculamos el importe de todos los menus
		for (MenuPedido menu : pedido.getOrden().getListaMenus()) {
			importe = sumarCantidades(importe, menu.getPrecioMenu());
		}
		pedido.setImporteTotal(importe);

		logger.info("Se aplicó un descuento de {}% al pedido. Importe con descuento: {}", pedido.getDescuento(),
				importe);
		return importe;
	}

	/**
	 * Metodo que en caso de tener descuento aplicable, se va a restar el importe
	 * 
	 * @param pro        es el {@link Producto} que se va a retornar
	 * @param porcentaje porcentaje que se va reducir el precio
	 * @return {@link BigDecimal} con el coste del producto con descuento
	 */
	private BigDecimal calcularDescuentoProducto(Producto pro, int porcentaje) {
		if (!pro.isOpcionDescuento()) {
			logger.info("El producto con ID {} no tiene opcion de descuento", pro.getCodigo());
			return pro.getPrecioVenta();
		} else {
			BigDecimal factor = BigDecimal.ONE
					.subtract(new BigDecimal(porcentaje).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
			return pro.getPrecioVenta().multiply(factor).setScale(2, RoundingMode.HALF_UP);
		}

	}

	/**
	 * Metodo que suma dos cantidades
	 * 
	 * @param cantidadUno cantidad 1 a la que se le va a añadir la cantidad2
	 * @param cantidadDos cantidad 2 que se va a añadir a la cantidad1
	 * 
	 * @return {@link BigDecimal} con el resultado de la nueva cantidad
	 */
	private BigDecimal sumarCantidades(BigDecimal cantidadUno, BigDecimal cantidadDos) {
		BigDecimal resultado = cantidadUno.add(cantidadDos);
		logger.debug("Se ha sumado {} al importe actual: {}", cantidadDos, resultado);
		return resultado;
	}

}
