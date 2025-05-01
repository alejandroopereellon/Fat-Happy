package socket.util;

import java.net.Socket;

import auxiliares.singleton.ClasesEstaticas;

public class ComprobarConexionSocketCerrada {

	/**
	 * Metodo que comprueba si la conexion al socket esta cerrada
	 * 
	 * @return TRUE Si la conexion esta CERRADA || FALSE si la conexion NO esta
	 *         CERRADA
	 */
	public boolean comprobarConexionSocketCerrada() {
		Socket so = ClasesEstaticas.getSocket().getSocketCliente();
		return so.isClosed() || !so.isConnected() || so.isInputShutdown();
	}
}
