package auxiliares.solicitarDatos.tecladoPantalla;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que inicia y configura el teclado en pantalla
 *
 * @author Alejandro Perellón López
 */
public class TecladoEnPantallaMetodos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(TecladoEnPantallaMetodos.class);

	// Establecemos el dialogo del teclado en pantalla
	private TecladoEnPantalla interfaz;

	public TecladoEnPantallaMetodos(TecladoEnPantalla interfaz) {
		this.interfaz = interfaz;
	}

	public String iniciarTecladoPantalla() {
		// Iniciamos el teclado en pantalla
		TecladoEnPantalla teclado = new TecladoEnPantalla();
		// Hacemos visible el teclado en pantalla
		teclado.setVisible(true);

		// Una vez que continua la ejecucion retornamos la cadena de caracteres
		return teclado.getCadena().toString();
	}

	/**
	 * Metodo que añade a la cadena de texto la letra introducida
	 * 
	 * @param letra es la letra que se va a anadir a la cadena de caracteres
	 */
	protected void anadirLetra(String letra) {
		interfaz.getCadena().append(letra.toUpperCase());
		interfaz.getTextoIntroducido().setText(interfaz.getCadena().toString());
		logger.debug("Se ha añadido la letra '{}' en el texto");
	}

	/**
	 * Metodo que borrar el ultimo caracter
	 */
	protected void borrarCaracter() {
		// Comprobamos que la cadena no este vacia
		if (!interfaz.getCadena().isEmpty()) {
			interfaz.getCadena().delete(interfaz.getCadena().length() - 1, interfaz.getCadena().length());
			interfaz.getTextoIntroducido().setText(interfaz.getCadena().toString());
			logger.debug("Se ha borrado la ultima letra en el texto");
		}
	}

}
