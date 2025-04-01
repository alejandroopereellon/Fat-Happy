package pedido.util;

import pedido.modelo.Pedido;

/**
 * Metodo que al crearse un nuevo pedido va a iniciar un nuevo contador del
 * tiempo que tarde el pedido en realizarse, una vez que el pedido haya
 * finalizado se va a finalizar el contador y se establece el valor en el pedido
 */
public class IniciarContadorPedido extends Thread {
	private Pedido pedidoOrigen;

	public void run() {
		int tiempoConsumido = 0;
		while (pedidoOrigen.getEstadoPedido() == 0) {
			tiempoConsumido++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		pedidoOrigen.setTiempoPedido(tiempoConsumido);
	}

	public IniciarContadorPedido(Pedido pedidoOrigen) {
		this.pedidoOrigen = pedidoOrigen;
	}

}
