package ventanaPrincipal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import caja.interfazCaja.panelAccionesCaja.AccionesSobreCaja;
import caja.interfazCaja.panelAccionesCaja.AccionesSobreCajaMetodos;
import caja.interfazCaja.panelPrincipalCaja.PanelCaja;
import caja.interfazCaja.panelPrincipalCaja.PanelCajaMetodos;
import empleados.util.ActividadEmpleados;
import pedido.interfazPedido.PanelPedido;
import pedido.interfazPedido.PanelPedidoMetodos;

/**
 * Clase que contiene todos los metodos necesarios para utilizar el
 * {@link JFrame} de la {@link InterfazVentanaPrincipal}
 * 
 * @author Alejandro Perellón López
 */
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
		// configuracionPantalla();
		// Establecemos el look and feel
		aplicarLookAndFeel();
		// Hacemos la interfaz visible
		interfaz.setVisible(true);
		// Configuramos el panel de caja
		configurarPanelCaja();
		// Configuramos el panel principal
		configurarPanelPrincipal();
	}

	/**
	 * Metodo que establece el panel de la caja para que soporte la informacion del
	 * {@link PanelCaja}
	 */
	public void configurarPanelCaja() {
		// Establecemos el layout del panelCaja
		interfaz.getPanelCaja().setLayout(new BorderLayout());
		// Generamos el objeto de panelCaja
		PanelCaja panelCaja = new PanelCaja(ClasesEstaticas.getCaja());
		// Configuramos el panel de caja
		new PanelCajaMetodos(panelCaja).iniciarPanelCaja();
		// Añadimos la caja al panel principal
		new PanelUtil().insertarEnPanel(interfaz.getPanelCaja(), panelCaja);
	}

	/**
	 * Metodo que establece el panel de pedidos en el panel prinicpal
	 */
	public void configurarPanelPrincipal() {
		// Establecemos el layout del panel secundario
		interfaz.getPanelSecundario().setLayout(new FlowLayout());
		// Generamos el objeto de panelPedido
		PanelPedido panelPedido = new PanelPedido();
		// Configuramos el panel de pedido
		new PanelPedidoMetodos(panelPedido).iniciarPanelPedido();
		// Añadimos el panel pedido al panel principal
		new PanelUtil().insertarEnPanel(interfaz.getPanelSecundario(), panelPedido);

		// Anadimos el panel de pedido al singleton
		ClasesEstaticas.setPanelPedido(panelPedido);
	}

	/**
	 * Metodo que muestra el panel de administrador
	 */
	protected void mostrarPanelAdministrador() {
		if (new ActividadEmpleados().solicitarPermisos("Acceso al panel de administrador", 2)) {
			// Establecemos el layout del panel de administrador
			interfaz.getPanelSecundario().setLayout(new BorderLayout());
			// Generamos el objeto de accionCaja
			AccionesSobreCaja panelAdministrador = new AccionesSobreCaja();
			// Configuramos el panel de pedido
			new AccionesSobreCajaMetodos(panelAdministrador).iniciarConfiguracion();
			// Añadimos la el panel pedido al panel principal
			new PanelUtil().insertarEnPanel(interfaz.getPanelSecundario(), panelAdministrador);
		}
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
			logger.debug("Se ha iniciado la pantalla completa");
		} else {
			// Si no soporta pantalla completa maximizamos sin el marco
			interfaz.setExtendedState(JFrame.MAXIMIZED_BOTH);
			interfaz.setVisible(true);
			logger.debug("No soporta la pantalla completa, se maximizará sin marco");
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

}
