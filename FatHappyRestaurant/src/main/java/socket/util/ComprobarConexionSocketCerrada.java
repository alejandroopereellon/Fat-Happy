package socket.util;

import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;

public class ComprobarConexionSocketCerrada {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ComprobarConexionSocketCerrada.class);

	/**
	 * Metodo que comprueba si la conexion al socket esta cerrada, en caso de estar
	 * cerrada se va a realizar el cierre de la conexion
	 * 
	 * @return TRUE Si la conexion esta CERRADA || FALSE si la conexion NO esta
	 *         CERRADA
	 */
	public boolean comprobar() {
		Socket so = ClasesEstaticas.getSocket().getSocketCliente();

		if (so.isClosed() || !so.isConnected() || so.isInputShutdown()) {
			// Cerramos la conexion del socket
			new CerrarConexionSocket().cerrar(ClasesEstaticas.getSocket());
			// Retornamos el true
			return true;
		}
		return false;
	}
}
