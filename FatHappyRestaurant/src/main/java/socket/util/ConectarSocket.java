package socket.util;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.SocketCliente;

/**
 * Metodo que se encarga de crear una conexion con el socket, añadirla al
 * singleton
 * 
 * @author Alejandro Perellón López
 */
public class ConectarSocket {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ConectarSocket.class);

	public boolean crearConexion() {
		logger.info("Se va a establecer una conexion socket al servidor");
		// Establecemos el socket
		Socket clientSocket = new Socket();

		// Configuramos el socket
		try {
			// Se va a establecer una conexion con el servidor en el puerto 5000
			clientSocket.connect(new InetSocketAddress(ConfiguracionInicial.get().getFtpHost(), 5000));

			if (clientSocket.isConnected()) {
				logger.info("Se ha establecido la conexion con el servidor");
				// Creamos el objeto socketCliente
				SocketCliente socketCliente = new SocketCliente(clientSocket);

				// Anadimos a la clase singleton el socket
				ClasesEstaticas.setSocket(socketCliente);

				logger.debug("Se ha establecido en el singleton el socketCliente {}", socketCliente);
				return true;
			}
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al crear la conexion con el socket servidor, vuelve a intentarlo", e);
		}
		return false;
	}

}
