package caja.interfazCaja.panelAccionesCaja;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.inicioAplicacion.FTPDownloader;
import auxiliares.mostrarMensaje.DialogoMostrarMensaje;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import basesDatos.panelMostrarProductos.PanelMostrarProductosMetodos;
import basesDatos.panelMostrarProductos.PanelMuestraProductos;
import caja.util.CajaBuilder;
import caja.util.CalcularOperaciones;
import caja.util.CerrarCaja;

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
		if (ClasesEstaticas.getCaja() == null && new CajaBuilder().crearNuevaCaja()) {
			// Modificamos el estado de los botones
			habilitarDeshabilitarBotonesCaja();

			logger.info("Se ha iniciado la caja desde la interfaz correctamente");
		} else {
			logger.error("Ya existe una caja iniciada");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("Ya existe una caja iniciada");
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
		if (new FTPDownloader().iniciarConexionYDescargar()) {
			logger.info("Se han cargado los ficheros en local");
			new DialogoMostrarMensaje("Se han actualizado las imagenes correctamente");
		} else {
			logger.error("No se han podido cargar los ficheros en local");
			new DialogoMostrarMensaje("No se han podido actualizar las imagenes correctamente");
		}
	}

}
