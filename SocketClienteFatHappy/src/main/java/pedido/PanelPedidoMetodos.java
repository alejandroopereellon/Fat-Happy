package pedido;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import interfazGrafica.util.ActualizarInterfaz;
import interfazGrafica.util.HiloContadorTiempoPedido;
import socket.modelo.Confirmacion;
import socket.modelo.PedidoSocket;

/**
 * Metodo que maneja la configuracion del panel de pedido del socket de cliente
 * 
 * @author Alejandro Perellón López
 */
public class PanelPedidoMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelPedidoMetodos.class);

	private final PanelPedido panel = new PanelPedido(this);

	private final PedidoSocket pedido;

	private final StringBuilder sb;

	private final HiloContadorTiempoPedido hiloTiempo;

	public PanelPedidoMetodos(PedidoSocket pedido, StringBuilder sb) {
		this.hiloTiempo = new HiloContadorTiempoPedido(pedido, panel);
		this.pedido = pedido;
		this.sb = sb;
	}

	/**
	 * Metodo que se encarga de realizar toda la configuracion inicial del pedido
	 * 
	 * @param esAdicional indica si el panel se marca y configura como panel
	 *                    adicional
	 * @return
	 */
	protected PanelPedido configuracionInicialPanelPedido(Boolean esAdicional) {
		logger.debug("Se va a configurar el panel de pedido, el valor de adicional es {}", esAdicional);
		// Realizamos la configuracion en caso de ser adicioan

		if (esAdicional) {
			logger.debug("El pedido es adicional");
			// Vaciamos los label de informacion del pedido
			panel.getLabelDatosPedido().setText("");
			panel.getLabelTiempoPasado().setText("");
		} else {
			logger.debug("El pedido no es adicional");
			// Si no es adicional añadimos la configuracion del pedido
			establecerInformacionPedidoNoAdicional();
		}

		// Insertamos la cadena de texto adicional
		panel.getTextArea().append(sb.toString());
		logger.debug("Se ha insertado el texto en el panel");

		return panel;
	}

	/**
	 * Metodo que realiza la confirmacion de que se ha realizado un pedido
	 */
	protected void confirmarPedido() {
		// Enviamos al servidor una confirmacion de que el pedido se ha añadido
		ClasesEstaticas.getListaobjetospendientes().add(new Confirmacion(pedido.getNumeroPedido()));
		logger.debug("Se ha insertado la confirmacion del pedido {}", pedido);
		// Marcamos el pedido como confirmado
		pedido.setConfirmado(true);
		logger.debug("Se ha marcado el pedido como confirmado");

		// Actualizamos todos los elementos
		new ActualizarInterfaz().actualizar();
	}

	/**
	 * Metodo que establece la informacio basica del pedido, inicia el reloj y
	 * muestra el metodo de entrega
	 */
	private void establecerInformacionPedidoNoAdicional() {
		// Establecemos los datos del pedido
		panel.getLabelDatosPedido()
				.setText("C " + pedido.getNumeroCaja() + " | P " + pedido.getNumeroPedido() + " | " + metodoEntrega());

		// Iniciamos el contador de tiempo
		hiloTiempo.start();

		logger.debug("Se han insertado los datos del pedido {}", pedido);
	}

	/**
	 * Metodo que calcula el metodo de entrega
	 * 
	 * @return String con el metodo de entrega
	 */
	private String metodoEntrega() {
		switch (pedido.getEstadoEntrega()) {
		case -1:
			return "Llevar";
		case 0:
			return "Mostrador";
		default:
			return String.valueOf("Mesa " + pedido.getEstadoEntrega());
		}
	}

}
