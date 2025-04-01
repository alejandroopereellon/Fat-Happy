package empleados.modelo;

/**
 * Metodo singleton que permite obtener en cualquier momento los datos del
 * empleado para los diferentes metodos
 * 
 * @author Alejandro Perellón López
 */
public class EmpleadoDatos {
	// Dato estatico que mantiene en memoria el empleado
	private static Empleado empleadoActual;

	// Constructor vacio
	private EmpleadoDatos() {
	}

	/**
	 * Setter para almacenar en memoria el empleado
	 * 
	 * @param empleado el objeto {@link empleado} que se va a cargar en memoria
	 */
	public static void set(Empleado empleado) {
		empleadoActual = empleado;
	}

	/**
	 * Getter para obtener de memoria el empleado
	 * 
	 * @return objeto {@link empleado} al que pertenece la caja
	 */
	public static Empleado get() {
		return empleadoActual;
	}

}
