package caja.interfazCaja;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.mostrarMensaje.MostrarMensaje;
import caja.modelo.CajaDatos;
import caja.util.CajaBuilder;
import caja.util.CalcularOperaciones;
import caja.util.CerrarCaja;
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
		//configurarOpcionesCaja();

	}

	/*
	 * Metodo que establece las configuraciones de la caja, si la caja esta iniciada
	 * podemos cerrar la caja o consultar las operaciones de la caja
	 * 
	 * Si la caja esta cerrada damos la opcion de iniciar la caja nueva
	 */
	public void configurarOpcionesCaja() {
		if (CajaDatos.get() == null) {
			interfaz.getIniciarCaja().setEnabled(true);
			interfaz.getCerrarCaja().setEnabled(false);
			interfaz.getEstadisticasVentas().setEnabled(false);
		} else {
			interfaz.getIniciarCaja().setEnabled(false);
			interfaz.getCerrarCaja().setEnabled(true);
			interfaz.getEstadisticasVentas().setEnabled(true);
		}
	}

	/**
	 * Metodo que inicia una nueva caja
	 */
	public void iniciarCaja() {
		logger.info("Se va a iniciar una nueva caja");
		if (CajaDatos.get() == null) {
			if (new CajaBuilder().crearNuevaCaja()) {
				logger.info("Se ha iniciado la caja desde la interfaz correctamente");
				configurarOpcionesCaja();
			}
		} else {
			logger.error("Ya existe una caja iniciada");
			new MostrarMensaje("Ya existe una caja iniciada");
		}
	}

	/**
	 * Metodo que cierra una nueva caja
	 */
	protected void cerrarCaja() {
		logger.info("Se va a cerrar la caja");
		if (CajaDatos.get() != null) {
			if (new CerrarCaja().cerrarCaja()) {
				logger.info("Se ha cerrado la caja desde la interfaz correctamente");
				configurarOpcionesCaja();
			}
		} else {
			logger.error("No existe una caja iniciada");
			new MostrarMensaje("No existe una caja iniciada");
		}
	}

	/**
	 * Metodo que consulta las estadisticas de las ventas
	 */
	protected void consultarEstadisticasVentas() {
		// Obtenemos el total de ventas
		new MostrarMensaje("Total ganado : " + new CalcularOperaciones().calcularTotalOperaciones() + " Eur");
	}

	protected void consultarProductos() {
		// TODO
	}

	protected void activarProductos() {
		// TODO
	}

	protected void desactivarProductos() {
		// TODO
	}

	protected void volverPantallaPrincipal() {
		new InterfazVentanaPrincipalMetodos(ConfiguracionInicial.get().getVentanaPrincipal())
				.configurarPanelPrincipal();
	}

}
