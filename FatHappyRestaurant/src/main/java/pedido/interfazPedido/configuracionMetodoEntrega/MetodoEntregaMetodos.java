package pedido.interfazPedido.configuracionMetodoEntrega;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.solicitarNumero.SolicitarNumeroMetodos;
import pedido.modelo.Pedido;

/**
 * Clase que contiene todos los metodos de la clase {@link MetodoEntrega} el
 * cual consiste en un dialogo modal que pregunta si el pedido es para llevar o
 * tomar, en caso de ser para tomar se puede elegir si para llevar a mesa o no
 *
 * @author Alejandro Perellón López
 */
public class MetodoEntregaMetodos {

	Pedido pedido;

	// Crear el logger
	static Logger logger = LogManager.getLogger(MetodoEntregaMetodos.class);

	public MetodoEntregaMetodos(Pedido pedido) {
		this.pedido = pedido;
	}

	public void solicitarMetodoEntega() {
		logger.debug("Se va a solicitar el metodo de entrega");

		MetodoEntrega metodo = new MetodoEntrega();
		metodo.setVisible(true);

		// Una vez que se ha cerrado configuramos la situacion
		if (metodo.getPedidoLlevar()) {
			logger.debug("El pedido es para llevar");
			pedido.getOrden().setLlevar(true);
		} else {
			logger.debug("El pedido es para tomar");
			pedido.getOrden().setLlevar(false);
			// Solicitamos el numero de mesa
			int numeroMesa = new SolicitarNumeroMetodos("Introduce numero de mesa").solicitarNumero();
			if (numeroMesa == 0) {
				new DialogoMostrarMensajeMetodos().mostrarMensaje("No se ha asignado una mesa al pedido");
			} else {
				pedido.getOrden().setNumeroMesa(numeroMesa);
				logger.debug("Se ha asignado el numero {} de mesa al pedido {}", numeroMesa, pedido.getNumeroPedido());
			}
		}

	}
}
