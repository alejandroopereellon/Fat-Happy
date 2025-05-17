package socket.utilServidor;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.modelo.PedidoSocket;
import socket.modelo.SocketCliente;

/**
 * Clase que envia los {@link PedidoSocket} y recibe {@link PedidoSocket} del
 * servidor
 * 
 * @author Alejandro Perellón López
 */
public class EnviarRecibirObjetos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(EnviarRecibirObjetos.class);
	static final ComprobarConexionSocket conexion = new ComprobarConexionSocket();
	static final CerrarConexionSocket cerrar = new CerrarConexionSocket();

	private final SocketCliente cliente;

	public EnviarRecibirObjetos(SocketCliente socket) {
		this.cliente = socket;
	}

	public synchronized boolean EnviarObjetos(Object objeto) {
		logger.debug("Se va a enviar el objeto {} al cliente {}", objeto, cliente);

		try {
			cliente.getOutput().writeObject(objeto);
			cliente.getOutput().flush();
			logger.debug("Se ha enviado el objeto al cliente {}", cliente);
			return true;
		} catch (NotSerializableException e) {
			logger.error("El objeto enviado por el cliente {} no es serializable", cliente);
		} catch (SocketTimeoutException e) {
			logger.error("El servidor no responde: Se ha agotado el tiempo de conexion al sevidor", e);
		} catch (SocketException e) {
			logger.error("Conexión interrumpida: {}", e.toString());
			cerrarConexion();
		} catch (IOException e) {
			logger.error("No se ha podido enviar el objeto {} al servidor", objeto, e);
			cerrarConexion();
		} catch (Exception e) {
			logger.error("Ha ocurrido un error desconocido con el cliente {}, se cerrará la conexion", cliente, e);
			cerrarConexion();
		}

		return false;
	}

	public synchronized void RecibirObjetos() {
		// Creamos el objeto vacio
		Object objeto = null;

		try {
			// Leemos el objeto
			objeto = cliente.getInput().readObject();
			logger.debug("Se ha recibido el objeto {} desde el cliente {}", objeto, cliente);
			// Vamos a procesar el objeto
			new procesarObjeto(objeto, cliente).start();
		} catch (NotSerializableException e) {
			logger.error("El objeto recibido del cliente {} no es serializable", cliente, e);
		} catch (EOFException e) {
			logger.error("Ha ocurrido un error final inesperado de datos, el cliente {} ha cerrado el stream", cliente,
					e);
			cerrarConexion();
		} catch (SocketTimeoutException e) {
			logger.warn("El cliente {} no responde: Se ha agotado el tiempo de conexion al cliente (ERROR COMTEMPLADO)",
					cliente);
		} catch (StreamCorruptedException | InvalidClassException e) {
			logger.error("El objeto desSerializado del cliente {} esta corrupto", cliente, e);
		} catch (SocketException e) {
			logger.error("Conexión con el cliente {} interrumpida: {}", cliente, e);
			cerrarConexion();
		} catch (IOException e) {
			logger.error("Error leyendo objeto desde el cliente {}", cliente, e);
			cerrarConexion();
		} catch (ClassNotFoundException e) {
			logger.error(
					"El objeto serializado no existe, considera actualizar la version del cliente {} para tener las mismas clases",
					cliente, e);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error desconocido con el cliente {}, se cerrará la conexion", cliente, e);
			cerrarConexion();
		}

	}

	private void cerrarConexion() {
		cerrar.cerrar(cliente);
	}
}
