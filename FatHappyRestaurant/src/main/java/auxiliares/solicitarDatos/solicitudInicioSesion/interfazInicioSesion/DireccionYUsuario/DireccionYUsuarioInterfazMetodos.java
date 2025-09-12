package auxiliares.solicitarDatos.solicitudInicioSesion.interfazInicioSesion.DireccionYUsuario;

import auxiliares.solicitarDatos.solicitudInicioSesion.DatosInicioSesion;
import auxiliares.solicitarDatos.solicitudInicioSesion.InicioSesion;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.CifradoDatos;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.VerificacionDatosDireccionIP;

/**
 * Metodo encargado de solicita desde la interfaz grafica los datos de inicio de
 * sesion aun servidor, entre los datos solicita el nombre, contraseña,
 * direccion ip y puerto de conexion
 *
 * @author Alejandro Perellón López
 */
public class DireccionYUsuarioInterfazMetodos {

	private CifradoDatos cifrado = new CifradoDatos();

	/**
	 * Creamos la interfaz de usuario solicitando los datos
	 */
	private DireccionYUsuarioInterfaz interfaz = new DireccionYUsuarioInterfaz(null, true, this, "");

	protected boolean verificarDatos() {
		VerificacionDatosDireccionIP verificadorDireccion = new VerificacionDatosDireccionIP();
		// Comprobamos si los datos de inicio de sesion son correctos
		/**
		 * En caso de ser necesario un formato de contraseña y demas se pondrian aqui
		 * las necesidades
		 */

		// Verificamos la direccion IP
		if (!verificadorDireccion.verificarDireccionIP(interfaz.getDireccionIP().getText())) {
			return false;
		}

		// Verificamos el puerto
		if (!verificadorDireccion.verificarPuerto(interfaz.getPuerto().getText())) {
			return false;
		}

		return true;
	}

	/**
	 * Metodo encargado de iniciar la {@link DireccionYUsuarioInterfaz} para obtener
	 * los datos de inicio de sesion del usuario
	 * 
	 * @param motivoSolicitud es el objetivo por el que se solicitan los datos (EJ:
	 *                        Conexion BBDD)
	 * @return {@link InicioSesion} con los datos de inicio de sesion
	 */
	public DatosInicioSesion obtenerDatosInicioSesion(String motivoSolicitud) {

		// Establecemos el titulo de la ventana
		interfaz.getTitulo().setText(motivoSolicitud);

		/** Iniciamos un nuevo objeto de datosInicioSesion */
		DatosInicioSesion datos = new DatosInicioSesion();

		// Hacemos visible la interfaz de usuario, al cerrarse continua el codigo
		interfaz.setVisible(true);

		// Almacenamos los datos cifrados de la interfaz en datosInicioSesion
		// Usuario
		datos.setUsuario(cifrado.cifrarTexto(interfaz.getUsuario().getText()));
		// Contraseña
		datos.setContrasena(cifrado.cifrarTexto(new String(interfaz.getContrasena().getPassword())));
		// Puerto (Ya verificado)
		datos.setPuerto(Integer.parseInt(interfaz.getPuerto().getText()));
		// Direccion IP (Ya verificado)
		datos.setDireccionIp(cifrado.cifrarTexto(interfaz.getDireccionIP().getText()));

		// Retornamos los datos
		return datos;
	}
}
