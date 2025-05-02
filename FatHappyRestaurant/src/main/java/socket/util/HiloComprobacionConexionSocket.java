package socket.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;

/**
 * Metodo que ejecuta un hilo y comprueba si el socket sigue activo, en caso de
 * desconexion va a iniciar una nueva conexion
 * 
 * @author Alejandro Perellón López
 */
public class HiloComprobacionConexionSocket extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(HiloComprobacionConexionSocket.class);
	// Creamos el comprobador del socket
	static final ComprobarConexionSocketCerrada conexion = new ComprobarConexionSocketCerrada();

	private int numeroIntentos = 0;

	/**
	 * Hilo encargado de la gestion de los sockets, primero de todo inicia el socket
	 * y comprueba que la conexion es exitosa, si es existosa se va a iniciar el
	 * hilo completamente y cada 5 segundos va a comprobar si la conexion con el
	 * socket funciona correctamente
	 */
	public void run() {
		logger.info("Se ha iniciado el hilo de conexion al servidor");

		// Iniciamos la conexion al socket

		if (new ConectarAlServidor().crearConexion()) {
			while (true) {
				if (ClasesEstaticas.getSocket() == null || conexion.comprobar()) {
					// Cerramos el recurso de la clases estaticas
					cerrarRecursosSocket();

					// Creamos el nuevo socket
					logger.warn("El socket se ha desconectado, se vuelve a realizar la conexion");
					new ConectarAlServidor().crearConexion();

					// Alertamos al usuario si llevamos un multiplo de 5 intentos
					alertarUsuario();
				}

				// Esperamos 5 segundos y volvemos a comprobar el estado del socket
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					logger.error("Ha ocurrido un error al realizar la espera del hilo de conexion al servidor");
				}

			}
		} else {
			logger.error(
					"Ha ocurrido un error en la conexion al socket y no se ha iniciado el hilo de comprobacion de conexion");
			new DialogoMostrarMensajeMetodos().mostrarMensaje(
					"Ha ocurrido un error en la conexion al socket y no se ha iniciado el hilo de comprobacion de conexion");
		}

	}

	/**
	 * Metodo que se inicia cuando ocurre un error cerrando los recursos del socket
	 * y borrando el socket de la {@link ClasesEstaticas}
	 */
	private void cerrarRecursosSocket() {

		try {
			ClasesEstaticas.getSocket().getSocketCliente().close();
			logger.debug("Se ha cerrado el socket de la clase estatica");
			ClasesEstaticas.getSocket().getInput().close();
			logger.debug("Se ha cerrado el input de la clase estatica");
			ClasesEstaticas.getSocket().getOutput().close();
			logger.debug("Se ha cerrado el output de la clase estatica");
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al cerrar los datos de la clase estatica", e);
		}

		ClasesEstaticas.setSocket(null);
		logger.debug("Se ha puesto en estado null el socket de la clase estatica");
	}

	/**
	 * Metodo que aumenta en 1 el numero de intentos de conexion, en caso de ser 5 o
	 * multiplo de 5 intentos se va a notificar al usuario que esta ocurriendo un
	 * problema con el socket
	 */
	private void alertarUsuario() {
		numeroIntentos++;
		// Alertamos al usuario si llevamos 5 intentos
		if (numeroIntentos % 5 == 0) {
			logger.info("Se han realizado {} intentos de conexion sin exito durante la ejecucion", numeroIntentos);
			new DialogoMostrarMensajeMetodos().mostrarMensaje(
					"Se han realizado " + numeroIntentos + " intentos de conexion sin exito durante la ejecucion");
		}
	}

}
