package empleados.dao;

import empleados.modelo.Empleado;
import empleados.modelo.MovimientosEmpleado;

/**
 * Metodo que realiza el data acces objet de la clase empleados
 */
public interface EmpleadosDao {
	/**
	 * Este metodo retorna el empleado introducido a traves del ID
	 * 
	 * @param id Codigo de empleado introducido
	 * @return objeto empleado
	 */
	public Empleado obtenerEmpleado(int id);

	/**
	 * Este metodo consulta si existe el empleado creado en la base de datos
	 * 
	 * @param id Codigo de empleado
	 * @return TRUE si existe el empleado || FALSE si el empleado no existe
	 */
	public boolean comprobarEmpleadoExiste(int id);

	/**
	 * Metodo que se encarga de persistir el movimiento de un empleado
	 * 
	 * @param movimiento es el movmiento que ha realizado el empleado
	 * @return TRUE si se ha persistido correctamente || FALSE si no se ha
	 *         persistido
	 */
	public boolean AlmacenarMovimientoEmpleado(MovimientosEmpleado movimiento);

}
