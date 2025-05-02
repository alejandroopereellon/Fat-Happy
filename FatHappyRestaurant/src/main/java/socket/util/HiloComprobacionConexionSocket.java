package socket.util;

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
		new ConectarAlServidor().crearConexion();

		while (true) {
			if (ClasesEstaticas.getSocket() == null || conexion.comprobar()) {

				// Si el socket es diferente de null se cierra
				if (ClasesEstaticas.getSocket() != null) {
					logger.debug("El socket se ha quedado sin conexion, se van a limpiar todos los datos");
					// Cerramos el recurso de la clases estaticas
					new CerrarConexionSocket().cerrar(ClasesEstaticas.getSocket());
				}

				// Creamos el nuevo socket
				new ConectarAlServidor().crearConexion();

				// Alertamos al usuario si llevamos un multiplo de 5 intentos
				alertarUsuario();
			}

			// Esperamos 5 segundos y volvemos a comprobar el estado del socket
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error("Ha ocurrido un error al realizar la espera del hilo de conexion al servidor", e);
				this.interrupt();
				logger.debug("Se ha interrumpido el hilo por seguridad");
				break;
			}
		}
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
