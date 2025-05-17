package socket.utilServidor;

import java.util.List;

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
public class HiloEnviarMensajes extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(HiloEnviarMensajes.class);

	private final SocketCliente cliente;

	private final EnviarRecibirObjetos enviarRecibir;

	List<Object> listaObjetosPendientes;

	public HiloEnviarMensajes(SocketCliente cliente) {
		this.cliente = cliente;
		this.enviarRecibir = new EnviarRecibirObjetos(cliente);
		this.listaObjetosPendientes = cliente.getListaObjetosPendientes();
	}

	@Override
	public void run() {
		logger.info("Iniciado el servicio de envio de objetos del cliente {}", cliente);

		try {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					// Si la lista no esta vacia se continua
					if (!listaObjetosPendientes.isEmpty()) {
						// Enviamos el primer objeto de la lista
						enviarRecibir.EnviarObjetos(listaObjetosPendientes.getFirst());
						// Eliminamos el primer objeto de la lista
						listaObjetosPendientes.removeFirst();
					}
				}
			} catch (Exception e) {
				logger.error("Ha ocurrido un error en el manejo de la lista de envio de objetos");
			}
			logger.info("Se ha cerrado el hilo de envio de objetos del cliente {}", cliente);
		} catch (Exception e) {
			logger.error("Se ha abortado el hilo de envio de objetos del cliente {}", cliente, e);
		}
	}
}
