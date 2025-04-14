package productos.interfazPedido.SeleccionProductos;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.utilidadesGraficas.PanelUtil;
import pedido.modelo.OrdenPedido;
import pedido.modelo.PedidoDatos;
import productos.interfazPedido.SeleccionProductos.ProductoSeleccionado.*;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;

/**
 * Clase con el principal objetivo de actualizar el panel de los productos
 * pedidos
 */
public class ActualizarPanelProductosPedidos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ActualizarPanelProductosPedidos.class);

	public void actualizarPanel() {
		// Comprobamos si el pedido y el panel funcionan correctamente
		if (PedidoDatos.getPedido() != null && PedidoDatos.getPanel() != null) {
			// Obtenemos el panel de pedidos
			JPanel panel = PedidoDatos.getPanel().getPanelProductosPedidos();

			// Vaciamos el panel antes de actualizar
			panel.removeAll();

			// Anadimos los menus en el panel
			anadirMenu(panel);

			// Anadimos los productos sueltos en el panel
			anadirProductos(panel);
		}
	}

	private void anadirProductos(JPanel panel) {
		OrdenPedido ordenPedido = PedidoDatos.getPedido().getOrden();
		for (Producto producto : ordenPedido.getListaProductos()) {
			// Iniciamos el panel de producto seleccionado
			PanelProductoSeleccionado proSel = new PanelProductoSeleccionado(producto);
			// Iniciamos los ajustes del panel
			new PanelProductoSeleccionadoMetodos(proSel).iniciarPanel();
			// Insertamos en el panel todos los productos
			new PanelUtil().insertarEnPanelSinBorrar(panel, proSel);

			logger.debug("Se ha insertado el producto en el panel de seleccion {}", producto);
		}

	}

	private void anadirMenu(JPanel panel) {
		OrdenPedido ordenPedido = PedidoDatos.getPedido().getOrden();
		for (MenuPedido menu : ordenPedido.getListaMenus()) {
			// new PanelUtil().insertarEnPanelSinBorrar(panel, //TODO);
			logger.debug("Se ha insertado un menu el siguiente menu en el pedido: {}", menu);
		}
	}

}
