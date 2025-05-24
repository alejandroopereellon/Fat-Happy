package socket.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
	static final CerrarConexionSocket cerrar = new CerrarConexionSocket();

	public synchronized boolean EnviarObjetos(Object objeto) {

		logger.debug("Se va a enviar el objeto {}", objeto);

		try {
			ClasesEstaticas.getSocket().getOutput().writeObject(objeto);
			ClasesEstaticas.getSocket().getOutput().flush();
			logger.debug("Se ha enviado el objeto al servidor");
			return true;
		} catch (NotSerializableException e) {
			logger.error("El objeto enviado no es serializable");
		} catch (SocketTimeoutException e) {
			logger.error("El servidor no responde: Se ha agotado el tiempo de conexion al sevidor", e);
		} catch (SocketException e) {
			logger.error("Conexión interrumpida: {}", e.toString());
			cerrarConexion();
		} catch (IOException e) {
			logger.error("No se ha podido enviar el objeto {} al servidor", objeto, e);
			cerrarConexion();
		}
		return false;
	}

	public void RecibirObjetos() {
		logger.debug("Se va a recibir el objeto");

		// Creamos el objeto vacio
		Object objeto = null;

		try {
			objeto = ClasesEstaticas.getSocket().getInput().readObject();
			logger.debug("Se ha recibido el objeto desde el servidor");
			new ProcesarObjetos().procesar(objeto);
		} catch (NotSerializableException e) {
			logger.error("El objeto recibido no es serializable", e);
		} catch (EOFException e) {
			logger.error("Ha ocurrido un error final inesperado de datos, el servidor ha cerrado el stream", e);
			cerrarConexion();
		} catch (SocketTimeoutException e) {
			logger.warn("El servidor no responde: Se ha agotado el tiempo de conexion al sevidor (FALLO NO GRAVE)");
		} catch (StreamCorruptedException | InvalidClassException e) {
			logger.error("El objeto desSerializado esta corrupto", e);
			cerrarConexion();
		} catch (SocketException e) {
			logger.error("Conexión interrumpida: {}", e.toString());
			cerrarConexion();
		} catch (IOException e) {
			logger.error("Error leyendo objeto desde el servidor", e);
			cerrarConexion();
		} catch (ClassNotFoundException e) {
			logger.error(
					"El objeto serializado no existe, considera actualizar la version del cliente/servidor para tener las mismas clases",
					e);
		}
	}

	private void cerrarConexion() {
		cerrar.cerrar();
	}
}
