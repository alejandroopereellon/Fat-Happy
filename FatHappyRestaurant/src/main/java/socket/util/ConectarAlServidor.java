package socket.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import socket.modelo.SocketCliente;

/**
 * Metodo que se encarga de crear una conexion con el socket, añadirla al
 * singleton
 * 
 * @author Alejandro Perellón López
 */
public class ConectarAlServidor {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ConectarAlServidor.class);

	public boolean crearConexion() {

		// Si NO existe conexion al servidor establecemos una nueva conexion
		logger.info("Se va a establecer una conexion socket al servidor");
		// Creamos el socket
		Socket cliente = new Socket();

		// Configuramos el socket
		try {
			// Se va a establecer una conexion con el servidor en el puerto 5000
			cliente.connect(new InetSocketAddress("localhost", 5000), 5000);
			logger.info("Se va a stablecer conexion en la ip {}, puerto {}", cliente.getInetAddress(),
					cliente.getPort());

			if (cliente.isConnected()) {
				// Creamos el objeto socketCliente
				new SocketCliente(cliente);
				logger.debug("Se ha establecido el socket del cliente {}", cliente);
				logger.info("Se ha establecido la conexion con el servidor");

				return true;
			}
		} catch (SocketTimeoutException e) {
			logger.error("Se ha excedido el tiempo de espera del socket", e);
		} catch (UnknownHostException e) {
			logger.error("No se ha podido resolver la direccion ip del servidor", e);
		} catch (IOException e) {
			logger.error("Ha ocurrido un error de red", e);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al crear la conexion con el socket servidor", e);
		}
		return false;
	}

}
