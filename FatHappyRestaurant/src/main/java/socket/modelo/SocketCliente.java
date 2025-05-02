package socket.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.util.CerrarConexionSocket;

/**
 * Clase que almacena el {@link Socket} de cliente y los
 * {@link ObjectInputStream} y {@link ObjectOutputStream} para enviar el
 * {@link PedidoSocket}
 * 
 * @author Alejandro Perellón López
 */
public class SocketCliente {
	// Crear el logger
	static Logger logger = LogManager.getLogger(SocketCliente.class);

	private Socket socketCliente;

	private ObjectOutputStream output;

	private ObjectInputStream input;

	// Constructor
	public SocketCliente(Socket socketCliente) throws IOException {
		this.socketCliente = socketCliente;

		this.socketCliente.setSoTimeout(15000);
		logger.debug("Se ha establecido un tiempo de espera de 15 segundos en el socket");

		this.socketCliente.setKeepAlive(true);
		logger.debug("Se ha establecido el keep alive en el socket");

		try {
			// Establecemos el output en base al socket de cliente
			this.output = new ObjectOutputStream(socketCliente.getOutputStream());
			this.output.flush();

			// Establecemos el inpput en base al socket del cliente
			this.input = new ObjectInputStream(socketCliente.getInputStream());
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al crear el input y el output del socket cliente", e);
			new CerrarConexionSocket().cerrar(this);
		}

	}

	// Getters
	public Socket getSocketCliente() {
		return socketCliente;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setSocketCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

}
