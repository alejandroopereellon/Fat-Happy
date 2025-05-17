package socket.utilServidor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.modelo.SocketCliente;

/**
 * Clase que ejecuta un hilo encargado de recibir los objetos enviados por el
 * {@link SocketCliente} y segun el tipo de {@link Object}o que sea se va a
 * realizar una tarea u otra
 * 
 * @author Alejandro Perellón López
 */
public class HiloRecibirMensajes extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(HiloRecibirMensajes.class);

	private final SocketCliente cliente;

	private final EnviarRecibirObjetos enviarRecibir;

	public HiloRecibirMensajes(SocketCliente cliente) {
		this.cliente = cliente;
		this.enviarRecibir = new EnviarRecibirObjetos(cliente);
	}

	@Override
	public void run() {
		logger.info("Iniciado el servicio de recepcion de objetos del cliente {}", cliente);

		try {
			while (!Thread.currentThread().isInterrupted()) {
				enviarRecibir.RecibirObjetos();
			}
			logger.info("Se ha cerrado el hilo de recepcion de objetos del cliente {}", cliente);
		} catch (Exception e) {
			logger.error("Se ha abortado el hilo de recepcion de objetos del cliente {}", cliente, e);
		}
	}
}
