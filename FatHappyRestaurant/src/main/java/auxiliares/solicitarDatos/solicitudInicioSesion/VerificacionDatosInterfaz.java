package auxiliares.solicitarDatos.solicitudInicioSesion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;

public class VerificacionDatosInterfaz implements VerificacionDatos {

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
			} else {
				// Si es un numero pero no esta en el rango
				new DialogoMostrarMensajeMetodos()
						.mostrarMensaje("El puerto introducido no esta en el rango solicitado");
			}
		} catch (NumberFormatException e) {
			// Si el puerto no es un numero
			new DialogoMostrarMensajeMetodos().mostrarMensaje("El puerto introducido no es un numero");
		}
		return false;
	}

	@Override
	public boolean verificarDireccionIP(String direccionIP) {
		// Comprobamos si la direccion es una direccion existente
		try {
			// Si no lanza exception es una ip validad
			if (InetAddress.getByName(direccionIP) != null) {
				// Ciframos la contraseña y la retornamos
				return true;
			}
		} catch (UnknownHostException e) {
			return false;
		}
		return false;
	}

}
