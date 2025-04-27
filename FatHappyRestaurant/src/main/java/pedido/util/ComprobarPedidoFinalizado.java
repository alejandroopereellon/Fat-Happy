package pedido.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import caja.interfazCaja.panelCobro.InterfazCobro;
import caja.interfazCaja.panelCobro.MetodosInterfazCobro;
import pedido.interfazPedido.configuracionMetodoEntrega.MetodoEntregaMetodos;
import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;

public class ComprobarPedidoFinalizado {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ComprobarPedidoFinalizado.class);

	/**
	 * Metodo que comprueba si todo el pedido se ha realizado correctamente y todos
	 * los producto se han rellenado
	 * 
	 * @return TRUE si el pedido se ha realizado correctamente || FALSE si el pedido
	 *         no se ha realizado correctamente
	 */
	public boolean comprobarOrdenPedido() {
		Pedido pedido = ClasesEstaticas.getPedido();

		// 1. Comprobamos si el pedido es nulo
		if (pedido == null) {
			logger.error("No hay un pedido activo en este momento");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("ERROR: No hay un pedido iniciado");
			return false;
		}

		// 2. Comprobamos que el pedido no este vacio
		if (pedido.getOrden().getListaMenus().size() == 0 && pedido.getOrden().getListaProductos().size() == 0) {
			logger.error("El pedido esta vacio");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("ERROR: El pedido esta vacio");
			return false;
		}

		// 3. Comprobamos que los menuPedido tengan las bebidas y complementos
		if (comprobarMenusCompletos(pedido)) {
			// Establecemos el estado del pedido en pendiente de cobro
			pedido.setEstadoPedido(3);
			logger.debug("Se ha establecido el pedido en 'pendiente de cobro'");

			// Calculamos el importe total del pedido
			new CalcularImporte(pedido).obtenerImporteDescuento();
			logger.debug("Se ha actualizado el importe de descuento");

			// Solicitamos el metodo de entrega
			new MetodoEntregaMetodos(pedido).solicitarMetodoEntega();

			// Establecemos el panel secundario de la ventana principal para el cobro
			InterfazCobro interfaz = new InterfazCobro(pedido);
			new MetodosInterfazCobro(interfaz).iniciarInterfazCobro();

			return true;
		}
		return false;
	}

	/**
	 * Metodo que repasa todos los menus del pedido y comprueba si tienen bebida y
	 * complemento
	 * 
	 * @param pedido es el {@link Pedido} que se va a recorrer
	 */
	private boolean comprobarMenusCompletos(Pedido pedido) {
		for (MenuPedido menu : pedido.getOrden().getListaMenus()) {
			if (menu.getBebida() == null || menu.getComplemento() == null) {
				new DialogoMostrarMensajeMetodos().mostrarMensaje("El menú con hamburguesa '"
						+ menu.getHamburguesa().getNombreProducto() + "' tiene el menu incompleto");
				logger.debug("El menu con hamburguesa {} tiene el menu incompleto");
				return false;
			}
		}
		logger.debug("Todos los menus estan correctos");
		return true;
	}

}
