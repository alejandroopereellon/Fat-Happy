package socket.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import socket.util.CambiarEstadoConexion;
import socket.util.CerrarConexionSocket;
import socket.util.ComprobarConexionSocket;
import socket.util.EnviarRecibirObjetos;
import socket.util.IniciarHiloRecibirMensaje;

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

	private IniciarHiloRecibirMensaje recibirMensajes;

	private final ScheduledExecutorService pingExec = Executors.newSingleThreadScheduledExecutor();

	// Constructor
	public SocketCliente(Socket socketCliente) throws IOException {
		this.socketCliente = socketCliente;

		this.socketCliente.setSoTimeout(60000);
		logger.debug("Se ha establecido un tiempo de espera de 60 segundos en el socket");

		this.socketCliente.setKeepAlive(true);
		logger.debug("Se ha establecido el keep alive en el socket");

		try {
			// Establecemos el output en base al socket de cliente
			this.output = new ObjectOutputStream(socketCliente.getOutputStream());
			this.output.flush();

			// Establecemos el inpput en base al socket del cliente
			this.input = new ObjectInputStream(socketCliente.getInputStream());

			// Anadimos a la clase singleton el socket
			ClasesEstaticas.setSocket(this);
			logger.debug("Se ha establecido el socket del cliente en la clase estatica");

			// Enviamos los datos del rol al cliente
			new EnviarRecibirObjetos()
					.EnviarObjetos(new RolSocket(ClasesEstaticas.getRestaurante().getIdRestaurante(), 0 /* Caja */));
			logger.debug("Se ha enviado el rol al cliente");

			// Iniciamos el hilo de recepcion de mensajes
			this.recibirMensajes = new IniciarHiloRecibirMensaje();
			this.recibirMensajes.start();
			logger.debug("Se ha iniciado el hilo de recepcion de mensajes del socket");

			arrancarPingTask();
			logger.debug("Se ha iniciado la task de comprobacion de ping");

			// Informamos del estado de la conexion en la interfaz
			new CambiarEstadoConexion().cambiarEstadoConexion();

		} catch (IOException e) {
			logger.error("Ha ocurrido un error al crear el input y el output del socket cliente", e);
			new CerrarConexionSocket().cerrar();
		}

	}

	private void arrancarPingTask() {
		pingExec.scheduleAtFixedRate(() -> {
			ClasesEstaticas.getColapong().clear();
			new ComprobarConexionSocket().comprobar();
		}, 0, 30, TimeUnit.SECONDS);
	}

	public void stopTasks() {
		pingExec.shutdownNow();
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

	public IniciarHiloRecibirMensaje getRecibirMensajes() {
		return recibirMensajes;
	}

}
