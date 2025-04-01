package caja.dao;

import caja.modelo.Caja;

public interface CajasDao {

	/**
	 * Este metodo almacena los datos de la caja
	 * 
	 * @param cj es la caja que se va a almacenar en el medio no volatil
	 */
	public void insertarCaja(Caja caja);

	
}
