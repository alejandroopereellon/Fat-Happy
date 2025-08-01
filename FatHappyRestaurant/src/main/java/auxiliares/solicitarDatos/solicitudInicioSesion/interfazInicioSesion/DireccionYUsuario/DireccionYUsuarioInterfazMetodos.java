package auxiliares.solicitarDatos.solicitudInicioSesion.interfazInicioSesion.DireccionYUsuario;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.solicitarDatos.solicitudInicioSesion.InicioSesion;

/**
 * Metodo encargado de solicita desde la interfaz grafica los datos de inicio de
 * sesion aun servidor, entre los datos solicita el nombre, contraseña,
 * direccion ip y puerto de conexion
 *
 * @author Alejandro Perellón López
 */
public class DireccionYUsuarioInterfazMetodos {

	private DireccionYUsuarioInterfaz interfaz;

	public DireccionYUsuarioInterfazMetodos(DireccionYUsuarioInterfaz interfaz) {
		this.interfaz = new DireccionYUsuarioInterfaz(ConfiguracionInicial.get().getVentanaPrincipal(), true, this);
	}

	/**
	 * Metodo encargado de iniciar la {@link DireccionYUsuarioInterfaz} para obtener
	 * los datos de inicio de sesion del usuario
	 * 
	 * @param motivoSolicitud es el objetivo por el que se solicitan los datos (EJ:
	 *                        Conexion BBDD)
	 * @return {@link InicioSesion} con los datos de inicio de sesion
	 */
	public InicioSesion obtenerDatosInicioSesion(String motivoSolicitud) {
		return null;
	}
}
