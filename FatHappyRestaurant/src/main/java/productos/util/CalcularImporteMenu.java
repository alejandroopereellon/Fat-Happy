package productos.util;

import java.math.BigDecimal;

import productos.modelo.MenuPedido;

/**
 * Metodo que recalcula el importe total del menu, calcula el precio de la
 * hamburguesa + unos intereses y adicionalmente si existe postre en el menu el
 * precio del menu aumentará en el precio del postre
 * 
 * @author Alejandro Perellón López
 */
public class CalcularImporteMenu {

	private MenuPedido menu;

	public CalcularImporteMenu(MenuPedido menu) {
		this.menu = menu;
	}

	/**
	 * Metodo que recalcula el importe total
	 */
	public void calcularImporte() {
		// Calculamos el precio de la hamburguesa mas el coste de intereses
		menu.setPrecioMenu(menu.getHamburguesa().getPrecioVenta().multiply(new BigDecimal("1.65")));
		// En caso de existir un postre en el menu se va a actualizar el precio
		if (menu.getPostre() != null) {
			menu.getPrecioMenu().add(menu.getPostre().getPrecioVenta());
		}
	}

}
