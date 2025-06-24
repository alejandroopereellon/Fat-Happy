package auxiliares.solicitarDatos.solicitarNumeroDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GestionDecimales {
	// Crear el logger
	static Logger logger = LogManager.getLogger(GestionDecimales.class);

	/**
	 * Este metodo procesa el numero decimal para poderlo mostrar de manera correcta
	 * con dos decimales
	 * 
	 * Primero elimina las comas del numero propuesto para tener una cifra de
	 * numeros seguidos.
	 * 
	 * A continuacion comprueba que el primer numero no sea el 0, ya que la cifra
	 * propuesta es 0,00.
	 * 
	 * Una vez que se han eliminado los 0 iniciales y la coma, a continuacion se
	 * pone la coma (";") en los decimales correctos (2 decimales)
	 * 
	 * @param numero es el numero propuesto a modificacion
	 * @return {@link String} con el numero modificado
	 */
	public String procesarDecimales(String numero) {
		// Eliminamos la ","
		numero = numero.replace(".", "");

		// Comprobamos si el primer numero es un 0 y lo eliminamos
		if (numero.startsWith("0")) {
			numero = numero.substring(1);
		}

		// La ponemos en la posicion correcta (2 decimales)
		numero = new StringBuilder(numero).insert(numero.length() - 2, ".").toString();

		logger.debug("El numero formateado da un total de {}", numero);
		return numero;
	}
}
