package auxiliares.inicioAplicacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.grafica.InicioApp;
import auxiliares.singleton.ClasesEstaticas;
import caja.util.IniciarCaja;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import productos.util.hiloActualizacionProductos.ActualizarListaProductos;
import restaurante.dao.RestauranteDaoHibernateImpl;
import socket.util.ConectarAlServidor;
import ventanaPrincipal.InterfazVentanaPrincipal;
import ventanaPrincipal.InterfazVentanaPrincipalMetodos;

/**
 * Metodo encargado de iniciar todos los ajustes de la pagina
 */
public class InicioAplicacion {

	// Crear el logger
	static Logger logger = LogManager.getLogger(InicioAplicacion.class);

	/**
	 * Metodo que se ejecuta en el inicio de la aplicacion y realiza todos los
	 * procesos de configuracion, y almacenamiento en memoria necesarios para el
	 * correcto funcionamiento del programa
	 */
	public boolean cargarDatosAplicacion() {
		// Establecemos el escalado de las imagenes al 100%
		System.setProperty("sun.java2d.uiScale", "1.0");
		// Iniciamos la ventana de notificacion
		InicioApp grafica = new InicioApp();
		grafica.setVisible(true);

		grafica.getEstadoInicio().setText("Iniciando aplicacion...");
		logger.info("Iniciando aplicación...");
		grafica.getBarraProgreso().setValue(5);

		// Iniciar descarga de imágenes del servidor ftp
		grafica.getEstadoInicio().setText("Obteniendo imagenes del servidor");
		grafica.getBarraProgreso().setValue(15);
//		if (new FTPDownloader().iniciarConexionYDescargar()) {
//			logger.info("Se han cargado los ficheros en local");
//		} else {
//			logger.error("No se han podido cargar los ficheros en local");
//			return false;
//		}
		grafica.getBarraProgreso().setValue(20);

		// Cargar datos del restaurante
		grafica.getEstadoInicio().setText("Cargando datos del restaurante");
		ClasesEstaticas.setRestaurante(new RestauranteDaoHibernateImpl()
				.obtenerRestaurante(ConfiguracionInicial.get().getCodigoRestaurante()));
		grafica.getBarraProgreso().setValue(25);
		// Realizamos comprobacion de si el restaurante se ha podido volcar
		// correctamente
		if (ClasesEstaticas.getRestaurante() != null) {
			logger.info("Se ha cargado el restaurante con ID {}", ClasesEstaticas.getRestaurante().getIdRestaurante());
			grafica.getBarraProgreso().setValue(30);
		} else {
			logger.error("No se ha podido cargar los datos del restaurante con ID {}",
					ConfiguracionInicial.get().getCodigoRestaurante());
			return false;
		}
		grafica.getBarraProgreso().setValue(40);

		// Establecemos la conexion al socket
		grafica.getEstadoInicio().setText("Conectando al servidor");
		// new HiloComprobacionConexionSocket().start();
		if (new ConectarAlServidor().crearConexion()) {
			logger.debug("Se ha realizado la conexion al servidor");
		}
		logger.info("Se ha iniciado el hilo de conexion al socket");
		grafica.getBarraProgreso().setValue(50);

		// Establecemos el dao
		grafica.getEstadoInicio().setText("Estableciendo la obtencion de datos");
		ProductosDaoGlobal.set(new ProductosDaoHibernateImpl());
		grafica.getBarraProgreso().setValue(60);

		// Cargamos la caja, en caso de estar activa se añade la caja y el empleado
		// asignado
		grafica.getEstadoInicio().setText("Recuperando la ultima caja del sistema");
		if (new IniciarCaja().recuperarCajaInicio()) {
			logger.info("Se ha cargado la caja {}", ConfiguracionInicial.get().getNumeroCaja());
			logger.info("Se ha cargado el empleado con ID {} en la caja {}",
					ClasesEstaticas.getEmpleado().getIdEmpleado(), ConfiguracionInicial.get().getNumeroCaja());
		}
		grafica.getBarraProgreso().setValue(70);

		// Cargamos todos los productos en memoria haciendo uso del hilo
		grafica.getEstadoInicio().setText("Cargando los productos del sistema");

		// Creamos el objeto de actualizacion de productos
		ActualizarListaProductos actualizarProductos = new ActualizarListaProductos();

		// Actualizamos todos los productos
		actualizarProductos.actualizarDatos();
		grafica.getBarraProgreso().setValue(80);

		// Iniciamos el hilo de actualizacion automatica del stock
		// actualizarProductos.start();
		grafica.getBarraProgreso().setValue(85);

		// Iniciamos la ventana principal del programa
		grafica.getEstadoInicio().setText("Iniciando la ventana principal...");
		// Establecemos la ventana principal en el global
		ConfiguracionInicial.get().setVentanaPrincipal(new InterfazVentanaPrincipal());
		new InterfazVentanaPrincipalMetodos(ConfiguracionInicial.get().getVentanaPrincipal())
				.iniciarConfiguracionInicial();

		// Hacemos visible la ventana
		ConfiguracionInicial.get().getVentanaPrincipal().setVisible(true);
		grafica.getBarraProgreso().setValue(100);

		// Si todo ha funcionado correctamente
		grafica.dispose();

		return true;
	}

}
