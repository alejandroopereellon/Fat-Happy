package socket.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;

/**
 * Hilo ligero que se encarga de recibir paquetes del servidor para comprobar la
 * conexion
 */
public class IniciarHiloRecibirMensaje extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarHiloRecibirMensaje.class);

	//public static ComprobarConexionSocket comprobar = new ComprobarConexionSocket();

	private final EnviarRecibirObjetos enviarRecibir = new EnviarRecibirObjetos();

	public void run() {
		try {
			logger.debug("Se ha iniciado el hilo de recepcion de objetos");
			while (!Thread.currentThread().isInterrupted()) {
				enviarRecibir.RecibirObjetos();
			}
			logger.info("Se ha cerrado el hilo de recepcion de objetos del cliente {}", ClasesEstaticas.getSocket());
		} catch (Exception e) {
			logger.error("Se ha abortado el hilo de recepcion de objetos", e);
		}
	}
}
