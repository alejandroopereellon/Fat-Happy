package empleados.util;

import auxiliares.solicitarNumero.SolicitarNumero;
import empleados.dao.EmpleadoDaoHibernateImpl;
import empleados.dao.EmpleadosDao;
import empleados.modelo.Empleado;

/**
 * Clase encargada de obtener nuevo empleado
 */
public class ObtenerEmpleado {

	// Establecemos el dao del empleado
	EmpleadosDao dao = new EmpleadoDaoHibernateImpl();

	/**
	 * Metodo que solicita el numero de empleado para obtener el objeto
	 * {@link Empleado} y aplicarlo en las situaciones necesarias como la asignacion
	 * en caja
	 * 
	 * @return {@link Empleado}
	 */
	public Empleado nuevoEmpleado() {
		Empleado emp = null;
		int idEmpleado = solicitarIDEmpleado();
		// Si el id de empleado cunple los requisitos se va a buscar
		if (idEmpleado) {
			emp = dao.obtenerEmpleado(idEmpleado);
		}

		emp = solicitarIDEmpleado(emp);
		return emp;
	}

	/**
	 * Metodo que inicia la clase {@link SolicitarNumero} para obtener el numero de
	 * empleado y utilizarlo
	 * 
	 * @param emp {@link Empleado} que se va
	 * @return
	 */
	private Boolean solicitarIDEmpleado(Empleado emp) {
		// Solicitamos el numero de empleado
		int numeroPedido = new SolicitarNumero().solicitarNumero("Introduce el ID de empleado");

		// Comprobamos el posible numero de empleado
		if (numeroPedido >= 100 && numeroPedido < 999) {
			return true;
		}
		return false;
	}

}
