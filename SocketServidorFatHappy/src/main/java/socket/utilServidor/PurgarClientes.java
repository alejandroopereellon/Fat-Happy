package socket.utilServidor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.ClasesEstaticas;

/**
 * Clase que maneja la eliminacion de los clientes en la clase estatica
 * 
 * @author Alejandro Perellón López
 */
public class PurgarClientes {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PurgarClientes.class);

	private final ComprobarConexionSocket comprobarConexion = new ComprobarConexionSocket();

	private final ScheduledExecutorService programador = Executors.newSingleThreadScheduledExecutor();

	protected void iniciar() {
		logger.debug("Se ha iniciado la tarea programada del purgado de usuarios");

		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		programador.scheduleWithFixedDelay(() -> {
			ClasesEstaticas.getListaClientes().removeIf(cli -> !comprobarConexion.comprobar(cli));
			logger.debug("Se han purgado los clientes no activos");
		}, 0, 120, TimeUnit.SECONDS);
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
