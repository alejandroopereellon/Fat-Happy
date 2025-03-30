package auxiliares.inicioAplicacion;

public class Configuracion {

	private int codigoRestaurante;
	private String directorioLocal;
	private String ftpHost;
	private int ftpPuerto;
	private String ftpUsuario;
	private String ftpContrasena;
	private String ftpDirectorioRemoto;

	// Getters y setters
	public int getCodigoRestaurante() {
		return codigoRestaurante;
	}

	public void setCodigoRestaurante(int codigoRestaurante) {
		this.codigoRestaurante = codigoRestaurante;
	}

	public String getDirectorioLocal() {
		return directorioLocal;
	}

	public void setDirectorioLocal(String directorioLocal) {
		this.directorioLocal = directorioLocal;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public int getFtpPuerto() {
		return ftpPuerto;
	}

	public void setFtpPuerto(int ftpPuerto) {
		this.ftpPuerto = ftpPuerto;
	}

	public String getFtpUsuario() {
		return ftpUsuario;
	}

	public void setFtpUsuario(String ftpUsuario) {
		this.ftpUsuario = ftpUsuario;
	}

	public String getFtpContrasena() {
		return ftpContrasena;
	}

	public void setFtpContrasena(String ftpContrasena) {
		this.ftpContrasena = ftpContrasena;
	}

	public String getFtpDirectorioRemoto() {
		return ftpDirectorioRemoto;
	}

	public void setFtpDirectorioRemoto(String ftpDirectorioRemoto) {
		this.ftpDirectorioRemoto = ftpDirectorioRemoto;
	}
}
