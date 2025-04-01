package auxiliares.inicioAplicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class ConfiguracionInicial {

	private static final Logger logger = LogManager.getLogger(ConfiguracionInicial.class);
	private static final String RUTA_CONFIG = System.getProperty("user.home") + File.separator + "fathappyrestaurant"
			+ File.separator + "config.json";

	private static Configuracion configuracion;

	static {
		cargarOCrearConfiguracion();
	}

	private static void cargarOCrearConfiguracion() {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		File archivo = new File(RUTA_CONFIG);

		try {
			if (!archivo.exists()) {
				logger.info("No se encontró config.json, creando configuración por defecto");
				crearConfiguracionPorDefecto(mapper, archivo);
			}
			configuracion = mapper.readValue(archivo, Configuracion.class);
			logger.info("Configuración cargada correctamente desde {}", RUTA_CONFIG);
		} catch (IOException e) {
			logger.error("Error al leer/crear la configuración: ", e);
		}
	}

	private static void crearConfiguracionPorDefecto(ObjectMapper mapper, File archivo) throws IOException {
		Configuracion porDefecto = new Configuracion();
		porDefecto.setCodigoRestaurante(0);
		porDefecto.setDirectorioLocal(System.getProperty("user.home") + File.separator + "fathappyrestaurant");
		porDefecto.setFtpHost("79.116.186.62");
		porDefecto.setFtpPuerto(1020);
		porDefecto.setFtpUsuario("restaurante");
		porDefecto.setFtpContrasena("restaurante123");
		porDefecto.setFtpDirectorioRemoto("/Imagenes");
		porDefecto.setNumeroCaja(1);

		archivo.getParentFile().mkdirs();
		mapper.writeValue(archivo, porDefecto);
		logger.info("Archivo de configuración por defecto creado");
	}

	// Acceso estático desde cualquier parte del programa
	public static Configuracion get() {
		return configuracion;
	}
}
