package socket.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.ClasesEstaticas;
import socket.utilServidor.CerrarConexionSocket;
import socket.utilServidor.ComprobarConexionSocket;
import socket.utilServidor.HiloEnviarMensajes;
import socket.utilServidor.HiloRecibirMensajes;

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

	private int numeroRestaurante = -1;

	private int rolCliente = -1;

	private HiloRecibirMensajes recibirMensajes;

	private final ScheduledExecutorService pingExec = Executors.newSingleThreadScheduledExecutor();

	private final BlockingQueue<Pong> colaPong = new LinkedBlockingQueue<Pong>();

	private final List<Object> listaObjetosPendientes = new CopyOnWriteArrayList<Object>();

	// Constructor
	public SocketCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;

		try {
			this.socketCliente.setSoTimeout(120000);
			logger.debug("Se ha establecido un tiempo de espera de 20 minutos en el socket del cliente");

			this.socketCliente.setKeepAlive(true);
			logger.debug("Se ha establecido el keep alive en el socket");

			// Establecemos el output en base al socket de cliente
			this.output = new ObjectOutputStream(socketCliente.getOutputStream());
			this.output.flush();
			logger.debug("Se ha iniciado el outputStrean del cliente {}", this);

			// Establecemos el input en base al socket del cliente
			this.input = new ObjectInputStream(socketCliente.getInputStream());
			logger.debug("Se ha iniciado el inputStream del cliente {}", this);

			// Cargamos el hilo de recepcion de mensajes
			logger.debug("Se esta creando el hilo de recepcion de mensajes");
			this.recibirMensajes = new HiloRecibirMensajes(this);
			recibirMensajes.start();

			// Cargamos el hilo de envio de mensajeria
			logger.debug("Se esta creando el hilo de envio de mensajes");
			new HiloEnviarMensajes(this).start();

			// Cargamos el hilo de ping pong
			arrancarPingTask();

			// Anadimos el socket a la lista de productos
			ClasesEstaticas.getListaClientes().add(this);
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al crear el input y el output del socket cliente", e);
			new CerrarConexionSocket().cerrar(this);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error no contemplado", e);
			new CerrarConexionSocket().cerrar(this);
		}

	}

	private void arrancarPingTask() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		pingExec.scheduleAtFixedRate(() -> {
			colaPong.clear();
			new ComprobarConexionSocket().comprobar(this);
		}, 0, 60, TimeUnit.SECONDS);
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

	public int getNumeroRestaurante() {
		return numeroRestaurante;
	}

	public void setNumeroRestaurante(int numeroRestaurante) {
		this.numeroRestaurante = numeroRestaurante;
	}

	public int getRolCliente() {
		return rolCliente;
	}

	public void setRolCliente(int rolCliente) {
		this.rolCliente = rolCliente;
	}

	public HiloRecibirMensajes getRecibirMensajes() {
		return recibirMensajes;
	}

	public BlockingQueue<Pong> getColaPong() {
		return colaPong;
	}

	public List<Object> getListaObjetosPendientes() {
		return listaObjetosPendientes;
	}

	@Override
	public String toString() {
		return "SocketCliente [numeroRestaurante=" + numeroRestaurante + ", rolCliente=" + rolCliente + "]";
	}

}
