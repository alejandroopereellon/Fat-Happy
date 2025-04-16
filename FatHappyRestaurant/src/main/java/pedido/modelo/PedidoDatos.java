package pedido.modelo;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import pedido.interfazPedido.PanelPedido;
import pedido.util.PedidoBuilder;

/**
 * Clase singleton estática que centraliza el acceso al pedido actual y al panel
 * activo del proceso de pedido.
 * 
 * @author Alejandro Perellón López
 */
public class PedidoDatos {

	private static Pedido pedidoActual;
	private static PanelPedido panel;

	// Constructor privado para evitar instancias
	private PedidoDatos() {
	}

	/**
	 * Guarda el pedido actual en memoria.
	 * 
	 * @param pedido el objeto Pedido
	 */
	public static void setPedido(Pedido pedido) {
		pedidoActual = pedido;
	}

	/**
	 * Obtiene el pedido actual.
	 * 
	 * @return el objeto Pedido guardado, o null si no hay ninguno.
	 */
	public static Pedido getPedido() {
		return pedidoActual;
	}

	/**
	 * Establece manualmente el panel de pedido. Útil si creas el panel en un flujo
	 * personalizado.
	 * 
	 * @param panel PanelPedido activo
	 */
	public static void setPanel(PanelPedido panel) {
		PedidoDatos.panel = panel;
	}

	/**
	 * Devuelve el panel del pedido actual. Si no se ha establecido manualmente, lo
	 * intenta obtener desde ConfiguracionInicial automáticamente.
	 * 
	 * @return PanelPedido actual o null si no está disponible
	 */
	public static PanelPedido getPanel() {
		if (panel == null) {
			try {
				Object panelSecundario = ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario();

				if (panelSecundario instanceof PanelPedido) {
					panel = (PanelPedido) panelSecundario;
				}
			} catch (NullPointerException e) {
				// Puede que la ventana principal aún no esté disponible
				panel = null;
			}
		}
		return panel;
	}

	/**
	 * Reinicia los datos en memoria del pedido actual.
	 */
	public static void reset() {
		pedidoActual = null;
		panel = null;
	}

	/**
	 * Comprueba si hay un pedido en curso.
	 * 
	 * @return true si hay pedido y panel asignado
	 */
	public static boolean hayPedidoEnCurso() {
		return pedidoActual != null && getPanel() != null;
	}

	public static boolean iniciarPedido() {
		// Iniciamos el pedido y lo añadimos al pedidoActual
		pedidoActual = new PedidoBuilder().build();
		// Establecemos la ventana
		panel = getPanel();

		if (pedidoActual != null && panel != null) {
			return true;
		}
		return false;
	}
}
