package interfazGrafica.ventanaPrincipal;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import interfazGrafica.util.ActualizarInterfaz;
import interfazGrafica.util.Reloj;
import socket.util.ComprobarConexionSocket;

public class VentanaPrincipalMetodos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(VentanaPrincipalMetodos.class);

	public void inciarVentana() {
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.getPanelPedidos().setLayout(new GridLayout(2, 4, 10, 10));

		// Iniciamos el reloj
		new Reloj(ventana.getLabelReloj()).start();

		// Establecemos la ventana principal en la clase
		ClasesEstaticas.setVentana(ventana);

		// Establecemos el puesto
		establecerPuesto();

		// Mostramos todos los elementos en la interfaz
		new ActualizarInterfaz().actualizar();

		// Hacemos la ventana visible
		ventana.setVisible(true);

		logger.debug("Se ha cargado la ventana principal");

	}

	private void establecerPuesto() {
		switch (ClasesEstaticas.getRolcliente()) {
		case 1:
			ClasesEstaticas.getVentana().getPuesto().setText("Expeditor");
			logger.debug("Se ha establecido el puesto en expeditor");
			break;
		case 2:
			ClasesEstaticas.getVentana().getPuesto().setText("Cocina");
			logger.debug("Se ha establecido el puesto en cocina");
			break;
		case 3:
			ClasesEstaticas.getVentana().getPuesto().setText("Bebidas");
			logger.debug("Se ha establecido el puesto en bebidas");
			break;
		}

	}

	/**
	 * Metodo encargado de aplicar el look and feel en la ventana
	 */
	public static void aplicarLookAndFeel() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			logger.debug("Se ha establecido el look and feel de windows");
		} catch (Exception e) {
			System.out.println("No se pudo aplicar el estilo Windows. Se usará el predeterminado.");
		}
	}

	/**
	 * Metodo que muestra en la parte inferior de la {@link VentanaPrincipal} si se
	 * ha establecido la conexion con el servidor
	 */
	public void cambiarEstadoConexion() {
		JLabel label = ClasesEstaticas.getVentana().getLabelEstadoConexion();

		// Si la conexion es correcta se va a notificar en la interfaz
		if (new ComprobarConexionSocket().comprobar()) {
			label.setForeground(Color.green);
			label.setText("Conectado");
			logger.debug("Se ha establecido el estado de la conexion a conectado");
		} else {
			label.setForeground(Color.red);
			label.setText("Desconectado");
			logger.debug("Se ha establecido el estado de la conexion a desconectado");
		}
	}

	/**
	 * Metodo que se cambia cuando se esta iniciando la conexion
	 */
	public void establecerConexionPendiente() {
		JLabel label = ClasesEstaticas.getVentana().getLabelEstadoConexion();
		label.setForeground(Color.orange);
		label.setText("Estableciendo conexion...");
		logger.debug("Se esta estableciendo conexion con el servidor");
	}
}
