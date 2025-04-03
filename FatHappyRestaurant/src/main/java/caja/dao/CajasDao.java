package caja.dao;

import caja.modelo.Caja;

public interface CajasDao {

	/**
	 * Este metodo almacena los datos de la caja
	 * 
	 * @param caja es la caja que se va a almacenar en el medio no volatil
	 */
	public boolean insertarCaja(Caja caja);

	/**
	 * Este metodo recupera los datos de la caja desde el medio de persistencia
	 * 
	 * @param caja es la caja que se va a recuperar en el medio no volatil
	 */
	public Caja recuperarCaja();

	/**
	 * Este metodo obtiene el numero de sesion que se debe asignar a la caja
	 */
	public int obtenerSiguienteNumeroSesion();
}
