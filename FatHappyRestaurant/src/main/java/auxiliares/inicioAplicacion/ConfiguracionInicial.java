package auxiliares.inicioAplicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import auxiliares.solicitarDatos.solicitarNumero.SolicitarNumeroMetodos;
import auxiliares.solicitarDatos.solicitudInicioSesion.interfazInicioSesion.DireccionYUsuario.DireccionYUsuarioInterfazMetodos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Clase que contiene todos los ajustes de configuracion inicial del programa
 * 
 * @author Alejandro Perellón López
 */
public class ConfiguracionInicial {

	private static final Logger logger = LogManager.getLogger(ConfiguracionInicial.class);
	private static final String RUTA_CONFIG = System.getProperty("user.home") + File.separator + "fathappyrestaurant"
			+ File.separator + "config.json";

	private static Configuracion configuracion;

	static {
		cargarOCrearConfiguracion();
	}

	/**
	 * Metodo encargado de cargar o crear la configuracion, primero de todo busca en
	 * la ruta principal del programa si ya existe el archivo de configuracion, en
	 * caso de no existir va a ejecutar el metodo crearconfiguracionPorDefecto
	 */
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

	/**
	 * Metodo encargado de crear la configuracion por defecto de la aplicacion,
	 * contiene los datos del restaurante al que se pertenece, contiene los
	 * usuarios, contraseñas y direcciones ip de los distintos servicios
	 * 
	 * @param mapper  es el mapper del json
	 * @param archivo es la ruta que se va a utilizar para almacenar el fichero de
	 *                configuracion
	 * @throws IOException es el {@link IOException}
	 */
	private static void crearConfiguracionPorDefecto(ObjectMapper mapper, File archivo) throws IOException {
		Configuracion porDefecto = new Configuracion();

		// Solicitamos y almacenamos los datos de la base de datos
		porDefecto.setDatosBBDD(
				new DireccionYUsuarioInterfazMetodos().obtenerDatosInicioSesion("Iniciar sesion Base de datos"));

		// Solicitamos y almacenamos los datos del servidor FTP

		porDefecto.setDatosFTP(new DireccionYUsuarioInterfazMetodos().obtenerDatosInicioSesion("Iniciar sesion FTP"));
		// Soliciamos el numero de restaurante
		porDefecto.setCodigoRestaurante(
				new SolicitarNumeroMetodos("Introduce el número de restaurante").solicitarNumero());

		// Generemos el directorio local donde se van a almacenar los datos
		porDefecto.setDirectorioLocal(System.getProperty("user.home") + File.separator + "fathappyrestaurant");

		// Establecemos la ruta del directorio remoto del servidor FTP
		porDefecto.setFtpDirectorioRemoto("/Imagenes");

		// Solicitamos el numero de caja
		porDefecto.setNumeroCaja(new SolicitarNumeroMetodos("Introduce el numero de caja").solicitarNumero());

		archivo.getParentFile().mkdirs();
		mapper.writeValue(archivo, porDefecto);
		logger.info("Archivo de configuración por defecto creado");
	}

	/**
	 * Acceso estático desde cualquier parte del programa
	 * 
	 * @return la clase de configuracion
	 */
	public static Configuracion get() {
		return configuracion;
	}
}
