package socket.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

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

	public boolean EnviarObjetos(Object objeto) {
		logger.debug("Se va a enviar el objeto {}", objeto);

		try {
			ClasesEstaticas.getSocket().getOutput().writeObject(objeto);
			ClasesEstaticas.getSocket().getOutput().flush();
			logger.debug("Se ha enviado el objeto {} al servidor", objeto);
			return true;
		} catch (NotSerializableException e) {
			logger.error("ENVIAR: El objeto enviado no es serializable");
		} catch (SocketTimeoutException e) {
			logger.error("ENVIAR: El servidor no responde: Se ha agotado el tiempo de conexion al sevidor", e);
		} catch (SocketException e) {
			logger.error("ENVIAR: Conexión interrumpida: {}", e.toString());
			cerrarConexion();
		} catch (IOException e) {
			logger.error("ENVIAR: No se ha podido enviar el objeto {} al servidor", objeto, e);
			cerrarConexion();
		} catch (Exception e) {
			logger.error("ENVIAR: Ha ocurrido un error no contemplado con el objeto {}", objeto, e);
			cerrarConexion();
		}
		return false;
	}

	public void RecibirObjetos() {
		logger.debug("Se va a recibir el objeto");

		// Creamos el objeto vacio
		Object objeto = null;

		try {
			if (ClasesEstaticas.getSocket().getInput()!=null) {
				logger.debug("El input no es nulo");
				objeto = ClasesEstaticas.getSocket().getInput().readObject();
			}else {
				JOptionPane.showMessageDialog(null, "MACHO ESTO ES NULO");
			}
			//objeto = ClasesEstaticas.getSocket().getInput().readObject();
			logger.debug("RECIBIR: Se ha recibido el objeto {} desde el servidor", objeto);
			new ProcesarObjetos().procesar(objeto);
		} catch (NotSerializableException e) {
			logger.error("RECIBIR: El objeto recibido no es serializable", e);
		} catch (EOFException e) {
			logger.error("RECIBIR: Ha ocurrido un error final inesperado de datos, el servidor ha cerrado el stream", e);
			cerrarConexion();
		} catch (SocketTimeoutException e) {
			logger.warn("RECIBIR: El servidor no responde: Se ha agotado el tiempo de conexion al sevidor (FALLO NO GRAVE)");
		} catch (StreamCorruptedException | InvalidClassException e) {
			logger.error("RECIBIR: El objeto desSerializado esta corrupto", e);
			cerrarConexion();
		} catch (SocketException e) {
			logger.error("RECIBIR: Conexión interrumpida: {}", e.toString());
			cerrarConexion();
		} catch (IOException e) {
			logger.error("RECIBIR: Error leyendo objeto desde el servidor", e);
			cerrarConexion();
		} catch (ClassNotFoundException e) {
			logger.error(
					"RECIBIR: El objeto serializado no existe, considera actualizar la version del cliente/servidor para tener las mismas clases",
					e);
		} catch (Exception e) {
			logger.error("RECIBIR: Ha ocurrido un error no contemplado", e);
			cerrarConexion();
		}
	}

	private void cerrarConexion() {
		cerrar.cerrar();
	}
}
