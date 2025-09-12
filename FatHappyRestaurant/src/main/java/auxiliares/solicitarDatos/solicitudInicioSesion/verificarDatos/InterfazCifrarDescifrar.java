package auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Interfaz que muestra las clases necesarias para realizar un cifrado de datos
 * eficiente
 */
public interface InterfazCifrarDescifrar {

	/**
	 * Metodo encargado de cifrar el dato introducido
	 * 
	 * @param mensaje es el texto que se va a cifrar
	 * @return {@link String} con la cadena cifrada
	 */
	public String cifrarTexto(String texto);

	/**
	 * Metodo encargado de descifrar el dato introducido
	 * 
	 * @param mensaje es el texto que se va a descifrar
	 * @return {@link String} con la cadena descifrada
	 */
	public String desCifrarTexto(String texto);

	/**
	 * Metodo encargado de la configuracion basica del cifrado
	 * 
	 * @return {@link StandardPBEStringEncryptor} con la configuracion del cifrado
	 */
	public StandardPBEStringEncryptor configurarCifrado();

}
