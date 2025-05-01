package socket.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Clase que almacena el {@link Socket} de cliente y los
 * {@link ObjectInputStream} y {@link ObjectOutputStream} para enviar el
 * {@link PedidoSocket}
 * 
 * @author Alejandro Perellón López
 */
public class SocketCliente {
	private Socket socketCliente;

	private ObjectOutputStream output;

	private ObjectInputStream input;

	// Constructor
	public SocketCliente(Socket socketCliente) throws IOException {
		this.socketCliente = socketCliente;

		// Establecemos el output en base al socket de cliente
		this.output = new ObjectOutputStream(socketCliente.getOutputStream());

		// Establecemos el inpput en base al socket del cliente
		this.input = new ObjectInputStream(socketCliente.getInputStream());
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

}
