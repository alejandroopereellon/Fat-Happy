package interfazGrafica.util;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import interfazGrafica.util.panelVacio.panelVacio;
import interfazGrafica.ventanaPrincipal.VentanaPrincipal;
import pedido.PanelPedido;
import socket.modelo.PedidoSocket;

public class ActualizarInterfaz {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ActualizarInterfaz.class);

	private static final PanelUtil panelUtil = new PanelUtil();

	/**
	 * Metodo que se encarga de actualizar el panel de la {@link VentanaPrincipal}
	 * para mostrar los primeros 8 pedidos
	 */
	public void actualizar() {
		logger.debug("Se va a actualizar la interfaz");
		// Actualizamos los pedidos confirmados
		new OrganizarPedidos().organizarPedidos();
		logger.debug("Se han organizado los pedidos");

		// Borramos todos los paneles de la ventana principal
		ClasesEstaticas.getVentana().getPanelPedidos().removeAll();
		logger.debug("Se ha borrado todos los elementos del panel");

		int numeroElementos = 0;

		// Insertamos los paneles
		for (PedidoSocket pedido : ClasesEstaticas.getListapedidos()) {
			// Comprobamos si el numero de elementos aproximado es superior a 8
			if (numeroElementos + pedido.getPaneles().size() > 8) {
				// Cerramos el bucle
				logger.debug("Se va a superar el numero de elementos, se va a cerra el bucle");
				break;
			}

			// Comprobamos si el pedido NO esta confirmado
			if (!pedido.isConfirmado()) {
				// Insertamos los paneles
				for (PanelPedido panel : pedido.getPaneles()) {
					// Insertamos el panel de pedido
					panelUtil.insertarEnPanelSinBorrar(ClasesEstaticas.getVentana().getPanelPedidos(), panel);
					numeroElementos++;
				}
			}
		}
		logger.debug("Se han insertado todos los pedidos, el numero de elementos es {}", numeroElementos);

		// Si el numero de elementos es menor que el numero de huecos maximo
		if (numeroElementos < 8) {
			// Insertamos los paneles en vacio
			for (int i = numeroElementos; i < 8; i++) {
				mostrarPanel(new panelVacio());
			}
			logger.debug("Se han insertado los paneles vacios");
		}
	}

	/**
	 * Metodo que añade en el panel principal el objeto, ya sea panel de pedido o
	 * panel vacio
	 * 
	 * @param panel es el {@link JPanel} que se va a insertar en la
	 *              {@link VentanaPrincipal}
	 */
	private void mostrarPanel(JPanel panel) {
		panelUtil.insertarEnPanelSinBorrar(ClasesEstaticas.getVentana().getPanelPedidos(), panel);
	}
}
