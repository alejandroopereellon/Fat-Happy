package auxiliares.solicitarDatos.solicitudInicioSesion;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public interface CifrarDescifrar {

	/**
	 * Metodo encargado de cifrar el dato introducido
	 * 
	 * @param texto es el texto que se va a cifrar
	 */
	public void cifrarTexto(String texto);

	/**
	 * Metodo encargado de descifrar el texto introducido
	 * 
	 * @param texto es el texto que se va a cifrar
	 */
	public void desCifrarTexto(String texto);

	/**
	 * Metodo encargado de notificar al usuario si la variable de entorno existe o
	 * no de la manera que el usuario seleccione
	 * 
	 * @param estado TRUE en caso de que la variable exista y FALSE en caso de que
	 *               la variable no exista
	 */
	public void notificarVariableEntorno(Boolean estado);

	/**
	 * Metodo encargado de la configuracion basica del cifrado
	 * 
	 * @return {@link StandardPBEStringEncryptor} con la configuracion del cifrado
	 */
	public StandardPBEStringEncryptor configurarCifrado();

}
