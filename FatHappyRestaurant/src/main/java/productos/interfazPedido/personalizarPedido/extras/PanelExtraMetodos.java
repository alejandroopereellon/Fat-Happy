package productos.interfazPedido.personalizarPedido.extras;

import java.awt.Color;

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
	 * 
	 * @param esPar indica si el panel esta en posicion par o impar
	 */
	protected void iniciarPanelExtras(boolean esPar) {
		// Establecemos los datos del extra
		establecerDatosExtra(interfaz.getExtra());

		// Establecer datos del extra
		establecerColorCasilla(esPar);

		// Establecemos los botones si llega a uno de los limites
		controlarBotonesCantidad();
	}

	/**
	 * Para que el producto sea visiblemente mas bonito segun si la posicion del
	 * producto es par o impar se va a modificar el color de fondo del producto
	 * 
	 * @param esPar TRUE la casilla se cambia de color || FALSE el color de la
	 *              casilla no cambia
	 */
	private void establecerColorCasilla(boolean esPar) {
		// Si es numero impar se cambia de color
		if (esPar) {
			interfaz.setBackground(Color.decode("#a9dfbf"));
		}
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
		// Establecemos el nombre del ingrediente
		interfaz.getNombreExtra().setText(extra.getRutaImagen());
		// Establecemos la cantidad de producto
		interfaz.getCantidadProducto().setText(String.valueOf(extra.getCantidadExtra()));
	}

	/**
	 * Este metodo controla los botones de aumentar o disminuir la cantidad de
	 * producto, en caso de que se iguale la cantidad maxima el boton de aumentar de
	 * inabilita, en caso de que la cantidad sea 0 la cantidad de producto se
	 * desactiva el boton de disminuir
	 */
	private void controlarBotonesCantidad() {
		Extra extra = interfaz.getExtra();

		// Activamos los botones de aumentar y disminuir
		interfaz.getAumentarCantidad().setEnabled(true);
		interfaz.getDisminuirCantidad().setEnabled(true);

		// Comprobamos si se iguala la cantidad maxima y se desHabilita el boton
		if (extra.getCantidadExtra() >= extra.getMaximoExtras()) {
			interfaz.getAumentarCantidad().setEnabled(false);
		}
		// Comprobamos si la cantidad de extra son
		if (extra.getCantidadExtra() <= 0) {
			interfaz.getDisminuirCantidad().setEnabled(false);
		}
	}

	/**
	 * Metodo que aumenta la cantidad de producto
	 */
	protected void aumentarCantidad() {
		// Aumentamos la cantidad
		new ModificarExtras(interfaz.getExtra()).aumentarCantidad();
		// Actualizamos los botones
		controlarBotonesCantidad();
	}

	/**
	 * Metodo que disminuye la cantidad de producto
	 */
	protected void disminuirCantidad() {
		// Aumentamos la cantidad
		new ModificarExtras(interfaz.getExtra()).disminuirCantidad();
		// Actualizamos los botones
		controlarBotonesCantidad();
	}
}
