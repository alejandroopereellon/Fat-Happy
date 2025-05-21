package pedido;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import interfazGrafica.util.ActualizarInterfaz;
import socket.modelo.Confirmacion;
import socket.modelo.PedidoSocket;

public class CrearPedido extends Thread {
	// Crear el logger
	static Logger logger = LogManager.getLogger(CrearPedido.class);

	private final PedidoSocket pedido;

	public static ActualizarInterfaz actualizarInterfaz = new ActualizarInterfaz();

	public CrearPedido(PedidoSocket pedido) {
		this.pedido = pedido;
	}

	public void run() {

		// Separamos los productos segun el rol
		StringBuilder sb = new ObtenerTextoPedido(pedido).separarFormatearProductos();
		logger.debug("El texto del pedido es {}", sb);

		// Si la lista de productos es nula no se genera el pedido
		if (sb == null) {
			logger.debug("El texto de pedido es nulo, se va a confirmar el pedido automaticamente");
			// Confirmamos que el pedido esta creado
			ClasesEstaticas.getListaobjetospendientes().add(new Confirmacion(pedido.getNumeroPedido()));

			// Finalizamos el proceso de creacion del producto
			logger.debug("Se va a finalizar el proceso de la creacion de un pedido");
			return;
		}

		// Anadimos el pedido en la clase estatica si tiene algun elemento que mostrar
		ClasesEstaticas.getListapedidos().add(pedido);

		// Dividimos el texto del panel en varias lineas
		String[] filas = sb.toString().split(System.lineSeparator());

		// Creamos el texto dedicado al nuevo panel
		StringBuilder textoPanel = new StringBuilder();

		// Creamos la bandera que informa de si el panel es adicional o no
		boolean bandera = true;

		// Por cada linea, vamos a añadirla, cuando sea divisor de 15 se genera un nuevo
		// panel
		for (int i = 0; i < filas.length; i++) {
			textoPanel.append(filas[i]).append(System.lineSeparator());

			// Si el numero de elementos es dividor de 15, se añaden los datos al panel y se
			// vuelve a comenzar
			if ((i + 1) % 15 == 0) {
				// Insertamos el panel de pedidos con el texto
				PanelPedido panel = new PanelPedidoMetodos(pedido, textoPanel).configuracionInicialPanelPedido(bandera);
				pedido.getPaneles().add(panel);

				bandera = false;

				// Reiniciamos el textoPanel
				textoPanel.setLength(0);
			}
		}

		// Si hay elementos restantes se añaden
		if (textoPanel.length() > 0) {
			// Insertamos el panel de pedidos con el texto
			PanelPedido panel = new PanelPedidoMetodos(pedido, textoPanel).configuracionInicialPanelPedido(false);
			pedido.getPaneles().add(panel);
		}

		// reproducimos el sonido de nuevo pedido
		new ReproducirSonido("campana.wav");
		logger.debug("Se ha reproducido el sonido");

		// Actualizamos la interfaz
		new ActualizarInterfaz().actualizar();
	}
}
