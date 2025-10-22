package empleados.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.solicitarDatos.solicitarNumero.SolicitarNumeroMetodos;
import empleados.dao.EmpleadoDaoHibernateImpl;
import empleados.dao.EmpleadosDao;
import empleados.modelo.Empleado;
import empleados.modelo.MovimientosEmpleado;

/**
 * Clase encargada de las acciones principales de los empleados
 */
public class ActividadEmpleados {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ActividadEmpleados.class);

	// Establecemos el dao del empleado
	EmpleadosDao dao = new EmpleadoDaoHibernateImpl();

	/**
	 * Metodo que solicita los permisos al empleado
	 * 
	 * @param motivo          motivo por el que se solicitan los permisos
	 * @param permisosMinimos es el nivel minimo de permisos para autorizar o no
	 * @return TRUE en caso de que tenga permisos || FALSE en caso de que no tenga
	 *         permisos
	 */
	public boolean solicitarPermisos(String motivo, int permisosMinimos) {
		// Obtenemos el empleado
		Empleado emp = obtenerEmpleado(motivo);
		MovimientosEmpleado movimiento = null;
		Boolean bandera = false;

		// Si el empleado no es nulo
		if (emp != null) {
			// Comprobamos si el empleado tiene los pemisos necesarios
			if (emp.getPermisos() >= permisosMinimos) {
				logger.debug("El objeto empleado con ID {} tiene permisos de acceso", emp.getIdEmpleado());
				movimiento = new MovimientosEmpleado(emp, motivo, "Empleado tiene permisos", true);
				bandera = true;
			} else {
				logger.debug("El objeto empleado con ID {} no tiene permisos de acceso", emp.getIdEmpleado());
				movimiento = new MovimientosEmpleado(emp, motivo, "Empleado no tiene permisos", false);
				new DialogoMostrarMensajeMetodos().buscarMensajes("ERROR_EMPLEADO_NO_TIENE_PERMISOS");
			}
		} else {
			movimiento = new MovimientosEmpleado(emp, motivo, "Empleado no existente u operacion cancelada", false);
			logger.error("El objeto empleado no existe en la base de datos");
			bandera = false;
			new DialogoMostrarMensajeMetodos().buscarMensajes("ERROR_EMPLEADO_NO_ENCONTRADO");
		}

		// Almacenamos el movimiento
		dao.AlmacenarMovimientoEmpleado(movimiento);

		return bandera;
	}

	/**
	 * Metodo que solicita el {@link Empleado} para obtener el objeto y aplicarlo en
	 * las situaciones necesarias como la asignacion en caja
	 * 
	 * @return {@link Empleado}
	 */
	public Empleado obtenerEmpleado(String motivo) {
		// Solicitamos el numero de empleado
		int idEmpleado = solicitarNumeroEmpleado(motivo);
		// Si el id de empleado cumple los requisitos se va a buscar
		if (idEmpleado != 0) {
			return dao.obtenerEmpleado(idEmpleado);
		}
		return null;
	}

	/**
	 * Metodo que inicia la clase {@link SolicitarNumeroMetodos} para obtener el
	 * numero de empleado, comprueba que tenga 3 cifras y lo devuelve
	 * 
	 * @return numero de empleado || 0 en caso de que no cumpla los requisitos
	 */
	private int solicitarNumeroEmpleado(String motivo) {
		// Solicitamos el numero de empleado
		logger.debug("Se va a solicitar el numero de empleado con el siguiente motivo: {}", motivo);
		int numeroEmpleado = new SolicitarNumeroMetodos(
				ClasesEstaticas.getProveedorMensaje().findMessage("MOTIVO_SOLICITAR_EMPLEADO") + System.lineSeparator()
						+ motivo)
				.solicitarNumero();

		// Comprobamos el posible numero de empleado
		if (numeroEmpleado >= 100 && numeroEmpleado < 999) {
			logger.info("El numero de empleado cumple el requisito de 3 digitos ({})", numeroEmpleado);
			return numeroEmpleado;
		}
		logger.warn("El numero de empleado no cumple el requisito de 3 digitos ({})", numeroEmpleado);
		return 0;
	}

}
