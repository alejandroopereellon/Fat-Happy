
package productos.interfazProducto.editarMenu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductos;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductosMetodos;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Postre;
import productos.modelo.Producto;

/**
 * Clase que contiene los metodos necesarios para realizar modificaciones en los
 * menus
 * 
 * @author Alejandro Perellón López
 */
public class editarMenuMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(editarMenuMetodos.class);
	// Establecemos el panel de pedido
	private editarMenu interfaz;

	public editarMenuMetodos(editarMenu interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Metodo que cargar toda la configuracion inicial de la interfaz
	 * 
	 * @param menu es el {@link MenuPedido} que se va a editar
	 */
	public void iniciarInterfaz() {
		obtenerElementosMenu();
		interfaz.setVisible(true);
	}

	/**
	 * Metodo que obtiene todos los elementos del menu y los añade a la lista
	 * 
	 * @param menu es el {@link MenuPedido} que se va a editar
	 */
	private void obtenerElementosMenu() {
		MenuPedido menu = interfaz.getMenu();
		// Establecemos la hamburguesa, complementos, bebidas y postre si existen
		interfaz.getModeloLista().addElement(menu.getHamburguesa());

		if (menu.getComplemento() != null) {
			interfaz.getModeloLista().addElement(menu.getComplemento());
		}

		if (menu.getBebida() != null) {
			interfaz.getModeloLista().addElement(menu.getBebida());
		}

		if (menu.getPostre() != null) {
			interfaz.getModeloLista().addElement(menu.getPostre());
		}
	}

	/**
	 * Metodo que permite iniciar la interfaz de edicion de productos
	 */
	protected void editarProducto() {
		Producto pro = obtenerSeleccion();
		if (pro instanceof Hamburguesa) {
			new PanelEdicionProductosMetodos(new PanelEdicionProductos((Hamburguesa) pro)).iniciarPanelEdicion();
			logger.debug("Se esta editando la hamburguesa");
		} else if (pro instanceof Postre) {
			new PanelEdicionProductosMetodos(new PanelEdicionProductos((Postre) pro)).iniciarPanelEdicion();
			logger.debug("Se esta editando el postre");
		}
	}

	/**
	 * Metodo que elimina el {@link Producto} seleccionado de la lista mientras no
	 * sea una {@link Hamburguesa}
	 */
	protected void eliminarProducto() {
		MenuPedido menu = interfaz.getMenu();

		Producto pro = obtenerSeleccion();

		if (pro instanceof Complemento) {
			menu.setComplemento(null);
			logger.debug("Se ha establecido el complemento en null");
		} else if (pro instanceof Bebida) {
			menu.setBebida(null);
			logger.debug("Se ha establecido la bebida en null");
		} else if (pro instanceof Postre) {
			menu.setPostre(null);
			logger.debug("Se ha establecido el postre en null");
		} else if (pro instanceof Hamburguesa) {
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("No se puede eliminar la hamburguesa, debes eliminar el menu completo");
		}
	}

	/**
	 * Metodo que obtiene el elemento seleccionado en la lista
	 * 
	 * @return {@link Producto} seleccionado en la lista
	 */
	private Producto obtenerSeleccion() {
		return interfaz.getModeloLista().get(interfaz.getListaProductosMenu().getSelectedIndex());
	}

	/**
	 * Este metodo establece si un producto se puede o no editar
	 */
	void actualizarBotonEdicion() {
		interfaz.getBotonEditar().setEnabled(true);
		if (obtenerSeleccion() instanceof Bebida || obtenerSeleccion() instanceof Complemento) {
			interfaz.getBotonEditar().setEnabled(false);
		}
	}

}
