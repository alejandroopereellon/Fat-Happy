package productos.interfazProducto.casillaProducto;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.coloresInterfaz.ColoresInterfaz;
import pedido.modelo.OrdenPedido;
import pedido.util.ModificarOrdenPedido;
import productos.interfazProducto.listaProductosPedidos.ListaProductosPedidosMetodos;
import productos.modelo.Bebida;
import productos.modelo.Producto;

/**
 * Clase que contiene los metodos de la {@link CasillaProducto}
 * 
 * @author Alejandro Perellón López
 */
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
		if (pro instanceof Bebida && pro.getPrecioVenta().equals(BigDecimal.ZERO)) {
			interfaz.getTextoPrecio().setText("Seleccionar tamaño");
		} else {
			interfaz.getTextoPrecio().setText(pro.getPrecioVenta().setScale(2) + " €");
		}

		// Establecemos el nombre del producto
		interfaz.getTextoNombre().setText(pro.getNombreProducto());

		// Comprobamos si producto esta sin stock, deshabilitamos el panel
		if (!pro.isStockDisponible()) {
			interfaz.getTextoPrecio().setText("No disponible");
			interfaz.setBackground(new Color(209, 209, 209));
			interfaz.getTextoNombre().setEnabled(true);
		}
	}

	/**
	 * Metodo que anade el producto en la {@link OrdenPedido} y tambien da feedback
	 * al usuario de que se ha seleccionado el producto
	 */
	protected void anadirProductoPedido() {
		// Primero comprobamos que hay stock del producto
		if (!interfaz.getProducto().isStockDisponible()) {
			logger.debug("El producto seleccionado no tiene stock");
			return;
		}

		interfaz.setBackground(ColoresInterfaz.PRIMARIO_DORADO);

		new javax.swing.Timer(150, _ -> {
			interfaz.setBackground(Color.white);
		}).start();

		// Anadimos el producto al pedido
		// 1. Comprobamos si el pedido se ha iniciado, en caso contrario se inicia
		if (ClasesEstaticas.getPedido() == null) {
			// Iniciamos el pedido
			ClasesEstaticas.iniciarPedido();
		}

		// 2. Anadimos el producto al pedido si el pedido esta iniciado
		if (ClasesEstaticas.getPedido() != null) {
			new ModificarOrdenPedido(ClasesEstaticas.getPedido()).insertarProductoEnPedido(interfaz.getProducto());
		}

		// 3. Actualizamos la lista
		new ListaProductosPedidosMetodos(ClasesEstaticas.getPanelPedido()).actualizarLista();
		;

		// 4. Seleccionamos el ultimo elemento de la lista (el anadido)
		ClasesEstaticas.getPanelPedido().getListaProductosPedidos()
				.setSelectedIndex(ClasesEstaticas.getPanelPedido().getModeloLista().getSize() - 1);
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

}
