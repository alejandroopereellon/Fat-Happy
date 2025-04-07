package ventanaPrincipal;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.utilidadesGraficas.PanelUtil;
import caja.interfazCaja.PanelCaja;
import caja.modelo.CajaDatos;

public class InterfazVentanaPrincipalMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(InterfazVentanaPrincipalMetodos.class);
	// Interfaz de la ventana principal
	private InterfazVentanaPrincipal interfaz;

	public InterfazVentanaPrincipalMetodos(InterfazVentanaPrincipal interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Metodo que carga la configuracion inicial de la interfaz principal de la
	 * aplicacion
	 */
	public void iniciarConfiguracionInicial() {
		// Establecemos resolucion a pantalla completa
		configuracionPantalla();
		// Establecemos el look and feel
		aplicarLookAndFeel();
		// Hacemos la interfaz visible
		interfaz.setVisible(true);
		// Generamos el objeto de panelCaja
		PanelCaja panelCaja = new PanelCaja(CajaDatos.get());
		// Añadimos la caja al panel principal
		new PanelUtil().insertarEnPanel(interfaz.getPanelPrincipal(), panelCaja);
	}

	/**
	 * Metodo que establece la configuracion de la pantalla, en este caso establece
	 * la pantalla completa, en caso de no se posible la amplia sin marcos
	 */
	private void configuracionPantalla() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		// Establecer la pantalla completa
		if (gd.isFullScreenSupported()) {
			gd.setFullScreenWindow(interfaz);
		} else {
			// Si no soporta pantalla completa maximizamos sin el marco
			interfaz.setExtendedState(JFrame.MAXIMIZED_BOTH);
			interfaz.setVisible(true);
		}
	}

	/**
	 * Metodo encargado de aplicar el look and feel en la ventana
	 */
	private static void aplicarLookAndFeel() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("No se pudo aplicar el estilo Windows. Se usará el predeterminado.");
		}
	}

}
