package pedido.interfazPedido;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ventanaPrincipal.InterfazVentanaPrincipalMetodos;

public class PanelPedidoMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(InterfazVentanaPrincipalMetodos.class);
	// Establecemos el panel de pedido
	private PanelPedido interfaz;

	// Constructor
	public PanelPedidoMetodos(PanelPedido interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarPanelPedido() {
		// Establecemos la visibilidad del panel
		interfaz.setVisible(true);

	}

}
