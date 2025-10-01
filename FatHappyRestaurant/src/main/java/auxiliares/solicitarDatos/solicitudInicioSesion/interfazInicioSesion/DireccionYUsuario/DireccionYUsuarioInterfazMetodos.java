package auxiliares.solicitarDatos.solicitudInicioSesion.interfazInicioSesion.DireccionYUsuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.solicitarDatos.solicitudInicioSesion.DatosInicioSesion;
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

	// Declaramos los logger
	private static final Logger logger = LogManager.getLogger(DireccionYUsuarioInterfazMetodos.class);

	private CifradoDatos cifrado = new CifradoDatos();

	/**
	 * Creamos la interfaz de usuario solicitando los datos
	 */
	private DireccionYUsuarioInterfaz interfaz = new DireccionYUsuarioInterfaz(null, true, this, "");

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
		logger.debug("Se va a mostrar la interfaz de inicio de sesion");
		interfaz.setVisible(true);

		// Verificamos que los datos de inicio de sesion son correctos
//		if (!verificarDatos()) {
//			// En caso de que los datos verificados en algun momento no sean correctos se va
//			// anular el inicio de sesion
//			logger.warn("Los datos de inicio de sesion no son correctos");
//			return null;
//		}

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

	protected boolean verificarDatos() {
		VerificacionDatosDireccionIP verificadorDireccion = new VerificacionDatosDireccionIP();
		// Comprobamos si los datos de inicio de sesion son correctos
		/**
		 * En caso de ser necesario un formato de contraseña y demas se pondrian aqui
		 * las necesidades
		 */
		// Verificamos que el usuario no este vacio
		if (interfaz.getUsuario().getText().isBlank()) {
			logger.warn("El usuario esta vacio", interfaz.getDireccionIP().getText());
			new DialogoMostrarMensajeMetodos().mostrarMensaje("El usuario esta vacio");
			return false;
		}

		// Verificamos la contraseña
		if (new String(interfaz.getContrasena().getPassword()).matches(".*[a-zA-Z].*")
				&& new String(interfaz.getContrasena().getPassword()).matches(".*\\d.*")) {
			logger.warn("La contraseña debe contener un numero y letra", interfaz.getDireccionIP().getText());
			new DialogoMostrarMensajeMetodos().mostrarMensaje("La contraseña debe tener un numero y letra");
			return false;
		}

		// Verificamos la direccion IP
		if (!verificadorDireccion.verificarDireccionIP(interfaz.getDireccionIP().getText())) {
			logger.warn("La direccion ip {} es incorrecta, se va a rechazar", interfaz.getDireccionIP().getText());
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("La direccion IP debe esta en un rango entre 1.1.1.1 y 255.255.255.255");
			return false;
		}

		// Verificamos el puerto
		if (!verificadorDireccion.verificarPuerto(interfaz.getPuerto().getText())) {
			logger.warn("La el puerto {} es incorrecto, se va a rechazar", interfaz.getPuerto().getText());
			new DialogoMostrarMensajeMetodos().mostrarMensaje("El puerto debe estar en un rango entre 0 y 65535");
			return false;
		}

		logger.info("La direccion ip {} y puerto {} estan correctos", interfaz.getDireccionIP().getText(),
				interfaz.getPuerto().getText());
		return true;
	}
}
