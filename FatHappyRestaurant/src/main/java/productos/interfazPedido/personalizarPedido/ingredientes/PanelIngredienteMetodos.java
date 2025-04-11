package productos.interfazPedido.personalizarPedido.ingredientes;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import productos.modelo.Ingrediente;

public class PanelIngredienteMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelIngredienteMetodos.class);
	// Establecemos la interfaz
	private PanelIngrediente interfaz;

	public PanelIngredienteMetodos(PanelIngrediente interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Este metodo establece los datos del ingrediente en el panel, entre ellos el
	 * estado del ingrediente (activo o no), imagen y nombre
	 */
	public void iniciarPanelIngrediente() {
		// Establecemos el ingrediente de la interfaz
		Ingrediente ingrediente = interfaz.getIngrediente();

		// Establecemos la imagen del producto
		ImageIcon icon = new ImageIcon(ConfiguracionInicial.get().getDirectorioLocal() + File.separator + "imagenes"
				+ File.separator + "ImagenIngredientes" + File.separator + "Ingredientes64" + File.separator
				+ ingrediente.getRutaIngredientes());
		interfaz.getImagenIngrediente().setIcon(icon);
		logger.debug("Se ha establecido la imagen del ingrediente {}", ingrediente);
		// Establecemos el nombre del ingrediente
		interfaz.getNombreIngrediente().setText(ingrediente.getNombreIngrediente());
		logger.debug("Se ha establecido el nombre del ingrediente {}", ingrediente);
		// Establecemos si esta activo o no
		actualizarEstadoProducto();
	}

	/**
	 * Metodo que al pulsar el boton modifica de manera inversa el estado del
	 * ingrediente
	 */
	protected void cambiarEstadoIngrediente() {
		// Cambiamos el estado del ingrediente
		cambiarEstadoActivo();
	}

	/**
	 * Este metodo establece el icono que indica si esta activo o inactivo
	 */
	private void actualizarEstadoProducto() {
		if (interfaz.getIngrediente().isActivo()) {
			establecerImagenIngrediente(productoActivo());
			logger.debug("Se ha establecido la imagen de activo en el ingrediente {}", interfaz.getIngrediente());
		} else {
			establecerImagenIngrediente(productoInactivo());
			logger.debug("Se ha establecido la imagen de inactivo en el ingrediente {}", interfaz.getIngrediente());
		}
	}

	/**
	 * Metodo que modifica si un ingrediente esta activo o no, en caso de estar
	 * activo se desactiva y si esta desactivado se activa
	 */
	private void cambiarEstadoActivo() {
		// Establecemos el ingrediente de la interfaz
		Ingrediente ingrediente = interfaz.getIngrediente();

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
		interfaz.getCuadroEstadoIngrediente().setIcon(icon);
	}

	/**
	 * Metodo que retorna la imagen del producto activo
	 * 
	 * @return {@link Icon}o del producto activo
	 */
	private Icon productoInactivo() {
		return new ImageIcon(getClass().getClassLoader().getResource("graficaPersonalizarProducto/inactivo.png"));

	}

	/**
	 * Metodo que retorna la imagen del producto inactivo
	 * 
	 * @return {@link Icon}o del producto inactivo
	 */
	private Icon productoActivo() {
		return new ImageIcon(getClass().getClassLoader().getResource("graficaPersonalizarProducto/activo.png"));
	}
}
