package socket.util;

import java.awt.Color;

import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;

public class CambiarEstadoConexion {

	// Crear el logger
	static Logger logger = LogManager.getLogger(CambiarEstadoConexion.class);

	/**
	 * Metodo que muestra en la parte inferior de la {@link VentanaPrincipal} si se
	 * ha establecido la conexion con el servidor
	 */
	public void cambiarEstadoConexion() {
		if (ClasesEstaticas.getPanelCaja() != null) {
			JLabel label = ClasesEstaticas.getPanelCaja().getEstadoConexion();

			if (ClasesEstaticas.getSocket() != null) {
				label.setForeground(Color.green);
				label.setText(ClasesEstaticas.getProveedorMensaje().findMessage("CAMBIARESTADOCONEXION_ESTADO_CONECTADO"));
				logger.debug("Se ha establecido el estado de la conexion a conectado");
			} else {
				label.setForeground(Color.red);
				label.setText(ClasesEstaticas.getProveedorMensaje().findMessage("CAMBIARESTADOCONEXION_ESTADO_DESCONECTADO"));
				logger.debug("Se ha establecido el estado de la conexion a desconectado");
			}
		}
	}
}
