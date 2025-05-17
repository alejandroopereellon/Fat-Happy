package socket.util;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.Ping;
import socket.modelo.Pong;

public class ComprobarConexionSocket {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ComprobarConexionSocket.class);

	private static CerrarConexionSocket cerrar = new CerrarConexionSocket();

	/**
	 * Metodo que hace usom del sistema {@link Ping} {@link Pong}, en el cual el
	 * cliente envia al servidor un objeto ping y este debe devolver un pong.
	 * 
	 * En caso de devolverse el objeto pong se asume que la conexion es correcta y
	 * en caso de no recibirlo se va a cerrar la conexion y se volverá a crear la
	 * conexion
	 * 
	 * @return TRUE si se recibe el {@link Pong} (conexion correcta) || FALSE si no
	 *         se ha recibido {@link Pong}
	 */
	public boolean comprobar() {
		if (ClasesEstaticas.getSocket() == null) {
			return false;
		}

		try {
			// Limpiamos la cola antes por si hay mensajes antiguos
			ClasesEstaticas.getColaPong().clear();

			// Enviamos un ping
			ClasesEstaticas.getListaobjetospendientes().add(new Ping());

			// Esperamos hasta 5 segundos el pong
			Pong pong = ClasesEstaticas.getColaPong().poll(15, TimeUnit.SECONDS);

			// Esperamos recibir un pong
			if (pong != null) {
				logger.debug("Se ha recibido correctamente el pong");
				return true;
			} else {
				logger.warn("No se ha recibido pong: tiempo agotado");
			}

		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			logger.error("Se ha rechazado la recepcion del pong, se va a cerrar el hilo");
			cerrar.cerrar();
		} catch (Exception e) {
			logger.error("Ha ocurrido un erro en la conexion al servidor, se va a reiniciar el servidor", e);
			// Cerramos la conexion al servidor
			logger.warn("Se va a cerrar el socket del servidor");
			cerrar.cerrar();
		}
		return false;
	}

	/**
	 * Metodo que comprueba si la conexion al socket esta cerrada, en caso de estar
	 * cerrada se va a realizar el cierre de la conexion
	 * 
	 * @return TRUE Si la conexion esta CERRADA || FALSE si la conexion NO esta
	 *         CERRADA
	 */
//	public boolean comprobar2() {
//		Socket so = ClasesEstaticas.getSocket().getSocketCliente();
//
//		try {
//			if (so == null || so.isClosed() || !so.isConnected() || so.isInputShutdown() || so.isOutputShutdown()) {
//				// Cerramos la conexion del socket
//				new CerrarConexionSocket().cerrar(ClasesEstaticas.getSocket());
//				// Retornamos el true
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("Ha ocurrido un error al comprobar si el cliente esta conectado, se va a cerrar el socket", e);
//
//			// Cerramos la conexion del socket
//			new CerrarConexionSocket().cerrar(ClasesEstaticas.getSocket());
//		}
//
//		logger.debug("Se va a retornar false en la comprobacion del estado de la conexion");
//		return false;
//	}
}
