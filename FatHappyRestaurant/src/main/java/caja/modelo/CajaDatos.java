package caja.modelo;

/**
 * Metodo singleton que permite obtener en cualquier momento los datos del caja
 * para los diferentes metodos
 * 
 * @author Alejandro Perellón López
 */
public class CajaDatos {
	// Dato estatico que mantiene en memoria la caja
	private static Caja cajaActual;

	// Constructor vacio
	private CajaDatos() {
	}

	/**
	 * Setter para almacenar en memoria la caja
	 * 
	 * @param caja objeto {@link caja} que se va a cargar en memoria
	 */
	public static void set(Caja caja) {
		cajaActual = caja;
	}

	/**
	 * Getter para obtener de memoria el caja
	 * 
	 * @return objeto {@link caja} al que pertenece la caja
	 */
	public static Caja get() {
		return cajaActual;
	}
}
