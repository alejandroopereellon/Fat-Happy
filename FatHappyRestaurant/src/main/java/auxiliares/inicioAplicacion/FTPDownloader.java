package auxiliares.inicioAplicacion;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FTPDownloader {

    private static final Logger logger = LogManager.getLogger(FTPDownloader.class);

    public void iniciarConexionYDescargar() {
        Configuracion config = ConfiguracionRestaurante.get();

        String servidor = config.getFtpHost();
        int puerto = config.getFtpPuerto();
        String usuario = config.getFtpUsuario();
        String contrasena = config.getFtpContrasena();
        String directorioRemoto = config.getFtpDirectorioRemoto();
        String rutaLocal = config.getDirectorioLocal() + File.separator + "imagenes";

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

                descargarDirectorio(ftpClient, directorioRemoto, rutaLocal);

                ftpClient.logout();
                logger.info("Desconectado del servidor FTP.");
            } else {
                logger.error("Fallo en el login FTP");
            }

        } catch (IOException e) {
            logger.error("Error en la conexión FTP", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("Error al cerrar la conexión FTP", e);
                }
            }
        }
    }

    private void descargarDirectorio(FTPClient ftpClient, String directorioRemoto, String rutaLocal) throws IOException {
        FTPFile[] archivos = ftpClient.listFiles(directorioRemoto);

        File carpetaLocal = new File(rutaLocal);
        if (!carpetaLocal.exists()) {
            boolean creada = carpetaLocal.mkdirs();
            if (creada) logger.info("Carpeta creada: {}", carpetaLocal.getAbsolutePath());
        }

        for (FTPFile archivo : archivos) {
            String nombreArchivo = archivo.getName();

            if (nombreArchivo.equals(".") || nombreArchivo.equals("..")) {
                continue;
            }

            String rutaRemota = directorioRemoto + "/" + nombreArchivo;
            String rutaLocalArchivo = rutaLocal + File.separator + nombreArchivo;

            if (archivo.isDirectory()) {
                descargarDirectorio(ftpClient, rutaRemota, rutaLocalArchivo);
            } else {
                try (OutputStream outputStream = new FileOutputStream(rutaLocalArchivo)) {
                    boolean exito = ftpClient.retrieveFile(rutaRemota, outputStream);
                    if (exito) {
                        logger.info("Descargado: {}", rutaRemota);
                    } else {
                        logger.error("Error al descargar: {}", rutaRemota);
                    }
                }
            }
        }
    }
}
