package socket.utilServidor;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.modelo.Ping;
import socket.modelo.Pong;
import socket.modelo.SocketCliente;

public class ComprobarConexionSocket {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ComprobarConexionSocket.class);

	private static int numeroComprobacion = 0;

	/**
	 * Metodo que hace uso del sistema {@link Ping} {@link Pong}, en el cual el
	 * cliente envia al servidor un objeto ping y este debe devolver un pong.
	 * 
	 * En caso de devolverse el objeto pong se asume que la conexion es correcta y
	 * en caso de no recibirlo se va a cerrar la conexion y se volverá a crear la
	 * conexion
	 * 
	 * @return TRUE si se recibe el {@link Pong} (conexion correcta) || FALSE si no
	 *         se ha recibido {@link Pong}
	 */
	public boolean comprobar(SocketCliente cliente) {
		numeroComprobacion++;

		try {
			// Limpiamos la cola antes por si hay mensajes antiguos
			cliente.getColaPong().clear();

			// Enviamos el objeto al cliente
			Ping ping = new Ping();
			cliente.getListaObjetosPendientes().add(ping);
			logger.debug("{} Se ha enviado la peticion peticion de vida numero {} al servidor", LocalDateTime.now(),
					numeroComprobacion);

			// Esperamos hasta 5 segundos el pong
			Pong pong = cliente.getColaPong().poll(15, TimeUnit.SECONDS);

			// Esperamos recibir un pong
			if (pong != null) {
				logger.debug("{} Se ha recibido correctamente el pong del servidor numero {}", LocalDateTime.now(),
						numeroComprobacion);
				return true;
			} else {
				logger.warn("No se ha recibido pong id {}: tiempo agotado", ping.getNumeroComprobacion());
			}

		} catch (Exception e) {
			logger.error("Ha ocurrido un error al hacer una peticion de pong en la conexion al cliente {}", cliente, e);
		}
		// Cerramos la conexion al servidor
		logger.warn("Se va a cerrar el socket del servidor");
		new CerrarConexionSocket().cerrar(cliente);
		return false;
	}

	/**
	 * Metodo que comprueba si la conexion al socket esta cerrada, en caso de estar
	 * cerrada se va a realizar el cierre de la conexion
	 * 
	 * @return TRUE Si la conexion esta CERRADA || FALSE si la conexion NO esta
	 *         CERRADA
	 */
//	public boolean comprobar2(SocketCliente so) {
//		try {
//			if (so.getSocketCliente() == null || so.getSocketCliente().isClosed()
//					|| !so.getSocketCliente().isConnected() || so.getSocketCliente().isInputShutdown()
//					|| so.getSocketCliente().isOutputShutdown()) {
//				// Cerramos la conexion del socket
//				new CerrarConexionSocket().cerrar(so);
//				// Retornamos el true
//				logger.debug("Conexion cerrada");
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("Ha ocurrido un error al comprobar si el cliente esta conectado, se va a cerrar el socket", e);
//
//			// Cerramos la conexion del socket
//			new CerrarConexionSocket().cerrar(so);
//		}
//
//		logger.debug("La conexion al cliente {} esta abierta", so);
//		return false;
//	}
}
