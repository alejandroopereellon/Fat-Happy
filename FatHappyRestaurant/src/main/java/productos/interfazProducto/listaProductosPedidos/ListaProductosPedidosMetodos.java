package productos.interfazProducto.listaProductosPedidos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.interfazPedido.PanelPedido;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductos;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductosMetodos;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Postre;
import productos.modelo.Producto;

/**
 * Clase que maneja el modelo de la lista para poder ver y manipular los
 * productos pedidos por el cliente
 */
public class ListaProductosPedidosMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ListaProductosPedidosMetodos.class);

	private PanelPedido interfaz;

	public ListaProductosPedidosMetodos(PanelPedido panel) {
		this.interfaz = panel;
	}

	/**
	 * Metodo que añade un objeto en la lista
	 * 
	 * @param obj {@link Object}o que se va a insertar, puede ser {@link Producto} o
	 *            {@link MenuPedido}
	 */
	public void anadirElemento(Object obj) {
		interfaz.getModeloLista().addElement(obj);
		logger.info("Se ha añadido a la lista el objeto {}", obj);
	}

	/**
	 * Metodo que elimina de la lista el elemento seleccionado
	 * 
	 * @param obj {@link Object}o que se va a eliminar del modelo
	 */
	public void eliminarElemento(Object obj) {
		//Eliminamos el objeto seleccionado del modelo
		interfaz.getModeloLista().removeElement(obj);
		logger.debug("Se ha eliminado de la lista el objeto {}", obj);
	}

	/**
	 * Metodo que permite editar el elemento seleccionado
	 */
	public void editarElemento() {
		Object obj = interfaz.getListaProductosPedidos().getSelectedValue();

		if (obj instanceof Hamburguesa) {
			new PanelEdicionProductosMetodos(new PanelEdicionProductos((Hamburguesa) obj)).iniciarPanelEdicion();
			logger.debug("Se esta editando la hamburguesa");
		} else if (obj instanceof Postre) {
			new PanelEdicionProductosMetodos(new PanelEdicionProductos((Postre) obj)).iniciarPanelEdicion();
			logger.debug("Se esta editando el pedido");
		}
	}
}
