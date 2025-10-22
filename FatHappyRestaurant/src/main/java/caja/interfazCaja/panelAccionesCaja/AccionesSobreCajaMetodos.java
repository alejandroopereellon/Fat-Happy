package caja.interfazCaja.panelAccionesCaja;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.inicioAplicacion.FTPDownloader;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import basesDatos.panelMostrarProductos.PanelMostrarProductosMetodos;
import basesDatos.panelMostrarProductos.PanelMuestraProductos;
import caja.util.CajaBuilder;
import caja.util.CalcularOperaciones;
import caja.util.CerrarCaja;
import empleados.util.ActividadEmpleados;
import multilingual_support.languageSelection.LanguageSelectionJOptionPane;
import socket.util.CerrarConexionSocket;
import socket.util.ConectarAlServidor;
import ventanaPrincipal.InterfazVentanaPrincipalMetodos;


/**
 * Clase que contiene los metodos de la interfaz grafica de
 * {@link AccionesSobreCaja}
 * 
 * @author Alejandro Perellón López
 */
public class AccionesSobreCajaMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(AccionesSobreCajaMetodos.class);
	// Interfaz de la ventana principal
	private AccionesSobreCaja interfaz;

	public AccionesSobreCajaMetodos(AccionesSobreCaja interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarConfiguracion() {
		// Hacemos visible la ventana
		interfaz.setVisible(true);
		// Configuramos los botones segun la caja
		habilitarDeshabilitarBotonesCaja();
		// Establecemos el checkbox segun el estado del reintento de conexion
		interfaz.getCheckReintentarConexion().setSelected(ClasesEstaticas.reconexionAutomatica);
	}

	/*
	 * Metodo que establece las configuraciones de la caja, si la caja esta iniciada
	 * podemos cerrar la caja o consultar las operaciones de la caja
	 * 
	 * Si la caja esta cerrada damos la opcion de iniciar la caja nueva
	 */
	public void habilitarDeshabilitarBotonesCaja() {		
		if (ClasesEstaticas.getCaja() == null) {
			// El boton iniciar casa se activa
			interfaz.getIniciarCaja().setEnabled(true);
			// El boton cerrar caja se desactiva
			interfaz.getCerrarCaja().setEnabled(false);
			// El boton ver estadisticas se desactiva
			interfaz.getEstadisticasVentas().setEnabled(false);
		} else {
			// El boton inidica caja se desactiva
			interfaz.getIniciarCaja().setEnabled(false);
			// El boton cerrar caja se activa
			interfaz.getCerrarCaja().setEnabled(true);
			// El boton ver estadisticas se desactiva
			interfaz.getEstadisticasVentas().setEnabled(true);
		}
	}

	/**
	 * Metodo que inicia una nueva caja
	 */
	public void iniciarCaja() {
		logger.info("Se va a iniciar una nueva caja");
		// Si la caja es nula y se ha podido crear una nueva caja
		if (ClasesEstaticas.getCaja() != null) {
			logger.info("Ya existe una caja iniciada");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("Ya existe una caja iniciada");
		} else if (ClasesEstaticas.getCaja() == null && new CajaBuilder().crearNuevaCaja()) {
			// Modificamos el estado de los botones
			habilitarDeshabilitarBotonesCaja();
			logger.info("Se ha iniciado la caja desde la interfaz correctamente");
		}
	}

	/**
	 * Metodo que cierra una nueva caja
	 */
	protected void cerrarCaja() {
		logger.info("Se va a cerrar la caja");
		// Mostramos las estadisticas de venta
		consultarEstadisticasVentas();
		// Si la caja existe y se ha cerrado correctamente
		if (ClasesEstaticas.getCaja() != null && new CerrarCaja().cerrarCaja()) {
			// Modificamos el estado de los botones
			habilitarDeshabilitarBotonesCaja();
			logger.info("Se ha cerrado la caja desde la interfaz correctamente");
		} else {
			logger.error("No existe una caja iniciada");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("No existe una caja iniciada");
		}
	}

	/**
	 * Metodo que consulta las estadisticas de las ventas
	 */
	protected void consultarEstadisticasVentas() {
		// Obtenemos el total de ventas
		new DialogoMostrarMensajeMetodos()
				.mostrarMensaje("Total ganado : " + new CalcularOperaciones().calcularTotalOperaciones() + " Eur");
	}

	protected void consultarProductos() {
		// TODO
	}

	/**
	 * Metodo que inicia el panel que permite modificar el stock de los productos
	 */
	protected void cambiarEstockProductos() {
		// Cargamos el panel de muestra de productos
		PanelMuestraProductos panel = new PanelMuestraProductos();

		// Iniciamos la configuracion del panel
		new PanelMostrarProductosMetodos(panel).mostrarProductos();

		// Añadimos el panel pedido al panel principal
		new PanelUtil().insertarEnPanel(ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario(), panel);
	}

	/**
	 * Metodo que cambia el panel actual por el principal del sistema
	 */
	protected void volverPantallaPrincipal() {
		// Añadimos el panel pedido al panel principal
		new PanelUtil().insertarEnPanel(ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario(),
				ClasesEstaticas.getPanelPedido());
	}

	/**
	 * metodo que fuerza la actualizacion de los productos desde la base de datos
	 */
	protected void actualizarImagenesServidor() {
		Thread hiloFTP = new Thread(() -> {
			if (new FTPDownloader().descargarImagenesServidor()) {
				logger.info("Se han cargado los ficheros en local");
				new DialogoMostrarMensajeMetodos().mostrarMensaje("Se han actualizado las imagenes correctamente");
			} else {
				logger.error("No se han podido cargar los ficheros en local");
				new DialogoMostrarMensajeMetodos()
						.mostrarMensaje("No se han podido actualizar las imagenes correctamente");
			}
		});
		// Iniciamos el hilo de conexion FTP
		hiloFTP.start();
		logger.debug("Se ha iniciado el hilo de conexion al servidor");
		new DialogoMostrarMensajeMetodos().mostrarMensaje("Se van a descargar las imagenes");
	}

	/**
	 * Metodo que mediante un checkbox permite la reconexion automatica al servidor
	 * o permite deshabilitarla
	 */
	protected void actualizarReconexionAutomatica() {
		if (interfaz.getCheckReintentarConexion().isSelected()) {
			ClasesEstaticas.setReconexionAutomatica(true);
			ConfiguracionInicial.get().setReconexionServidor(true);
			logger.info("Se ha establecido la reconexion automatica con el servidor");
		} else {
			ClasesEstaticas.setReconexionAutomatica(false);
			ConfiguracionInicial.get().setReconexionServidor(false);
			logger.info("Se ha deshabilitado la reconexion automatica con el servidor");
		}

		try {
			ConfiguracionInicial.almacenarConfiguracionActual();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que permite forzar la conexion al servidor en caso de ocurrir un error
	 */
	protected void forzarConexionServidor() {
		// Marcamos el check como activo
		interfaz.getCheckReintentarConexion().setSelected(true);
		actualizarReconexionAutomatica();

		if (ClasesEstaticas.getHiloconexionservidor() != null) {
			try {
				// Cerramos el hilo de conexion automatica
				ClasesEstaticas.getHiloconexionservidor().interrupt();

				ClasesEstaticas.getHiloconexionservidor().join();
				ClasesEstaticas.setHiloConexionServidor(null);
				logger.info("El hilo de conexion automatica ha terminado.");
			} catch (InterruptedException e) {
				logger.warn("El hilo de conexion automatica  actual fue interrumpido mientras esperaba.", e);
			} catch (NullPointerException e) {
				logger.warn("El hilo de conexion automatica es nulo", e);
			}
		}

		// Cerramos la conexion al servidor
		new CerrarConexionSocket().cerrar();

		// Iniciamos la conexion al servidor de nuevo
		try {
			ClasesEstaticas.setHiloConexionServidor(new ConectarAlServidor());
			logger.debug("Se ha puesto en null el hilo de conexion al servidor");
			ClasesEstaticas.getHiloconexionservidor().start();
			logger.debug("Se ha iniciado el hilo de conexion al servidor");
		} catch (NullPointerException e) {
			logger.error("Ha ocurrido un error al iniciar el hilo de conexion automatica al servidor", e);
		}

		logger.debug("Se esta reiniciado la conexion al servidor ");
		new DialogoMostrarMensajeMetodos().mostrarMensaje("Se esta reiniciado la conexion al servidor ");

	}

	/**
	 * Metodo que realiza una desconexion del servidor
	 */
	protected void desconectarDelServidor() {
		// Marcamos el check como activo
		interfaz.getCheckReintentarConexion().setSelected(false);
		actualizarReconexionAutomatica();

		new CerrarConexionSocket().cerrar();
		logger.debug("Se ha cerrado la conexion al servidor");
		new DialogoMostrarMensajeMetodos().mostrarMensaje("Se ha cerrado la conexion al servidor");

		// Modificamos el checkbox a desactivado
		interfaz.getCheckReintentarConexion().setSelected(false);
		actualizarReconexionAutomatica();
		logger.debug("Se ha deshabilitado el check de conexion automatica");
	}

	/**
	 * Metodo que abri los logs del servidor automaticamente
	 */
	protected void abrirLogsServidor() {
		try {
			String ruta = System.getProperty("user.home") + "\\fathappyrestaurant\\logs\\app.log";
			ProcessBuilder pb = new ProcessBuilder("notepad.exe", ruta);
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo enfocado en cancelar el pedido en curso
	 */
	protected void cancelarPedido() {
		// Comprobamos si el pedido esta iniciado
		if (ClasesEstaticas.getPedido() != null) {
			logger.debug("Se tiene permisos de administrador para cancelar el pedido");
			// Solicitamos permisos de encargado
			if (new ActividadEmpleados()
					.solicitarPermisos("Cancelar el pedido " + ClasesEstaticas.getPedido().getNumeroPedido(), 2)) {
				// Notificamos que se ha cancelado el pedido
				new DialogoMostrarMensajeMetodos()
						.mostrarMensaje("Se ha cancelado el pedido " + ClasesEstaticas.getPedido().getNumeroPedido());
				logger.debug("Se ha notificado la cancelacion del pedido");

				// Ponemos el pedido actual y el panel de pedido en nulo
				ClasesEstaticas.setPanelPedido(null);
				ClasesEstaticas.setPedido(null);
				logger.debug("Se han puesto en nulo el panel de pedido y el pedido");

				// Establecemos un nuevo panel de pedido y lo mostramos
				new InterfazVentanaPrincipalMetodos(ConfiguracionInicial.get().getVentanaPrincipal())
						.configurarPanelPrincipal();
				logger.info("Se ha cancelado el pedido actual");
			} else {
				new DialogoMostrarMensajeMetodos()
						.mostrarMensaje("No existen los permisos necesarios para cancelar el pedido");
				logger.debug("No hay permisos de adminsitrador para cancelar el pedido");
			}
		} else {
			new DialogoMostrarMensajeMetodos().mostrarMensaje("No hay un pedido en curso para cancelar");
			logger.debug("No hay ningun pedido activo para cancelar");
		}

	}

	void cambiarIdioma() {
		// Seleccionamos el nuevo idioma
		String idiomaNuevo = new LanguageSelectionJOptionPane().selectLanguage();

		// Cambiamos en la configuracion el nuevo idioma
		ConfiguracionInicial.get().setIdioma(idiomaNuevo);
		try {
			ConfiguracionInicial.almacenarConfiguracionActual();
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al almacenar la configuracion actual en el sistema");
			e.printStackTrace();
		}

		// Notificamos que el idioma se carga en el proximo inicio de la aplicacion
		new DialogoMostrarMensajeMetodos()
				.mostrarMensaje("The language changes on the next restart of the application.");
	}

}
