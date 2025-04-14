package productos.interfazPedido.casillaProducto;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.utilidadesGraficas.PanelUtil;
import pedido.modelo.OrdenPedido;
import pedido.util.ModificarOrdenPedido;
import productos.modelo.Producto;

public class CasillaProductoMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(CasillaProductoMetodos.class);

	private CasillaProducto interfaz;

	public CasillaProductoMetodos(CasillaProducto interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Este metodo establece toda la informacion del producto en la casilla, a
	 * traves del objeto {@link Producto} obtiene el nombre, imagen y precio del
	 * producto
	 */
	public void establecerDatosProducto() {
		// Establecemos el producto
		Producto pro = interfaz.getProducto();

		// Establecemos la imagen del producto
		interfaz.getCuadroImagen().setIcon(new ImageIcon(obtenerRutaImagen(pro.getImagenProducto64())));

		// Establecemos el precio del producto
		interfaz.getTextoPrecio().setText(pro.getPrecioVenta().setScale(2) + " €");

		// Establecemos el nombre del producto
		interfaz.getTextoNombre().setText(pro.getNombreProducto());

		// Comprobamos si producto esta sin stock, deshabilitamos el panel
		if (pro.isStockDisponible()) {
			interfaz.setEnabled(false);
		}

		// Actualizamos el panel
		new PanelUtil().actualizarPanel(interfaz);
	}

	/**
	 * Metodo que anade el producto en la {@link OrdenPedido} y tambien da feedback
	 * al usuario de que se ha seleccionado el producto
	 */
	protected void anadirProductoPedido() {
		// Cambiar color momentáneamente
		interfaz.setBackground(Color.decode("#D4AF37"));
		new PanelUtil().actualizarPanel(interfaz);

		// Volver al color original después de 50ms
		new javax.swing.Timer(50, _ -> {
			interfaz.setBackground(Color.white);
			new PanelUtil().actualizarPanel(interfaz);
		}).start();

		// Anadimos el producto al pedido
		new ModificarOrdenPedido(interfaz.getPedido()).anadirProducto(interfaz.getProducto());
		logger.info("Se ha añadido el producto en la lista de productos");

		// TODO Deberia de hacer una actualizacion de la lista de productos del panel
		// visual para que se muestre actualizado
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
				logger.info("La ruta obtenida existe en los archivos: {}", imagen.getAbsolutePath());
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

}
