package auxiliares.solicitarDatos.solicitudInicioSesion;

/**
 * Metodo encargado de almacenar cifrados los datos de inicio de sesion de un
 * servicio o aplicacion
 * 
 * @author Alejandro Perellón López
 */
public class DatosInicioSesion {

	private String direccionIp;
	private int puerto;
	private String usuario;
	private String contrasena;

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

}
