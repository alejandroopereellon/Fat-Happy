package socket.utilServidor;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.modelo.SocketCliente;

/**
 * Metodo que se ejecuta inicialmente e inicia el servidor y se encarga de que
 * siempre se este ejecutando el servidor, en caso de ocurrir algun error se va
 * a reinciar automaticamente hasta que el proceso principal se cierre
 * 
 * @author Alejandro Perellón Lópezz
 */
public class IniciarServidor extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarServidor.class);

	private static ServerSocket servidor;

	private final PurgarClientes purgadoCliente = new PurgarClientes();

	private final PurgarPedidos purgadoPedidos = new PurgarPedidos();

	/**
	 * Metodo que se encarga de iniciar y mantener iniciado el servidor para que
	 * siempre se pueda mantener una conexion al servidor en todo momentoº
	 */
	public void run() {
		while (true) {
			System.out.println("Se esta iniciando el servidor" + LocalDateTime.now());
			// Si el servidor es nulo se va a iniciar
			if (servidor == null) {
				iniciarServidor();
			}

			// Ejecutamos el tiempo de espera de 5 segundos
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("Ha ocurrido un error en el hilo de inicio de servidor", e);
			}
		}

	}

	private void iniciarServidor() {
		logger.debug("Servidor iniciado");
		try {
			servidor = new ServerSocket();

			// Evitamos que tras un cierre el puerto quede bloqueado
			servidor.setReuseAddress(true);

			servidor.bind(new InetSocketAddress("0.0.0.0", 1114));
			logger.info("Servidor escuchando en {}:{}", servidor.getInetAddress(), servidor.getLocalPort());

			// Iniciamos el purgado de usuarios
			purgadoCliente.iniciar();

			// Iniciamos el purgado de pedidos
			purgadoPedidos.iniciar();

			while (true) {
				try (Socket cliente = servidor.accept()) {
					logger.debug("Esperando cliente");
					
					/*
					 * Comprobamos si se ha aceptado el cliente e iniciamos el hilo de recepcion de
					 * mensajes
					 */
					if (cliente.isConnected()) {
						logger.debug("Se ha conectado un cliente");

						// Generamos el socket del cliente
						new SocketCliente(cliente);
						logger.debug("Se ha iniciado el socket del cliente");
					}
				} catch (SocketTimeoutException ste) {
					logger.warn("Se ha superado el tiempo de espera del servidor");
				} catch (SocketException se) {
					logger.error("ServerSocket abortado", se);
					break;
				} catch (IOException ioe) {
					logger.error("Fallo al aceptar cliente", ioe);
				} catch (OutOfMemoryError oom) {
					logger.fatal("Sin memoria para más clientes", oom);
					break;
				}
			}
		} catch (BindException be) {
			logger.fatal("Puerto 1114 ocupado", be);
		} catch (SecurityException se) {
			logger.fatal("Política de seguridad bloquea el socket", se);
		} catch (IOException ioe) {
			logger.fatal("Error al iniciar ServerSocket", ioe);
		} catch (Exception e) {
			logger.error("Ha ocurrido no contemplado en el inicio del servidor",e);
		} finally {
			System.out.println("Se ha cerrado el servidor" + LocalDateTime.now());
			// Cerramos el servidor comprobando que no sea nulo o este activo ahora mismo
			if (servidor != null && !servidor.isClosed()) {
				cerrarServidor();
			}
		}
	}

	/**
	 * Metodo encargado de cerrar el servidor
	 */
	private void cerrarServidor() {
		try {
			// Detenemos el cerrado de la tarea programada
			purgadoCliente.detener();
			purgadoPedidos.detener();

			// Detenemos el servidor
			servidor.close();
			logger.debug("Servidor detenido");

			// Borramos el servidor
			servidor = null;
			logger.debug("Se ha borrado el servidor");

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Ha ocurrido un error durante el cierre del servidor");
		} finally {
			System.out.println("Se ha cerrado la conexion al servidor");
		}

	}

}