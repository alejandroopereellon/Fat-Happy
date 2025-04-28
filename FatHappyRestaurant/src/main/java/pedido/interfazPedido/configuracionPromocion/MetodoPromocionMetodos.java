package pedido.interfazPedido.configuracionPromocion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import caja.interfazCaja.panelCobro.InterfazCobro;
import empleados.util.ActividadEmpleados;
import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;

/**
 * Clase que contiene todos los metodos de la clase {@link MetodoPromocion} el
 * cual consiste en un dialogo modal que pregunta si el pedido es para llevar o
 * tomar, en caso de ser para tomar se puede elegir si para llevar a mesa o no
 *
 * @author Alejandro Perellón López
 */
public class MetodoPromocionMetodos {

	Pedido pedido;
	InterfazCobro interfaz;

	// Crear el logger
	static Logger logger = LogManager.getLogger(MetodoPromocionMetodos.class);

	public MetodoPromocionMetodos(Pedido pedido, InterfazCobro interfaz) {
		this.pedido = pedido;
		this.interfaz = interfaz;
	}

	public void solicitarMetodoPromocion() {
		logger.debug("Se va a solicitar el metodo de promocion");

		// Solicitamos los permisos de superior
		if (!new ActividadEmpleados().solicitarPermisos("Acceso menu promocion", 2)) {
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("No hay permisos suficientes para acceder al menu de promociones");
			logger.debug("No hay permisos para acceder al menu de promociones");
			return;
		}

		MetodoPromocion metodo = new MetodoPromocion();
		metodo.setVisible(true);

		if (metodo.getPromocionarProducto() != null) {
			if (metodo.getPromocionarProducto()) {
				// Obtenemos el elemento seleccionado
				obtenerElementoSeleccionadoYPromocionar();
			} else {
				pedido.setPedidoPromocionado(!pedido.isPedidoPromocionado());
				logger.debug("Se ha promocionado el pedido ");
			}
		}
	}

	private void obtenerElementoSeleccionadoYPromocionar() {
		int posicionSeleccionada = interfaz.getListaProductosPedidos().getSelectedIndex();
		if (posicionSeleccionada != -1) {
			Object objetoSeleccionado = interfaz.getModeloLista().getElementAt(posicionSeleccionada);
			if (objetoSeleccionado instanceof Producto) {
				((Producto) objetoSeleccionado)
						.setProductoPromocionado(!((Producto) objetoSeleccionado).isProductoPromocionado());
			} else if (objetoSeleccionado instanceof MenuPedido) {
				((MenuPedido) objetoSeleccionado)
						.setMenuPromocionado(!((MenuPedido) objetoSeleccionado).isMenuPromocionado());
			}
		} else {
			new DialogoMostrarMensajeMetodos().mostrarMensaje("Debes seleccionar un articulo para promocionarlo");
		}

	}
}
