package auxiliares.inicioAplicacion;

import com.fasterxml.jackson.annotation.JsonIgnore;

import auxiliares.solicitarDatos.solicitudInicioSesion.InicioSesion;
import ventanaPrincipal.InterfazVentanaPrincipal;

public class Configuracion {

	private int codigoRestaurante;
	private String directorioLocal;
	private int numeroCaja;
	@JsonIgnore
	private InterfazVentanaPrincipal ventanaPrincipal;

	// Configuracion de base de datos
	private InicioSesion datosBBDD;

	// Configuracion de servidor FTP
	private InicioSesion datosFTP;
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

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public InterfazVentanaPrincipal getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(InterfazVentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}

	public InicioSesion getDatosBBDD() {
		return datosBBDD;
	}

	public void setDatosBBDD(InicioSesion datosBBDD) {
		this.datosBBDD = datosBBDD;
	}

	public InicioSesion getDatosFTP() {
		return datosFTP;
	}

	public void setDatosFTP(InicioSesion datosFTP) {
		this.datosFTP = datosFTP;
	}

	public String getFtpDirectorioRemoto() {
		return ftpDirectorioRemoto;
	}

	public void setFtpDirectorioRemoto(String ftpDirectorioRemoto) {
		this.ftpDirectorioRemoto = ftpDirectorioRemoto;
	}
	
	

}
