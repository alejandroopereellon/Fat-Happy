package caja.interfazCaja;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import caja.modelo.CajaDatos;

public class AccionesSobreCajaMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(AccionesSobreCajaMetodos.class);
	// Interfaz de la ventana principal
	private AccionesSobreCaja interfaz;

	public AccionesSobreCajaMetodos(AccionesSobreCaja interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarConfiguracion() {
		// Si la caja es nula damos la opcion de iniciar la caja y deshabilitamos la
		// opcion de
		if (CajaDatos.get() == null) {

		}
	}

	/*
	 * Metodo que establece las configuraciones de la caja, si la caja esta iniciada
	 * podemos cerrar la caja o consultar las operaciones de la caja
	 * 
	 * Si la caja esta cerrada damos la opcion de
	 */
	public void configurarOpcionesCaja() {

	}
}
