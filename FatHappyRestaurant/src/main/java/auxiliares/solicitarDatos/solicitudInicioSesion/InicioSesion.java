package auxiliares.solicitarDatos.solicitudInicioSesion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;

/**
 * Metodo encargado de almacenar, cifrar y descifrar la contraseña de inicio de
 * sesion en un servicio fuera de java
 * 
 * @author Alejandro Perellón
 */
public class InicioSesion {

	private static final String NOMBRE_VARIABLE_ENTORNO = "java_password";

	private String direccionIp;
	private int puerto;
	private String usuario;
	private String contrasena;

	public static void main(String[] args) {
		InicioSesion ini = new InicioSesion("usuario", "contraena", "dip", "puerto");

		System.out.println(ini);
	}

	/**
	 * Metodo que solicita el texto que se va a pedir al usuario para poder
	 * 
	 * @param texto* es el texto que se usa para informar de los datos solicitados
	 */
	public InicioSesion(String textoUsuario, String textoContrasena, String textoDireccionIp, String textoPuerto) {
		this.direccionIp = verificarDireccionIp(textoDireccionIp);
		// Verificamos que la direccion ip existe
		if (direccionIp != null) {
			this.puerto = verificarPuerto(textoPuerto);
			// Verificamos que el puerto existe y es un numero
			if (puerto != 0) {
				this.usuario = cifrarTexto(solicitarTextoACifrar(textoUsuario));
				this.contrasena = cifrarTexto(solicitarTextoACifrar(textoContrasena));
			}
		}
	}

	private String verificarDireccionIp(String mensajeSolicitud) {
		// Solicitamos la direccion ip del host
		String direccion = solicitarTextoACifrar(mensajeSolicitud);

		// Comprobamos si la direccion es una direccion existente
		try {
			// Si no lanza exception es una ip validad
			InetAddress.getByName(direccion);

			// Ciframos la contraseña y la retornamos
			return cifrarTexto(direccion);
		} catch (UnknownHostException e) {
			return null;
		}
	}

	private int verificarPuerto(String mensajeSolicitud) {
		// Solicitamos el puerto del host
		String textoPuerto = solicitarTextoACifrar(mensajeSolicitud);

		// Comprobamos que el puerto es un numero exitoso
		try {

			// Convertimos el texto del puerto en un numero, si no es un numero ocurre una
			// excepcion
			int numeroPuerto = Integer.parseInt(textoPuerto);

			// Si el puerto es un numero y esta en el rango lo devolvemos
			if (numeroPuerto >= 1 && puerto <= 65535) {
				return numeroPuerto;
			} else {
				// Si es un numero pero no esta en el rango
				new DialogoMostrarMensajeMetodos()
						.mostrarMensaje("El puerto introducido no esta en el rango solicitado");
			}
		} catch (NumberFormatException e) {
			// Si el puerto no es un numero
			new DialogoMostrarMensajeMetodos().mostrarMensaje("El puerto introducido no es un numero");
		}
		return 0;
	}

	/**
	 * Metodo encargado de solicitar mediante un {@link JOptionPane} el texto que se
	 * va a cifrar
	 * 
	 * @param mensajeMotivo es el motivo para el que se solicita el texto
	 * @return {@link String} con el texto plano con los datos que se van a
	 *         solicitar
	 */
	private static String solicitarTextoACifrar(String mensajeMotivo) {
		// Solicitamos el texto a cifrar
		return JOptionPane.showInputDialog(null, mensajeMotivo, "Introduce el texto", JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Metodo encargado de cifrar la contraseña el dato introducido
	 * 
	 * @param mensaje es el texto que se va a descifrar
	 * @return {@link String} con la cadena cifrada
	 */
	private static String cifrarTexto(String mensaje) {

		StandardPBEStringEncryptor cifrar = configurarCifrado();

		return cifrar.encrypt(mensaje);
	}

	/**
	 * Metodo encargado de descifrar la contraseña el dato introducido
	 * 
	 * @param mensaje es el texto que se va a descifrar
	 * @return {@link String} con la cadena descifrada
	 */
	public static String desCifrarTexto(String mensaje) {

		StandardPBEStringEncryptor cifrar = configurarCifrado();

		return cifrar.decrypt(mensaje);
	}

	/**
	 * Metodo encargado de la configuracion basica del cifrado
	 * 
	 * @return {@link StandardPBEStringEncryptor} con la configuracion del cifrado
	 */
	private static StandardPBEStringEncryptor configurarCifrado() {
		// Creamos el objeto de cifrado en el sistema
		StandardPBEStringEncryptor cifrar = new StandardPBEStringEncryptor();

		// Establecemos el algoritmo de cifrado
		cifrar.setAlgorithm("PBEWithHmacSHA512AndAES_256");

		// Establecemos la contraseña
		cifrar.setPassword(System.getenv(NOMBRE_VARIABLE_ENTORNO));

		// Indicamos que cada cifrado usará un IV (vector de inicialización) aleatorio
		cifrar.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
		return cifrar;
	}

	// Getters && Setters
	public String getUsuario() {
		return desCifrarTexto(usuario);
	}

	public void setUsuario(String usuario) {
		this.usuario = cifrarTexto(usuario);
	}

	public String getContrasena() {
		return desCifrarTexto(contrasena);
	}

	public void setContrasena(String contrasena) {
		this.contrasena = cifrarTexto(contrasena);
	}

	public String getDireccionIp() {
		return desCifrarTexto(direccionIp);
	}

	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	@Override
	public String toString() {
		return "InicioSesion [direccionIp=" + direccionIp + ", puerto=" + puerto + ", usuario=" + usuario
				+ ", contrasena=" + contrasena + "]";
	}

}
