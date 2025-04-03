package auxiliares;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.inicioAplicacion.FTPDownloader;
import caja.util.IniciarCaja;
import empleados.modelo.EmpleadoDatos;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import restaurante.dao.RestauranteDaoHibernateImpl;
import restaurante.modelo.RestauranteDatos;

/**
 * Metodo encargado de iniciar todos los ajustes de la pagina
 */
public class InicioAplicacion {

	// Crear el logger
	static Logger logger = LogManager.getLogger(InicioAplicacion.class);

	/**
	 * 
	 */
	public boolean cargarDatosAplicacion() {
		logger.info("Iniciando aplicación...");

		// Iniciar descarga de imágenes del servidor ftp
		if (new FTPDownloader().iniciarConexionYDescargar()) {
			logger.info("Se han cargado los ficheros en local");
		} else {
			logger.error("No se han podido cargar los ficheros en local");
			return false;
		}

		// Cargar datos del restaurante
		RestauranteDatos.set(new RestauranteDaoHibernateImpl()
				.obtenerRestaurante(ConfiguracionInicial.get().getCodigoRestaurante()));
		// Realizamos comprobacion de si el restaurante se ha podido volcar
		// correctamente
		if (RestauranteDatos.get() != null) {
			logger.info("Se ha cargado el restaurante con ID {}", RestauranteDatos.get().getIdRestaurante());
		} else {
			logger.error("No se ha podido cargar los datos del restaurante con ID {}",
					ConfiguracionInicial.get().getCodigoRestaurante());
			return false;
		}

		// Establecemos el dao
		ProductosDaoGlobal.set(new ProductosDaoHibernateImpl());

		// Cargamos la caja, en caso de estar activa se añade la caja y el empleado
		// asignado
		if (new IniciarCaja().recuperarCajaInicio()) {
			logger.info("Se ha cargado la caja {}", ConfiguracionInicial.get().getNumeroCaja());
			logger.info("Se ha cargado el empleado con ID {} en la caja {}", EmpleadoDatos.get().getIdEmpleado(),
					ConfiguracionInicial.get().getNumeroCaja());
		}

		return true;

	}

}
