package productos.interfazPedido.personalizarPedido.ingredientes;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import productos.modelo.Ingrediente;

public class PanelIngredienteMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelIngredienteMetodos.class);
	// Establecemos la interfaz
	private PanelIngrediente interfaz;
	// Extablecemos el ingrediente
	private Ingrediente ingrediente;

	protected PanelIngredienteMetodos(PanelIngrediente interfaz, Ingrediente ingrediente) {
		this.interfaz = interfaz;
		this.ingrediente = interfaz.getIngrediente();
	}

	/**
	 * Este metodo establece los datos del ingrediente en el panel, entre ellos el
	 * estado del ingrediente (activo o no), imagen y nombre
	 */
	protected void iniciarPanelIngrediente(Boolean posicion) {
		// Establecemos la imagen del producto
		ImageIcon icon = new ImageIcon(ingrediente.getRutaIngredientes());
		interfaz.getImagenIngrediente().setIcon(icon);
		logger.debug("Se ha establecido la imagen del ingrediente {}", ingrediente);
		// Establecemos el nombre del ingrediente
		interfaz.getNombreIngrediente().setText(ingrediente.getNombreIngrediente());
		logger.debug("Se ha establecido el nombre del ingrediente {}", ingrediente);
		// Establecemos si esta activo o no
		actualizarEstadoProducto();
		// Establecemos el color de la casilla
		establecerColorCasilla(posicion);

	}

	/**
	 * Metodo que al pulsar el boton modifica de manera inversa el estado del
	 * ingrediente
	 */
	protected void cambiarEstadoIngrediente() {
		// Cambiamos el estado del ingrediente
		cambiarEstadoActivo();
		// Cambiamos la imagen del ingrediente
		establecerImagenIngrediente(null);
	}

	/**
	 * Para que el producto sea visiblemente mas bonito segun si la posicion del
	 * producto es par o impar se va a modificar el color de fondo del producto
	 */
	private void establecerColorCasilla(boolean esPar) {
		// Si es numero impar se cambia de color
		if (esPar) {
			interfaz.setBackground(Color.decode("#a9dfbf"));
			logger.debug("Se ha modificado el estado de la casilla del ingrediente {}", ingrediente);
		}
	}

	/**
	 * Este metodo establece el icono que indica si esta activo o inactivo
	 */
	private void actualizarEstadoProducto() {
		if (ingrediente.isActivo()) {
			establecerImagenIngrediente(productoActivo());
			logger.debug("Se ha establecido la imagen de activo en el ingrediente {}", ingrediente);
		} else {
			establecerImagenIngrediente(productoInactivo());
			logger.debug("Se ha establecido la imagen de inactivo en el ingrediente {}", ingrediente);
		}
	}

	/**
	 * Metodo que modifica si un ingrediente esta activo o no, en caso de estar
	 * activo se desactiva y si esta desactivado se activa
	 */
	private void cambiarEstadoActivo() {
		// Cambiamos el estado del ingrediente
		if (ingrediente.isActivo()) {
			// Desactivamos el ingrediente
			ingrediente.setActivo(false);

			// Modificamos la imagen del ingrediente
			establecerImagenIngrediente(productoInactivo());

			logger.debug("Se ha establecido en inactivo el ingrediente {}", ingrediente);
		} else {
			// Activamos el ingrediente
			ingrediente.setActivo(true);

			// Modificamos la imagen del ingrediente
			establecerImagenIngrediente(productoActivo());

			logger.debug("Se ha establecido en activo el ingrediente {}", ingrediente);
		}
	}

	/**
	 * Metodo que establece dentro de la interfaz el {@link Icon}o
	 * 
	 * @param icon es el {@link Icon}o que se va a añadir
	 */
	private void establecerImagenIngrediente(Icon icon) {
		if (ingrediente.isActivo()) {

		}
		interfaz.getCuadroEstadoIngrediente().setIcon(icon);
	}

	/**
	 * Metodo que retorna la imagen del producto activo
	 * 
	 * @return {@link Icon}o del producto activo
	 */
	private Icon productoInactivo() {
		return new ImageIcon(getClass().getClassLoader().getResource("graficaPersonalizarProducto/activo.png"));

	}

	/**
	 * Metodo que retorna la imagen del producto inactivo
	 * 
	 * @return {@link Icon}o del producto inactivo
	 */
	private Icon productoActivo() {
		return new ImageIcon(getClass().getClassLoader().getResource("graficaPersonalizarProducto/inactivo.png"));
	}
}
