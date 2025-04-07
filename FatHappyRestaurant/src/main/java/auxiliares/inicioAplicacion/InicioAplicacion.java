package auxiliares.inicioAplicacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.grafica.InicioApp;
import caja.util.IniciarCaja;
import empleados.modelo.EmpleadoDatos;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import productos.util.ActualizarListaProductos;
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

		// Establecemos el escalado de las imagenes al 100%
		System.setProperty("sun.java2d.uiScale", "1.0");

		// Iniciamos la ventana de notificacion
		InicioApp grafica = new InicioApp();
		grafica.setVisible(true);

		// Iniciar descarga de imágenes del servidor ftp
		grafica.getEstadoInicio().setText("Obteniendo imagenes del servidor");
		grafica.getBarraProgreso().setValue(20);

		if (new FTPDownloader().iniciarConexionYDescargar()) {
			logger.info("Se han cargado los ficheros en local");
		} else {
			logger.error("No se han podido cargar los ficheros en local");
			return false;
		}

		// Cargar datos del restaurante
		grafica.getEstadoInicio().setText("Cargando datos del restaurante");
		grafica.getBarraProgreso().setValue(40);

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
		grafica.getEstadoInicio().setText("Estableciendo la obtencion de datos");
		grafica.getBarraProgreso().setValue(60);

		ProductosDaoGlobal.set(new ProductosDaoHibernateImpl());

		// Cargamos la caja, en caso de estar activa se añade la caja y el empleado
		// asignado
		grafica.getEstadoInicio().setText("Recuperando la ultima caja del sistema");
		grafica.getBarraProgreso().setValue(80);
		if (new IniciarCaja().recuperarCajaInicio()) {
			logger.info("Se ha cargado la caja {}", ConfiguracionInicial.get().getNumeroCaja());
			logger.info("Se ha cargado el empleado con ID {} en la caja {}", EmpleadoDatos.get().getIdEmpleado(),
					ConfiguracionInicial.get().getNumeroCaja());
		}

		// Cargamos todos los productos en memoria haciendo uso del hilo
		grafica.getEstadoInicio().setText("Cargando los productos del sistema");
		grafica.getBarraProgreso().setValue(100);

		new ActualizarListaProductos().start();

		// Si todo ha funcionado correctamente
		grafica.dispose();
		return true;
	}

}
