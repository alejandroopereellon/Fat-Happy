package auxiliares.singleton;

import restaurante.modelo.Restaurante;

public class ClasesEstaticas {

	// Dato estatico que mantiene en memoria el restaurante
	private static Restaurante restauranteActual;

	public ClasesEstaticas() {
	}

	/**
	 * Setter para almacenar en memoria el restaurante
	 * 
	 * @param restaurante el objeto {@link Restaurante} que se va a cargar en
	 *                    memoria
	 */
	public static void set(Restaurante restaurante) {
		restauranteActual = restaurante;
	}

	/**
	 * Getter para obtener de memoria el restaurante
	 * 
	 * @return objeto {@link Restaurante} al que pertenece la caja
	 */
	public static Restaurante get() {
		return restauranteActual;
	}
}
