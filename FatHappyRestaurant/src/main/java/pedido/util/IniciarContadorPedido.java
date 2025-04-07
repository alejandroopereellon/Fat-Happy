package pedido.util;

import caja.interfazCaja.PanelCaja;
import pedido.modelo.Pedido;

/**
 * Metodo que al crearse un nuevo pedido va a iniciar un nuevo contador del
 * tiempo que tarde el pedido en realizarse, una vez que el pedido haya
 * finalizado se va a finalizar el contador y se establece el valor en el pedido
 */
public class IniciarContadorPedido extends Thread {
	private Pedido pedidoOrigen;
	private PanelCaja panel;

	public IniciarContadorPedido(Pedido pedidoOrigen, PanelCaja panel) {
		this.pedidoOrigen = pedidoOrigen;
		this.panel = panel;
	}

	public void run() {
		int tiempoConsumido = 0;
		while (pedidoOrigen.getEstadoPedido() == 0) {
			tiempoConsumido++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			actualizarContador(tiempoConsumido);
		}
		// Establecemos el tiempo del pedido al objeto pedido
		pedidoOrigen.setTiempoPedido(tiempoConsumido);
		// Establecemos el contador en 0
		panel.getTiempoPedido().setText("00:00");

	}

	private void actualizarContador(int tiempoConsumido) {
		int segundos = tiempoConsumido;
		int minutos = segundos / 60;
		int segundosRestantes = segundos % 60;
		panel.getTiempoPedido().setText(minutos + ":" + segundosRestantes);
	}

	public IniciarContadorPedido(Pedido pedidoOrigen) {
		this.pedidoOrigen = pedidoOrigen;
	}

}
