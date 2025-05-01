package socket.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	public boolean EnviarObjetos(Object objeto) {
		// Comprobamos si la conexion no esta cerrada
		if (!new ComprobarConexionSocketCerrada().comprobarConexionSocketCerrada()) {
			logger.debug("Se va a enviar el objeto {}", objeto);

			try {
				ObjectOutputStream enviarObjeto = ClasesEstaticas.getSocket().getOutput();
				enviarObjeto.writeObject(objeto);
				enviarObjeto.flush();

				logger.debug("Se ha enviado el objeto al servidor");
				return true;
			} catch (IOException e) {
				logger.error("No se ha podido enviar el objeto al servidor");
			}
		} else {
			logger.error("No existe una conexion con el servidor");
		}

		return false;
	}

	public Object RecibirObjetos() {
		// Comprobamos si la conexion no esta cerrada
		if (!new ComprobarConexionSocketCerrada().comprobarConexionSocketCerrada()) {
			logger.debug("Se va a recibir el pedido");

			try {
				ObjectInputStream recibirObjeto = ClasesEstaticas.getSocket().getInput();
				Object objeto = recibirObjeto.readObject();

				logger.debug("Se ha enviado el objeto al servidor");
				return objeto;
			} catch (IOException e) {
				logger.error("No se ha recibir el objeto del servidor");
			} catch (ClassNotFoundException e) {
				logger.error("Ha ocurrido un error al detectar la clase del objeto");
			}
		} else {
			logger.error("No existe una conexion con el servidor");
		}

		return null;
	}

}
