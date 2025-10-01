package socket.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import interfazGrafica.ventanaPrincipal.VentanaPrincipalMetodos;
import socket.modelo.SocketCliente;

/**
 * Metodo que se encarga de crear una conexion con el socket, añadirla al
 * singleton
 * 
 * @author Alejandro Perellón López
 */
public class ConectarAlServidor extends Thread {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ConectarAlServidor.class);

	public void run() {
		int numeroIntentos = 0;
		// Mientras el socket sea nulo se va a intentar iniciar el socket
		do {
			// Si NO existe conexion al servidor establecemos una nueva conexion
			if (ClasesEstaticas.getSocket() != null) {
				return;
			}

			logger.info("Se va a establecer una conexion socket al servidor");

			// Establecemos la informacion del estado de la conexion
			new VentanaPrincipalMetodos().establecerConexionPendiente();

			// Creamos el socket
			Socket cliente = new Socket();

			// Configuramos el socket
			try {
				// Se va a establecer una conexion con el servidor en el puerto 5000
				cliente.connect(new InetSocketAddress("79.116.12.169", 1114), 5000);
				logger.info("Se va a stablecer conexion en la ip {}, puerto {}", cliente.getInetAddress(),
						cliente.getPort());

				if (cliente.isConnected()) {
					// Creamos el objeto socketCliente
					new SocketCliente(cliente);
					logger.debug("Se ha establecido el socket del cliente {}", cliente);
					logger.info("Se ha establecido la conexion con el servidor");

					new VentanaPrincipalMetodos().cambiarEstadoConexion();
					return;
				}
			} catch (SocketTimeoutException e) {
				logger.error("Se ha excedido el tiempo de espera del socket", e);
				new CerrarConexionSocket().cerrar();
			} catch (UnknownHostException e) {
				logger.error("No se ha podido resolver la direccion ip del servidor", e);
				new CerrarConexionSocket().cerrar();
			} catch (IOException e) {
				logger.error("Ha ocurrido un error de red", e);
				new CerrarConexionSocket().cerrar();
			} catch (Exception e) {
				logger.error("Ha ocurrido un error al crear la conexion con el socket servidor", e);
				new CerrarConexionSocket().cerrar();
			}

			// Aumentamos el numero de intentos
			numeroIntentos++;

			// Anadimos un tiempo de conexion incremental
			try {
				Thread.sleep((numeroIntentos * 1000));

				// Si llevamos 5 intentos notificamos al cliente
				if (numeroIntentos % 5 == 0) {
					JOptionPane.showMessageDialog(null,
							"Se han realizado " + numeroIntentos + " intentos de conexion al servidor sin exito",
							"Error conexion al servidor", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException e) {
				logger.error("Ha ocurrido un error en la espera del hilo de inicio de servidor", e);
			}

		} while (ClasesEstaticas.getSocket() == null);
	}
}
