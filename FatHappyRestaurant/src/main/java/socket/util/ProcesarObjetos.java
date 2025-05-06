package socket.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.Ping;
import socket.modelo.Pong;

public class ProcesarObjetos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ProcesarObjetos.class);

	protected void procesar(Object objeto) {

		// Si el objeto es una peticion de que estamos activo
		if (objeto instanceof Ping) {
			logger.debug("El servidor ha enviado una peticion de vida al cliente");
			new EnviarRecibirObjetos().EnviarObjetos(new Pong());
		}
		// Si el objeto es una respuesta de que estamos activos
		else if (objeto instanceof Pong) {
			logger.debug("El cliente ha enviado una peticion de vida al servidor");
			ClasesEstaticas.getColapong().offer((Pong) objeto);
		} else {
			logger.warn("Objeto desconocido {}", objeto.getClass().getSimpleName());
		}
	}
}
