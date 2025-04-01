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
	 * Metodo que restaura el importe original del pedido incluidos los descuentos
	 * aplicados en el pedido
	 */
	public void restaurarImporte() {
		aplicarDescuentoPedido(pedido.getDescuento());
		logger.info("Se ha restablecido el importe original del pedido con el descuento del {} aplicado",
				pedido.getDescuento());
	}

	/**
	 * Metodo que calcula el importe original del pedido, para ello realiza un
	 * recorrido de todos los productos sumando el precio total de todos los
	 * prodcutos
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

	/**
	 * Método que realiza un descuento sobre el precio original del pedido. El
	 * descuento se calcula y se aplica directamente al pedido.
	 *
	 * @param porcentaje Porcentaje de descuento a aplicar (ej. 10 = 10%)
	 */
	public void aplicarDescuentoPedido(int porcentaje) {
		BigDecimal porcentajeBD = new BigDecimal(porcentaje);
		BigDecimal factor = BigDecimal.ONE
				.subtract(porcentajeBD.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
		BigDecimal importeOriginal = obtenerImporteOriginal();
		BigDecimal importeDescontado = importeOriginal.multiply(factor).setScale(2, RoundingMode.HALF_UP);

		pedido.setDescuento(porcentaje);
		pedido.setImporteTotal(importeDescontado);

		logger.info("Se aplicó un descuento de {}% al pedido. Importe con descuento: {}", porcentaje,
				importeDescontado);
	}

}
