package socket.util;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.SocketCliente;

/**
 * Clase encarga de cerrar las conexiones del socket cliente
 * 
 * @author Alejandro Perellón López
 */
public class CerrarConexionSocket {
	// Crear el logger
	static Logger logger = LogManager.getLogger(EnviarRecibirObjetos.class);

	public synchronized void cerrar() {

		// Comprobamos si el socket es nulo
		if (ClasesEstaticas.getSocket() == null) {
			return;
		}

		SocketCliente cliente = ClasesEstaticas.getSocket();

		// Establecemos el input en null
		try {
			logger.debug("Se va a cerrar el input del cliente");
			// Comprobamos si el input es nulo
			if (cliente.getInput() != null) {
				cliente.getInput().close();
			}
			cliente.setInput(null);
			ClasesEstaticas.getSocket().setInput(null);
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
			}
			cliente.setOutput(null);
			ClasesEstaticas.getSocket().setOutput(null);
			logger.debug("Se ha cerrado el output del cliente");
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al cerrar el output del socket cliente", e);
		}

		// Paramos el hilo
		logger.debug("Se va a cerrar el hilo de recepcion de mensajes");
		cliente.getRecibirMensajes().interrupt();
		try {
			// Espera como mucho 2 segundos
			cliente.getRecibirMensajes().join(2000);

			// Cerramos la tarea de comprobacion de ping
			cliente.stopTasks();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // vuelve a marcarse como interrumpido
		}

		// Cerramos el socket del cliente
		try {
			logger.debug("Se va a cerrar el socket del cliente");
			if (cliente != null) {
				cliente.getSocketCliente().close();
				cliente.setSocketCliente(null);
			}
			ClasesEstaticas.setSocket(null);
			logger.debug("Se ha cerrado el socket del cliente");
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al cerrar el socket del cliente", e);
		}

//		JLabel label = ClasesEstaticas.getPanelCaja().getEstadoConexion();
//		label.setForeground(Color.orange);
//		label.setText("Estableciendo conexion");

		JLabel label = ClasesEstaticas.getPanelCaja().getEstadoConexion();
		label.setForeground(Color.red);
		label.setText("Desconectado");
	}

}
