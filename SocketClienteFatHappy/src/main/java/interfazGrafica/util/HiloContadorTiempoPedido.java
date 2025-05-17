package interfazGrafica.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.PanelPedido;
import socket.modelo.PedidoSocket;

/**
 * Metodo que al crearse un nuevo pedido va a iniciar un nuevo contador del
 * tiempo que tarde el pedido en realizarse, una vez que el pedido haya
 * finalizado se va a finalizar el contador y se establece el valor en el pedido
 * 
 * @author Alejandro Perellón López
 */
public class HiloContadorTiempoPedido extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(HiloContadorTiempoPedido.class);

	private final PedidoSocket pedidoOrigen;

	private final PanelPedido panel;

	public HiloContadorTiempoPedido(PedidoSocket pedidoOrigen, PanelPedido panel) {
		this.pedidoOrigen = pedidoOrigen;
		this.panel = panel;

	}

	public void run() {
		logger.debug("Se ha iniciado el hilo del contador de tiempo del pedido");

		int tiempoConsumido = 0;
		while (!pedidoOrigen.isConfirmado()) {
			tiempoConsumido++;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			actualizarContador(tiempoConsumido);
		}
	}

	private void actualizarContador(int tiempoConsumido) {
		int segundos = tiempoConsumido;
		int minutos = segundos / 60;
		int segundosRestantes = segundos % 60;

		String minutosCadena = "0", segundosCadena = "0";
		if (minutos < 10) {
			minutosCadena = "0" + minutos;
		} else {
			minutosCadena = String.valueOf(minutos);
		}

		if (segundosRestantes < 10) {
			segundosCadena = "0" + segundosRestantes;
		} else {
			segundosCadena = String.valueOf(segundosRestantes);
		}

		panel.getLabelTiempoPasado().setText(minutosCadena + ":" + segundosCadena);

	}

}
