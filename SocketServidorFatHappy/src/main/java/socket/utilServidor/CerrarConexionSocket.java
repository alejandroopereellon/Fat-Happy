package socket.utilServidor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.ClasesEstaticas;
import socket.modelo.SocketCliente;

/**
 * Clase encarga de cerrar las conexiones del socket cliente
 * 
 * @author Alejandro Perellón López
 */
public class CerrarConexionSocket {
	// Crear el logger
	static Logger logger = LogManager.getLogger(EnviarRecibirObjetos.class);

	public synchronized void cerrar(SocketCliente cliente) {

		if (cliente == null)
			return;

		// Paramos el hilo
		logger.debug("Se va a cerrar el hilo de recepcion de mensajes");
		cliente.getRecibirMensajes().interrupt();
		try {
			// Cerramos la tarea de comprobacion de ping
			cliente.stopTasks();

			// Espera como mucho 2 segundos
			cliente.getRecibirMensajes().join(2000);
			cliente.getSocketCliente().close();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // vuelve a marcarse como interrumpido
		} catch (IOException e) {
			logger.error("Se ha interrumpido la ejecucion del hilo de recepcion de objetos (ERROR CONTEMPLADO)");
		}

		// Establecemos el input en null
		try {
			logger.debug("Se va a cerrar el input del cliente");
			// Comprobamos si el input es nulo
			if (cliente.getInput() != null) {
				cliente.getInput().close();
			}
			cliente.setInput(null);
			logger.debug("Se ha cerrado el input del cliente");
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al cerrar el input del socket cliente", e);
		}

		// Establecemos el output en null
		try {
			logger.debug("Se va a cerrar el output del cliente");
			// Comprobamos si el output es nulo
			if (cliente.getOutput() != null) {
				cliente.getOutput().close();
				cliente.setOutput(null);
			}
			
			logger.debug("Se ha cerrado el output del cliente");
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al cerrar el output del socket cliente", e);
		}

		// Cerramos el socket del cliente
		try {
			logger.debug("Se va a cerrar el socket del cliente");
			if (cliente.getSocketCliente() != null) {
				cliente.getSocketCliente().close();
				cliente.setSocketCliente(null);
				logger.debug("Se ha cerrado el socket del cliente");
			}

		} catch (IOException e) {
			logger.error("Ha ocurrido un error al cerrar el socket del cliente", e);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error en el cierre del socket", e);
		}

		// Recorremos el bucle de los clientes y borramos el elemento
		ClasesEstaticas.getListaClientes().remove(cliente);
	}

}
