package auxiliares.solicitarDatos.solicitudInicioSesion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;

/**
 * Metodo encargado de almacenar, cifrar y descifrar la contraseña de inicio de
 * sesion en un servicio fuera de java
 * 
 * @author Alejandro Perellón
 */
public class InicioSesion {

	private VerificacionDatos verificarDireccionIP = new VerificacionDatosInterfaz();

	private String direccionIp;
	private int puerto;
	private String usuario;
	private String contrasena;

	private static final Logger logger = LogManager.getLogger(ConfiguracionInicial.class);

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
			if (puerto > 0 && puerto < 65535) {
				this.usuario = cifrarTexto(solicitarTextoACifrar(textoUsuario));
				this.contrasena = cifrarTexto(solicitarTextoACifrar(textoContrasena));
			} else {
				logger.warn("El puerto de conexion no es correcto");
				new DialogoMostrarMensajeMetodos().mostrarMensaje("El puerto de conexion no es correcto");
			}
		} else {
			logger.warn("La direccion IP introducida no es correcta");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("La direccion IP introducida no es correcta");
		}
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

		try {
			// Establecemos la contraseña
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

	// Getters && Setters

	public String getDireccionIp() {
		return direccionIp;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "InicioSesion [direccionIp=" + direccionIp + ", puerto=" + puerto + ", usuario=" + usuario
				+ ", contrasena=" + contrasena + "]";
	}

}
