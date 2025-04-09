package productos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import productos.interfazPedido.personalizarPedido.extras.PanelExtraMetodos;
import productos.modelo.Extra;

public class ModificarExtras {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelExtraMetodos.class);
	// Establecemos el extra
	private Extra extra;

	public ModificarExtras(Extra extra) {
		this.extra = extra;
	}

	/**
	 * Metodo que aumenta la cantidad de extras
	 */
	public void aumentarCantidad() {
		// Si la cantidad de extra es menor que el maximo de extras
		if (extra.getCantidadExtra() < extra.getMaximoExtras()) {
			extra.setCantidadExtra(extra.getCantidadExtra() + 1);
			logger.debug("Se ha aumentado la cantidad de extra");
		}
	}

	/**
	 * Metodo que disminuye la cantidad de extras
	 */
	public void disminuirCantidad() {
		// Si la cantidad de extra es mayor o igual 0
		if (extra.getCantidadExtra() > 0) {
			extra.setCantidadExtra(extra.getCantidadExtra() - 1);
			logger.debug("Se ha disminuido la cantidad de extra");
		}
	}
}
