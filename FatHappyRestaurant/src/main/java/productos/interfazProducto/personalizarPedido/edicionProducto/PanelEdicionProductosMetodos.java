package productos.interfazProducto.personalizarPedido.edicionProducto;

import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import pedido.interfazPedido.PanelPedidoMetodos;
import productos.interfazProducto.listaProductosPedidos.ListaProductosPedidosMetodos;
import productos.interfazProducto.personalizarPedido.extras.PanelExtra;
import productos.interfazProducto.personalizarPedido.extras.PanelExtraMetodos;
import productos.interfazProducto.personalizarPedido.ingredientes.PanelIngrediente;
import productos.interfazProducto.personalizarPedido.ingredientes.PanelIngredienteMetodos;
import productos.modelo.Extra;
import productos.modelo.Hamburguesa;
import productos.modelo.Ingrediente;
import productos.modelo.Postre;
import productos.modelo.Producto;

public class PanelEdicionProductosMetodos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelEdicionProductosMetodos.class);
	// Establecemos la interfaz
	private PanelEdicionProductos interfaz;
	// Establecemos el producto
	private Producto producto;

	// Constuctor de la clase
	public PanelEdicionProductosMetodos(PanelEdicionProductos interfaz) {
		this.interfaz = interfaz;
		this.producto = interfaz.getProducto();
	}

	public void iniciarPanelEdicion() {
		// Establecemos los datos del producto
		establecerDatosProducto();

		// Establecemos los ingredientes y extras
		establecerIngredientesExtras();

		// Iniciamos el panel en el selector de productos
		JPanel panel = ClasesEstaticas.getPanelPedido().getPanelProductos();
		new PanelUtil().insertarEnPanel(panel, interfaz);
	}

	private void establecerIngredientesExtras() {
		// Obtenemos el panel
		JPanel panel = interfaz.getPanelIngredientesExtras();
		// Establecemos el layout del panel en boxlayout y lo ponemos eje Y
		panel.setLayout(new GridLayout(0, 1));
		logger.debug("Se ha establecido el layout del panel");

		// Creamos una lista de ingredientes y de extras
		List<Ingrediente> listaIngredientes = null;
		List<Extra> listaExtras = null;

		if (producto instanceof Hamburguesa) {
			listaIngredientes = ((Hamburguesa) producto).getListaIngredientes();
			listaExtras = ((Hamburguesa) producto).getExtras();
			logger.debug("El producto es una hamburguesa");
		} else if (producto instanceof Postre) {
			listaIngredientes = ((Postre) producto).getListaIngredientes();
			listaExtras = ((Postre) producto).getListaExtras();
			logger.debug("El producto es un postre");
		}

		if (listaIngredientes != null) {
			System.out.println("Se estan creando paneles de ingredientes");
			// Insertamos el panel de ingrediente en el panel principal
			for (Ingrediente ing : listaIngredientes) {
				// Iniciamos el panel de ingredientes
				PanelIngrediente panIng = new PanelIngrediente(ing);
				// Establecemos los datos del panel de ingredientes
				new PanelIngredienteMetodos(panIng).iniciarPanelIngrediente();
				// Anadimos el panel al panel principal
				new PanelUtil().insertarEnPanelSinBorrar(panel, panIng);

				logger.debug("Se ha insertado el ingrediente {} en el panel", ing);
			}
		}

		if (listaExtras != null) {
			// Insetamos el panel de extra en el panel principal
			for (Extra ext : listaExtras) {
				// Iniciamos el panel de ingredientes
				PanelExtra panExt = new PanelExtra(ext);
				// Establecemos los datos del panel de ingredientes
				new PanelExtraMetodos(panExt).iniciarPanelExtras();
				// Anadimos el panel al panel principal
				new PanelUtil().insertarEnPanelSinBorrar(panel, panExt);

				logger.debug("Se ha insertado el extra {} en el panel", ext);
			}
		}

	}

	/**
	 * Metodo que establece los datos iniciales de un producto
	 */
	private void establecerDatosProducto() {
		// Establecemos el nombre
		interfaz.getNombreProducto().setText(producto.getNombreProducto());
		logger.debug("Se ha establecido el nombre en el panel de edicion");
		// Establecemos la imagen del producto
		File rutaImagen = new File(ConfiguracionInicial.get().getDirectorioLocal() + File.separator + "imagenes"
				+ File.separator + producto.getImagenProducto256());
		System.out.println(rutaImagen);
		if (rutaImagen.exists()) {
			ImageIcon imagen = new ImageIcon(rutaImagen.getAbsolutePath());
			interfaz.getImagenProducto().setIcon(imagen);
			logger.debug("Se ha establecido la imagen del producto");
		} else {
			logger.debug("No existe la imagen del producto, se ha dejado la por defecto");
		}
	}

	/**
	 * Metodo que vuelve a la ventana principal de seleccion de productos
	 */
	protected void volverPantallaPrincipal() {
		// 1. Mostramos la pantalla principal con toda la informacion
		new PanelPedidoMetodos(ClasesEstaticas.getPanelPedido()).iniciarPanelPedido();
		// 2. Actualizamos la lista
		new ListaProductosPedidosMetodos(ClasesEstaticas.getPanelPedido()).actualizarLista();
	}

}
