package pedido.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import caja.interfazCaja.panelPrincipalCaja.PanelCaja;
import pedido.modelo.Pedido;

/**
 * Metodo que al crearse un nuevo pedido va a iniciar un nuevo contador del
 * tiempo que tarde el pedido en realizarse, una vez que el pedido haya
 * finalizado se va a finalizar el contador y se establece el valor en el pedido
 */
public class IniciarContadorPedido extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(PedidoBuilder.class);

	private Pedido pedidoOrigen;
	private PanelCaja panel;

	public IniciarContadorPedido(Pedido pedidoOrigen) {
		this.pedidoOrigen = pedidoOrigen;
	}

	public void run() {
		logger.info("Se ha iniciado el hilo del contador de tiempo del pedido");

		// Establecemos el panel de la caja
		panel = ClasesEstaticas.getPanelCaja();

		int tiempoConsumido = 0;
		while (pedidoOrigen.getEstadoPedido() == 5) {
			tiempoConsumido++;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			actualizarContador(tiempoConsumido);
		}
		// Establecemos el tiempo del pedido en segundos al pedido
		pedidoOrigen.setTiempoPedido(tiempoConsumido);
		// Establecemos el contador del panel en 0
		panel.getTiempoPedido().setText("00:00");

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

		panel.getTiempoPedido().setText(minutosCadena + ":" + segundosCadena);
	}

}
