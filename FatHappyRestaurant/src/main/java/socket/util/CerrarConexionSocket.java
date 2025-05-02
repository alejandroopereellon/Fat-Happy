package socket.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.SocketCliente;

/**
 * Clase encarga de cerrar las conexiones del socket cliente
 * 
 * @author Alejandro Perellón López
 */
public class CerrarConexionSocket {
	// Crear el logger
	static Logger logger = LogManager.getLogger(EnviarRecibirObjetos.class);

	public boolean cerrar(SocketCliente cliente) {

		// Establecemos el input en null
		cliente.setInput(null);
		ClasesEstaticas.getSocket().setInput(null);
		logger.debug("Se ha cerrado el input del cliente");

		// Establecemos el output en null
		cliente.setOutput(null);
		ClasesEstaticas.getSocket().setOutput(null);
		logger.debug("Se ha cerrado el output del cliente");

		// Cerramos el socket del cliente
		try {
			cliente.getSocketCliente().close();
			cliente.setSocketCliente(null);
			ClasesEstaticas.setSocket(null);
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al cerrar el socket del cliente");
		}

		return false;

	}

}
