package productos.util;

import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.metodosBigDecimal.OperacionesBigDecimal;
import productos.modelo.MenuPedido;

/**
 * Metodo que recalcula el importe total del menu, calcula el precio de la
 * hamburguesa + unos intereses y adicionalmente si existe postre en el menu el
 * precio del menu aumentará en el precio del postre
 * 
 * @author Alejandro Perellón López
 */
public class CalcularImporteMenu {
	// Crear el logger
	static Logger logger = LogManager.getLogger(CalcularImporteMenu.class);

	private MenuPedido menu;

	public CalcularImporteMenu(MenuPedido menu) {
		this.menu = menu;
	}

	/**
	 * Metodo que recalcula el importe total
	 */
	public BigDecimal calcularImporte() {
		BigDecimal importe = BigDecimal.ZERO.setScale(2);

		// Calculamos el precio de la hamburguesa mas el coste de intereses
		new OperacionesBigDecimal().sumar(importe,
				menu.getHamburguesa().getPrecioVenta().multiply(new BigDecimal("1.65")));
		// En caso de existir un postre en el menu se va a actualizar el precio
		if (menu.getPostre() != null) {
			new OperacionesBigDecimal().sumar(importe, menu.getPostre().getPrecioVenta());
		}
		return importe;
	}

}
