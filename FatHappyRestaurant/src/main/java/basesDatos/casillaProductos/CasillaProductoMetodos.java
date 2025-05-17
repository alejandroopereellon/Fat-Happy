package basesDatos.casillaProductos;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import productos.dao.ProductosDaoGlobal;
import productos.modelo.Producto;

public class CasillaProductoMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(CasillaProductoMetodos.class);

	private final CasillaProducto casilla;

	private final Producto producto;

	public CasillaProductoMetodos(CasillaProducto casilla, Producto producto) {
		this.casilla = casilla;
		this.producto = producto;
	}

	public void iniciarConfiguracion() {
		// Mostramos la imagen del producto
		casilla.getImagenProducto().setIcon(new ImageIcon(obtenerRutaImagen(producto.getImagenProducto64())));
		// Mostramos el nombre del producto
		casilla.getNombreProducto().setText(producto.getNombreProducto());
		// Mostramos el estado de la casilla
		casilla.getEstadoProducto().setIcon(actualizarEstadoProducto());

		logger.debug("Se ha iniciado la casilla del producto {}", producto);
	}

	/**
	 * Metodo que devuelve un {@link URL} con la ruta de la imagen. Si no se
	 * encuentra en el sistema de archivos, se devuelve una imagen por defecto desde
	 * recursos.
	 *
	 * @param rutaImagen Ruta relativa a la carpeta de imagenes locales
	 * @return {@link URL} con la ruta de la imagen o imagen por defecto
	 */
	private URL obtenerRutaImagen(String rutaImagen) {
		// Ruta local principal
		String rutaLocalImagenes = ConfiguracionInicial.get().getDirectorioLocal() + File.separator + "imagenes"
				+ File.separator;

		// Ruta completa del archivo en el sistema
		File imagen = new File(rutaLocalImagenes + rutaImagen);

		// Si el archivo existe en disco, devolver su URL
		if (imagen.exists()) {
			try {
				logger.debug("La ruta obtenida existe en los archivos: {}", imagen.getAbsolutePath());
				return imagen.toURI().toURL();
			} catch (MalformedURLException e) {
				logger.error("Error al convertir la ruta del archivo a URL", e);
			}
		}

		// Si no existe, devolver imagen por defecto desde los recursos
		logger.warn("No se ha encontrado la imagen en la ruta local: {}. Cargando imagen por defecto.",
				imagen.getAbsolutePath());
		return getClass().getResource("/graficaErrorProducto/imageNotFound64.png");
	}

	/**
	 * Este metodo establece el icono que indica si esta activo o inactivo
	 */
	private Icon actualizarEstadoProducto() {
		if (producto.isStockDisponible()) {
			logger.debug("Se ha establecido la imagen de activo en el producto");
			return productoActivo();
		} else {
			logger.debug("Se ha establecido la imagen de inactivo en el producto");
			return productoInactivo();
		}
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

	/**
	 * Metodo que se encarga de modificar el estado de un producto
	 */
	protected void invertirEstadoProducto() {
		// Modificamos el estado del stock
		producto.setStockDisponible(!producto.isStockDisponible());
		
		// Actualizamos la imagen de la actualizacion
		casilla.getEstadoProducto().setIcon(actualizarEstadoProducto());

		// Modificamos el estado del stock en la base de datos
		ProductosDaoGlobal.get().modificarStockProducto(producto, producto.isStockDisponible());

	}
}
