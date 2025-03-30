package empleados.dao;

import empleados.modelo.Empleado;

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
	 * Este metodo solicita la autorizacion para realizar los procesos que requieran
	 * de un empleado un rango superior
	 * 
	 * @param id            Codigo de empleado
	 * @param nivelPermisos es el numero de nivel de permisos minimo
	 * @return TRUE si el empleado es de rango superior || FALSE si el empleado es
	 *         de rango inferior o no existe
	 */
	public boolean pedirAutorizacionEmpleado(int id, int nivelPermisoMinimo, String descripcionAutorizacion);

}
