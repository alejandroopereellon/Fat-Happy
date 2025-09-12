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
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.CifradoDatos;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.VerificacionDatos;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.VerificacionDatosDireccionIP;

/**
 * Metodo encargado de almacenar, cifrar y descifrar la contraseña de inicio de
 * sesion en un servicio fuera de java
 * 
 * @author Alejandro Perellón
 */
public class InicioSesion {

	private CifradoDatos cifrado;

	private VerificacionDatos verificarDireccionIP = new VerificacionDatosDireccionIP();

	private static final Logger logger = LogManager.getLogger(ConfiguracionInicial.class);

	/**
	 * Metodo que solicita el texto que se va a pedir al usuario para poder
	 * 
	 * @param texto es el texto que se usa para informar de los datos solicitados
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
