package auxiliares.inicioAplicacion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;

/**
 * Metodo que realiza la connexion y la descarga de todos los ficheros
 * necesarios para el sistema
 * 
 * @author Alejandro Perellón López
 */
public class FTPDownloader {

	private static final Logger logger = LogManager.getLogger(FTPDownloader.class);

	/**
	 * Metodo que ejecuta multiples hilos para descargar las diferentes carpetas
	 * simultaneamente
	 * 
	 * @return TRUE si se ha ejecutado correctamente el proceso
	 */
	public boolean descargarImagenesServidor() {
		Configuracion config = ConfiguracionInicial.get();
		String rutaLocal = config.getDirectorioLocal() + File.separator + "imagenes";
		String directorioRemoto = config.getFtpDirectorioRemoto();

		String[] remoto = { directorioRemoto, (directorioRemoto + "/128"), (directorioRemoto + "/64"),
				(directorioRemoto + "/256"), (directorioRemoto + "/ImagenExtra"),
				(directorioRemoto + "/ImagenIngredientes") };

		String[] ficheros = { rutaLocal, (rutaLocal + "/128"), (rutaLocal + "/64"), (rutaLocal + "/256"),
				(rutaLocal + "/ImagenExtra"), (rutaLocal + "/ImagenIngredientes") };

		List<Thread> listaHilos = new ArrayList<Thread>();

		for (int i = 0; i < ficheros.length; i++) {
			final int idx = i;
			listaHilos.add(new Thread(() -> {
				iniciarConexionYDescargar(ficheros[idx], remoto[idx]);
			}));

			// Iniciamos el hilo de conexion
			listaHilos.getLast().start();
			logger.debug("Se ha iniciado el hilo de conexion al servidor");
		}

		// Realizamos el bucle de espera
		for (Thread thread : listaHilos) {
			// Iniciamos la espera
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Ha ocurrido un error al hacer join a los hilos de la conexion FTP", e);
				return false;
			}
		}

		return true;

	}

	/**
	 * Metodo que principalmente realiza la conexion con el servidor
	 * 
	 * @return TRUE en caso de que se haya realizado la conexion y la descarga de
	 *         todos los ficheros || FALSE en caso de que ocurra algun error al
	 *         descargar los ficheros o establecer conexion con el servidor FTP
	 */
	public boolean iniciarConexionYDescargar(String rutaLocal, String directorioRemoto) {
		Configuracion config = ConfiguracionInicial.get();

		// Configuramos la conexion con el servidor
		String servidor = config.getDatosFTP().getDireccionIp();
		int puerto = config.getDatosFTP().getPuerto();
		String usuario = config.getDatosFTP().getUsuario();
		String contrasena = config.getDatosFTP().getContrasena();

		logger.info("Conectando a servidor FTP: {}:{}", servidor, puerto);
		logger.info("Ruta local de destino: {}", rutaLocal);

		FTPClient ftpClient = new FTPClient();

		try {
			ftpClient.connect(servidor, puerto);
			boolean login = ftpClient.login(usuario, contrasena);

			if (login) {
				logger.info("Conexión FTP exitosa");

				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

				// Descargamos todos los ficheros de la aplicacion
				boolean estadoDescarga = descargarDirectorio(ftpClient, directorioRemoto, rutaLocal);
				// Nos desconectamos del servidor
				ftpClient.logout();
				logger.info("Desconectado del servidor FTP.");

				// Retornamos true en caso de que la descargar se haya realizado correctamente
				if (estadoDescarga) {
					logger.info("Se han descargado todos los datos del servidor FTP");
					return true;
				} else {
					logger.info("NO se han descargado todos los datos del servidor FTP");
				}

			} else {
				logger.error("Fallo en el login FTP");
			}

		} catch (IOException e) {
			logger.error("Error en la conexión FTP", e);
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("ERROR: Ha ocurrido un error al realizar la conexion con el servidor FTP");
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("Error al cerrar la conexión FTP", e);
				}
			}
		}
		return false;
	}

	/**
	 * Metodo encargado de obtener todos los datos e imagenes necesarios del
	 * servidor FTP
	 * 
	 * @param ftpClient        es el cliente FTP
	 * @param directorioRemoto es el directo del servidor que se va a descargar
	 * @param rutaLocal        es el fichero local en el que se van a descargar los
	 *                         datos
	 * @return TRUE en caso de que se hayan descargado los ficheros correctamente
	 * @throws IOException error producido durante la descarga de los ficheros
	 */
	private boolean descargarDirectorio(FTPClient ftpClient, String directorioRemoto, String rutaLocal)
			throws IOException {
		FTPFile[] archivos = ftpClient.listFiles(directorioRemoto);

		File carpetaLocal = new File(rutaLocal);
		if (!carpetaLocal.exists()) {
			boolean creada = carpetaLocal.mkdirs();
			if (creada)
				logger.info("Carpeta creada: {}", carpetaLocal.getAbsolutePath());
		}

		for (FTPFile archivo : archivos) {
			String nombreArchivo = archivo.getName();

			if (nombreArchivo.equals(".") || nombreArchivo.equals("..")) {
				continue;
			}

			String rutaRemota = directorioRemoto + "/" + nombreArchivo;
			String rutaLocalArchivo = rutaLocal + File.separator + nombreArchivo;

			if (archivo.isDirectory()) {
				// descargarDirectorio(ftpClient, rutaRemota, rutaLocalArchivo);
			} else {
				try (OutputStream outputStream = new FileOutputStream(rutaLocalArchivo)) {
					boolean exito = ftpClient.retrieveFile(rutaRemota, outputStream);
					if (exito) {
						logger.info("Descargado: {}", rutaRemota);
					} else {
						logger.error("Error al descargar: {}", rutaRemota);
						return false;
					}
				}
			}
		}
		return true;
	}
}
