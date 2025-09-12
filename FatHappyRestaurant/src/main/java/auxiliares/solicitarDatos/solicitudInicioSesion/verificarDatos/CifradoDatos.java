package auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;

/**
 * Clase que implementa {@link InterfazCifrarDescifrar} encargada de todo el
 * sistema de cifrado, descifrado y configuracion de claves para el cifrado de
 * los datos
 */
public class CifradoDatos implements InterfazCifrarDescifrar {

	private static final Logger logger = LogManager.getLogger(ConfiguracionInicial.class);

	/**
	 * Metodo encargado de cifrar el dato introducido
	 * 
	 * @param mensaje es el texto que se va a cifrar
	 * @return {@link String} con la cadena cifrada
	 */
	@Override
	public String cifrarTexto(String mensaje) {

		StandardPBEStringEncryptor cifrar = configurarCifrado();

		return cifrar.encrypt(mensaje);
	}

	/**
	 * Metodo encargado de descifrar el dato introducido
	 * 
	 * @param mensaje es el texto que se va a descifrar
	 * @return {@link String} con la cadena descifrada
	 */
	@Override
	public String desCifrarTexto(String mensaje) {

		StandardPBEStringEncryptor cifrar = configurarCifrado();

		return cifrar.decrypt(mensaje);
	}

	@Override
	public StandardPBEStringEncryptor configurarCifrado() {
		// Creamos el objeto de cifrado en el sistema
		StandardPBEStringEncryptor cifrar = new StandardPBEStringEncryptor();

		// Establecemos el algoritmo de cifrado
		cifrar.setAlgorithm("PBEWithHmacSHA512AndAES_256");

		try {
			// Establecemos la contraseña del cifrado
			cifrar.setPassword(System.getenv(ClasesEstaticas.getNombreVariableEntorno()));
		} catch (IllegalArgumentException e) {
			logger.error("No se ha establecido una variable de entorno con el valor");
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("No se ha establecido una variable de entorno con el valor");
		}

		// Indicamos que cada cifrado usará un IV (vector de inicialización) aleatorio
		cifrar.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
		return cifrar;
	}
}
