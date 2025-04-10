package productos.interfazPedido.personalizarPedido.edicionProducto;

import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.utilidadesGraficas.PanelUtil;
import productos.interfazPedido.personalizarPedido.extras.PanelExtra;
import productos.interfazPedido.personalizarPedido.ingredientes.PanelIngrediente;
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
	protected PanelEdicionProductosMetodos(PanelEdicionProductos interfaz) {
		this.interfaz = interfaz;
		this.producto = interfaz.getProducto();
	}

	protected void iniciarPanelEdicion() {
		// Establecemos los datos del producto
		establecerDatosProducto();

		// Establecemos los ingredientes y extras
		establecerIngredientesExtras();
	}

	private void establecerIngredientesExtras() {
		// Obtenemos el panel
		JPanel panel = interfaz.getPanelIngredientesExtras();
		// Establecemos el layout del panel en boxlayout y lo ponemos eje Y
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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

		boolean posicion = false;

		if (listaIngredientes != null) {
			// Insertamos el panel de ingrediente en el panel principal
			for (Ingrediente ing : listaIngredientes) {
				new PanelUtil().insertarEnPanel(panel, new PanelIngrediente(ing, posicion));
				cambiarEstadoPosicion(posicion);

				logger.debug("Se ha insertado el ingrediente {} en el panel", ing);
			}
		}

		if (listaExtras != null) {
			// Insetamos el panel de extra en el panel principal
			for (Extra ext : listaExtras) {
				new PanelUtil().insertarEnPanel(panel, new PanelExtra(ext, posicion));
				cambiarEstadoPosicion(posicion);

				logger.debug("Se ha insertado el extra {} en el panel", ext);
			}
		}

	}

	/**
	 * Metodo que establece si una posicion es par o impar
	 * 
	 * @param posicion es la posicion, si es true es que es par
	 * @return posicion inversa
	 */
	private boolean cambiarEstadoPosicion(boolean posicion) {
		return !posicion;
	}

	/**
	 * Metodo que establece los datos iniciales de un producto
	 */
	private void establecerDatosProducto() {
		// Establecemos el nombre
		interfaz.getNombreProducto().setText(producto.getNombreProducto());
		logger.debug("Se ha establecido el nombre en el panel de edicion");
		// Establecemos la imagen del producto
		if (new File(producto.getImagenProducto256()).exists()) {
			ImageIcon imagen = new ImageIcon(producto.getImagenProducto256());
			interfaz.getImagenProducto().setIcon(imagen);
			logger.debug("Se ha establecido la imagen del producto");
		} else {
			logger.debug("No existe la imagen del producto, se ha dejado la por defecto");
		}
	}

}
