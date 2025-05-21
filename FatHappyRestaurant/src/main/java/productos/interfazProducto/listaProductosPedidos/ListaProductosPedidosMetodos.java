package productos.interfazProducto.listaProductosPedidos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import pedido.interfazPedido.PanelPedido;
import productos.interfazProducto.editarMenu.editarMenu;
import productos.interfazProducto.editarMenu.editarMenuMetodos;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductos;
import productos.interfazProducto.personalizarPedido.edicionProducto.PanelEdicionProductosMetodos;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Postre;
import productos.modelo.Producto;
import productos.modelo.Salsa;

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
		runInEdt(() -> interfaz.getModeloLista().addElement(obj));
		logger.info("Se ha añadido a la lista el objeto {}", obj);

	}

	/**
	 * Metodo que elimina de la lista el elemento seleccionado
	 * 
	 * @param obj {@link Object}o que se va a eliminar del modelo
	 */
	public void eliminarElemento(Object obj) {
		// Eliminamos el objeto seleccionado del modelo
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
		} else if (obj instanceof MenuPedido) {
			new editarMenuMetodos(
					new editarMenu(ConfiguracionInicial.get().getVentanaPrincipal(), true, (MenuPedido) obj))
					.iniciarInterfaz();
			logger.debug("Se esta editando el menu");
		} else if (obj instanceof Bebida) {
			((Bebida) obj).setExtraActivo(!((Bebida) obj).isExtraActivo());
			logger.debug("Se ha modificado el extra {} a la bebida al estado {}", ((Bebida) obj).getNombreExtra(),
					((Bebida) obj).isExtraActivo());
			actualizarLista();
		} else if (obj instanceof Complemento) {
			((Complemento) obj).setSalsas(new ArrayList<Salsa>());
			logger.debug("Se ha vaciado la lista de salsas al complemento {}", ((Complemento) obj).getNombreProducto());
			actualizarLista();
		}
	}

	/**
	 * Metodo que actualiza la lista para que cuadre con el modelo
	 */
	public void actualizarLista() {
		interfaz.getListaProductosPedidos().updateUI();
	}
	
	/**  Ejecuta el runnable en el EDT; si ya estamos, lo hace directamente. */
	private void runInEdt(Runnable r) {
	    if (SwingUtilities.isEventDispatchThread()) {
	        r.run();
	    } else {
	        SwingUtilities.invokeLater(r);
	    }
	}

}
