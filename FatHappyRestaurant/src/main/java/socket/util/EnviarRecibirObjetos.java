package socket.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.PedidoSocket;

/**
 * Clase que envia los {@link PedidoSocket} y recibe {@link PedidoSocket} del
 * servidor
 * 
 * @author Alejandro Perellón López
 */
public class EnviarRecibirObjetos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(EnviarRecibirObjetos.class);
	static final ComprobarConexionSocketCerrada conexion = new ComprobarConexionSocketCerrada();

	public boolean EnviarObjetos(Object objeto) {
		// Comprobamos si la conexion no esta cerrada
		if (!conexion.comprobar()) {
			logger.debug("Se va a enviar el objeto {}", objeto);

			try {
				ClasesEstaticas.getSocket().getOutput().writeObject(objeto);
				ClasesEstaticas.getSocket().getOutput().flush();
				logger.debug("Se ha enviado el objeto al servidor");
				return true;
			} catch (IOException e) {
				logger.error("No se ha podido enviar el objeto {} al servidor", objeto, e);
			}
		} else {
			logger.error("No existe una conexion con el servidor");
		}

		return false;
	}

	public Object RecibirObjetos() {
		// Comprobamos si la conexion no esta cerrada
		if (!conexion.comprobar()) {
			logger.debug("Se va a recibir el pedido");

			try {
				Object objeto = ClasesEstaticas.getSocket().getInput().readObject();
				logger.debug("Se ha enviado el objeto al servidor");
				return objeto;
			} catch (IOException e) {
				logger.error("Error leyendo objeto desde el servidor", e);
			} catch (ClassNotFoundException e) {
				logger.error(
						"El objeto serializado no existe, considera actualizar la version del cliente/servidor para tener las mismas clases",
						e);
			}
		} else {
			logger.error("No existe una conexion con el servidor");
		}

		return null;
	}

}
