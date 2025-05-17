package socket.utilServidor;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.ClasesEstaticas;
import socket.modelo.PedidoSocket;

/**
 * Clase que maneja la eliminacion de los pedidos en la clase estatica
 * 
 * @author Alejandro Perellón López
 */
public class PurgarPedidos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PurgarPedidos.class);

	private final ScheduledExecutorService programador = Executors.newSingleThreadScheduledExecutor();

	protected void iniciar() {
		logger.debug("Se ha iniciado la tarea programada del purgado de pedidos");
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		programador.scheduleWithFixedDelay(() -> {
			ClasesEstaticas.getListaPedidos().removeIf(ped -> comprobarPedidoPasadoTiempo(ped));
			logger.debug("Se han purgado los pedidos no activos");
		}, 0, 300, TimeUnit.SECONDS);
	}

	/**
	 * Metodo que comprueba si un pedido lleva mas de 20 minutos sin borrarse, se
	 * eliminará para liberar recursos del sistema
	 * 
	 * @param pedido es le {@link PedidoSocket} que se va a comprobar
	 * @return TRUE si lleva mas de 25 minutos || FALSE si lleva menos de 25 minutos
	 */
	private boolean comprobarPedidoPasadoTiempo(PedidoSocket pedido) {
		LocalDateTime fechaCaducidad = pedido.getMomentoLlegada().plusMinutes(25);

		if (LocalDateTime.now().isAfter(fechaCaducidad)) {
			logger.debug("El pedido {} restaurante {} lleva mas de 25 minutos, se liberará", pedido.getNumeroPedido(),
				pedido.getNumeroRestaurante());
			return true;
		}
		return false;
		
	}

	public void detener() {
		// Detenemos el programador
		programador.shutdownNow();

		try {
			// Esperamos 5 segundos a que termine la tarea si estaba iniciada
			if (!programador.awaitTermination(5, TimeUnit.SECONDS)) {
				// Si no se ha podido finalizar la tarea se notifica
				logger.warn("El scheduler no se detuvo a tiempo.");
			}
		} catch (InterruptedException ie) {
			logger.error("Se ha interrumpido la tarea durante la ejecucion");
			Thread.currentThread().interrupt();
		}
	}

}
