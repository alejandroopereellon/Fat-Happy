package caja.dao;

import java.util.List;

import caja.modelo.Caja;
import caja.modelo.Operacion;

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

	/**
	 * Metodo para insertar operaciones en el metodo DAO
	 * 
	 * @param operacion es la operacion que se va a persistir
	 * @return TRUE en caso de que se inserte || FALSE en caso de que no se inserte
	 */
	public boolean insertarOperacion(Operacion operacion);

	/**
	 * Metodo que retorna una {@link List}a de {@link Operacion}es de la caja y
	 * restaurante
	 * 
	 * @return {@link List}a de {@link Operacion}es
	 */
	public List<Operacion> listarOperaciones();

	/**
	 * Metodo que recupera de la base de datos la caja que está en uso, añade el
	 * total de las operaciones y establece el momento de cierre de caja
	 * 
	 * @return TRUE si se ha actualizado correctamente || FALSE si no se ha
	 *         actualizado
	 */
	public boolean cerrarCaja();
}
