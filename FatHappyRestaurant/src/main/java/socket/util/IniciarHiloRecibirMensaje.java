package socket.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hilo ligero que se encarga de recibir paquetes del servidor para comprobar la
 * conexion
 */
public class IniciarHiloRecibirMensaje extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarHiloRecibirMensaje.class);

	public static ComprobarConexionSocket comprobar = new ComprobarConexionSocket();

	private final EnviarRecibirObjetos enviarRecibir = new EnviarRecibirObjetos();

	public void run() {
		try {
			logger.debug("Se ha iniciado el hilo de recepcion de objetos");
			while (!Thread.currentThread().isInterrupted()) {
				enviarRecibir.RecibirObjetos();
			}
		} catch (Exception e) {
			logger.error("Se ha abortado el hilo de recepcion de objetos", e);
		}
	}
}
