package caja.gestionCajas;

import caja.modelo.Caja;

public class CambiarCaja {
	/**
	 * Este metodo realiza el cambio de caja, primero cierra la caja actual, y a
	 * continuacion inicia una nueva sesion de la caja
	 * 
	 * @param cj caja con la que se va a trabajar
	 * @return Caja nueva que se va a utilizar en el proceso
	 */
	public Caja cambiarCaja(Caja cj) {
		new CerrarCajas().cerrarCajas(cj);
		return new IniciarCajas().iniciarCaja(cj.getNumeroCaja());
	}
}
