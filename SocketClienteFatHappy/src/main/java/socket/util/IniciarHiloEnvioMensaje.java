package socket.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;

public class IniciarHiloEnvioMensaje extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarHiloRecibirMensaje.class);

	public static ComprobarConexionSocket comprobar = new ComprobarConexionSocket();

	private final EnviarRecibirObjetos enviarRecibir = new EnviarRecibirObjetos();

	List<Object> listaObjetosPendientes = ClasesEstaticas.getListaobjetospendientes();

	public void run() {
		try {
			logger.debug("Se ha iniciado el hilo de envio de objetos");
			while (!Thread.currentThread().isInterrupted()) {
				try {
					// Si la lista no esta vacia se continua
					if (!listaObjetosPendientes.isEmpty()) {
						// Enviamos el primer objeto de la lista
						enviarRecibir.EnviarObjetos(listaObjetosPendientes.getFirst());
						// Eliminamos el primer objeto de la lista
						listaObjetosPendientes.removeFirst();
					}
				} catch (Exception e) {
					logger.error("Ha ocurrido un error en el manejo de la lista de envio de objetos");
				}

			}
			logger.info("Se ha cerrado el hilo de envio de objetos del cliente {}", ClasesEstaticas.getSocket());
		} catch (Exception e) {
			logger.error("Se ha abortado el hilo de recepcion de objetos", e);
		}
	}

}
