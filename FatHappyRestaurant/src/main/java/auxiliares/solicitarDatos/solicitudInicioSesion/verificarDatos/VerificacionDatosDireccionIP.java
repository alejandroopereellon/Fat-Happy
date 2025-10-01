package auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerificacionDatosDireccionIP implements InterfazVerificacionDatos {

	private static final Logger logger = LogManager.getLogger(VerificacionDatosDireccionIP.class);

	@Override
	public boolean verificarPuerto(String numeroPuerto) {

		/**
		 * Comprobamos que el puerto es un numero entre 0 y 65,536, para ello hacemos
		 * uso de la excepciones para en caso de no haberse introducido un numero no se
		 * bloquee el sistema
		 */
		try {
			/**
			 * Convertimos el texto del puerto en un numero, si no es un numero ocurre una
			 * excepcion NumberFormatException
			 */
			int numeroPuertoVerificar = Integer.parseInt(numeroPuerto);

			// Si el puerto es un numero y esta en el rango devolvemos un true
			if (numeroPuertoVerificar >= 1 && numeroPuertoVerificar <= 65535) {
				return true;
			}
		} catch (NumberFormatException e) {
			logger.warn("El puertto {} no es un numero admitido", numeroPuerto);
		}
		return false;
	}

	@Override
	public boolean verificarDireccionIP(String direccionIP) {
		// Comprobamos si la direccion es una direccion existente
		try {
			// Si no lanza exception es una ip validad
			InetAddress.getByName(direccionIP);

			return true;
		} catch (UnknownHostException e) {
			/**
			 * En caso de ocurrir una excepcion por ser un error o no estar correcta la
			 * direccion IP
			 */
			logger.warn("La direccion IP {} no cumple los requisitos", direccionIP);
		}
		return false;
	}

}
