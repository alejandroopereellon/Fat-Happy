package socket.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.RolSocket;
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
		logger.info("Se va a establecer una conexion socket al servidor");
		// Establecemos el socket
		Socket cliente = new Socket();

		// Configuramos el socket
		try {
			// Se va a establecer una conexion con el servidor en el puerto 5000
			cliente.connect(new InetSocketAddress("localhost"/* ConfiguracionInicial.get().getFtpHost() */, 5000));
			logger.info("Se va a stablecer conexion en la ip {}, puerto {}", cliente.getInetAddress(),
					cliente.getPort());

			if (cliente.isConnected()) {

				// Creamos el objeto socketCliente
				SocketCliente socketCliente = new SocketCliente(cliente);
				logger.debug("Se ha establecido el socket del cliente {}", cliente);

				// Anadimos a la clase singleton el socket
				ClasesEstaticas.setSocket(socketCliente);
				logger.debug("Se ha establecido el socket del cliente en la clase estatica");

				// Enviamos los datos del rol al cliente
				new EnviarRecibirObjetos()
						.EnviarObjetos(new RolSocket(ClasesEstaticas.getRestaurante().getIdRestaurante(), 0));
				logger.info("Se ha establecido la conexion con el servidor");

				logger.debug("Se ha establecido en el singleton el socketCliente {}", socketCliente);
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
