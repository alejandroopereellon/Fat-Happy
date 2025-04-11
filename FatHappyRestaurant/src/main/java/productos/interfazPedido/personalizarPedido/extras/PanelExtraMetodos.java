package productos.interfazPedido.personalizarPedido.extras;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import productos.modelo.Extra;
import productos.util.ModificarExtras;

/**
 * Clase que controla el {@link PanelExtra} y realiza todas las acciones
 * necesarias en la clase
 * 
 * @author Alejandro Perellón López
 */
public class PanelExtraMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelExtraMetodos.class);
	// Establecemos la interfaz
	private PanelExtra interfaz;

	public PanelExtraMetodos(PanelExtra interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Metodo que inicia el panel de extras con toda la configuracion del producto
	 */
	public void iniciarPanelExtras() {
		// Establecemos los datos del extra
		establecerDatosExtra(interfaz.getExtra());

		// Establecemos los botones si llega a uno de los limites
		controlarBotonesCantidad();
	}

	/**
	 * Este metodo establece los datos del extra en el panel, entre los datos
	 * incluye la cantidad de producto adicional, un selector de cantidades
	 * adicionales y el coste adicional del producto
	 */
	public void establecerDatosExtra(Extra extra) {
		// Establecemos la imagen del producto
		ImageIcon icon = new ImageIcon(extra.getRutaImagen());
		interfaz.getImagenExtra().setIcon(icon);
		logger.debug("Se ha establecido la imagen del extra {}", extra);
		// Establecemos el nombre del ingrediente
		interfaz.getNombreExtra().setText(extra.getNombreExtra());
		logger.debug("Se ha establecido el nombre del extra {}", extra);
		// Establecemos la cantidad de producto
		actualizarCantidadExtras();
	}

	/**
	 * Este metodo controla los botones de aumentar o disminuir la cantidad de
	 * producto, en caso de que se iguale la cantidad maxima el boton de aumentar de
	 * inabilita, en caso de que la cantidad sea 0 la cantidad de producto se
	 * desactiva el boton de disminuir
	 */
	private void controlarBotonesCantidad() {
		// Establecemos el extra de la interfa
		Extra extra = interfaz.getExtra();

		// Activamos los botones de aumentar y disminuir
		interfaz.getAumentarCantidad().setEnabled(true);
		interfaz.getDisminuirCantidad().setEnabled(true);
		logger.debug("Se han habilitado las dos casillas del extra {}", extra);

		// Comprobamos si se iguala la cantidad maxima y se desHabilita el boton
		if (extra.getCantidadExtra() >= extra.getMaximoExtras()) {
			interfaz.getAumentarCantidad().setEnabled(false);
			logger.debug("Se ha deshabilitado la casilla de aumentar cantidad del extra {}", extra);
		}
		// Comprobamos si la cantidad de extra son
		if (extra.getCantidadExtra() <= 0) {
			interfaz.getDisminuirCantidad().setEnabled(false);
			logger.debug("Se ha deshabilitado la casilla de disminuir cantidad del extra {}", extra);
		}

		// Actualizamos el numero de extras
		actualizarCantidadExtras();

		// Actualizamos el importe de extras
		actualizarImporteExtras();
	}

	/**
	 * Metodo que actualiza el coste adicional de los extras
	 */
	private void actualizarImporteExtras() {
		interfaz.getCosteAdicional().setText("+ " + new ModificarExtras(interfaz.getExtra()).costeExtras());

	}

	/**
	 * Metodo que actualiza en el panel la cantidad de extras
	 */
	private void actualizarCantidadExtras() {
		String cantidad = String.valueOf(interfaz.getExtra().getCantidadExtra());
		// Establecemos la cantidad de extra
		interfaz.getCantidadProducto().setText(cantidad);
		logger.debug("Se ha actualizado la cantidad de extra {}", interfaz.getExtra());
	}

	/**
	 * Metodo que aumenta la cantidad de producto
	 */
	protected void aumentarCantidad() {
		// Aumentamos la cantidad
		new ModificarExtras(interfaz.getExtra()).aumentarCantidad();
		// Actualizamos los botones
		controlarBotonesCantidad();
		logger.debug("Se ha aumentado la cantidad de extra {}", interfaz.getExtra());
	}

	/**
	 * Metodo que disminuye la cantidad de producto
	 */
	protected void disminuirCantidad() {
		// Aumentamos la cantidad
		new ModificarExtras(interfaz.getExtra()).disminuirCantidad();
		// Actualizamos los botones
		controlarBotonesCantidad();
		logger.debug("Se ha disminuido la cantidad de extra {}", interfaz.getExtra());
	}
}
