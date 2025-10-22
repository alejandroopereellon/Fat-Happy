package auxiliares.inicioAplicacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.grafica.InicioApp;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import caja.util.IniciarCaja;
import multilingual_support.getMessages.MessageProvider;
import multilingual_support.loader.TranslationLoader;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import productos.util.hiloActualizacionProductos.ActualizarListaProductos;
import restaurante.dao.RestauranteDaoHibernateImpl;
import ventanaPrincipal.InterfazVentanaPrincipal;
import ventanaPrincipal.InterfazVentanaPrincipalMetodos;

/**
 * Metodo encargado de iniciar todos los ajustes de la pagina
 */
public class InicioAplicacion {

	// Crear el logger
	static Logger logger = LogManager.getLogger(InicioAplicacion.class);

	private MessageProvider language;

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

		// Cargamos en memoria las traducciones
		cargarTraducciones(grafica);

		grafica.getEstadoInicio().setText(language.findMessage("INICIANDO_APLICACION"));
		logger.info("Iniciando aplicación...");
		grafica.getBarraProgreso().setValue(5);

		// Iniciamos la configuracion
		grafica.getEstadoInicio().setText(language.findMessage("CARGA_CONFIG_INICIAL"));
		logger.info("Cargando configuracion inicial");
		grafica.getBarraProgreso().setValue(10);

		ConfiguracionInicial.get();

		// Iniciar descarga de imágenes del servidor ftp
		grafica.getEstadoInicio().setText(language.findMessage("OBTENER_IMAGENES_SERVIDOR"));
		grafica.getBarraProgreso().setValue(15);
		Thread hiloFTP = new Thread(() -> {
			new FTPDownloader().descargarImagenesServidor();
		});
		// Iniciamos el hilo de conexion FTP
		hiloFTP.start();
		logger.debug("Se ha iniciado el hilo de conexion al servidor");

		grafica.getBarraProgreso().setValue(20);

		// Cargar datos del restaurante
		grafica.getEstadoInicio().setText(language.findMessage("CARGAR_DATOS_RESTAURANTE"));
		ClasesEstaticas.setRestaurante(new RestauranteDaoHibernateImpl()
				.obtenerRestaurante(ConfiguracionInicial.get().getCodigoRestaurante()));
		grafica.getBarraProgreso().setValue(25);

		// Obtenemos el restaurante, y en caso
		if (!extraerDatosRestaurante(grafica)) {
			return false;
		}

		// Establecemos el dao
		grafica.getEstadoInicio().setText(language.findMessage("OBTENER_DATOS_DAO"));
		ProductosDaoGlobal.set(new ProductosDaoHibernateImpl());
		grafica.getBarraProgreso().setValue(60);

		// Cargamos la caja, en caso de estar activa se añade la caja y el empleado
		// asignado
		grafica.getEstadoInicio().setText(language.findMessage("RECUPERAR_ULTIMA_CAJA"));
		if (new IniciarCaja().recuperarCajaInicio()) {
			logger.info("Se ha cargado la caja {}", ConfiguracionInicial.get().getNumeroCaja());
			logger.info("Se ha cargado el empleado con ID {} en la caja {}",
					ClasesEstaticas.getEmpleado().getIdEmpleado(), ConfiguracionInicial.get().getNumeroCaja());
		}
		grafica.getBarraProgreso().setValue(70);

		cargarProductos(grafica);

		try {
			hiloFTP.join();
		} catch (InterruptedException e) {
			logger.error("Ha ocurrido un error con los hilos de FTP o de obtencion de productos desde la base de datos",
					e);
		}
		extablecerConexionSocket(grafica);

		// Iniciamos la ventana principal del programa
		grafica.getEstadoInicio().setText(language.findMessage("INICIO_VENTANA_PRINCIPAL"));
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

	private void cargarTraducciones(InicioApp grafica) {
		grafica.getEstadoInicio().setText("Cargando las traducciones del sistema");
		TranslationLoader translation = new TranslationLoader(ConfiguracionInicial.get().getIdioma());
		translation.loadTranslations();
		MessageProvider provider = new MessageProvider(translation.getTranslations());
		ClasesEstaticas.setProveedorMensaje(provider);

		language = ClasesEstaticas.getProveedorMensaje();
	}

	private void extablecerConexionSocket(InicioApp grafica) {
		// Establecemos la conexion al socket
		grafica.getEstadoInicio().setText(language.findMessage("CONECTADO_SERVIDOR"));

		ClasesEstaticas.getHiloconexionservidor().start();
		logger.info("Se ha iniciado el hilo de conexion al socket");
		grafica.getBarraProgreso().setValue(50);
	}

	private boolean extraerDatosRestaurante(InicioApp grafica) {
		// Realizamos comprobacion de si el restaurante se ha podido volcar
		// correctamente
		if (ClasesEstaticas.getRestaurante() != null) {
			logger.info("Se ha cargado el restaurante con ID {}", ClasesEstaticas.getRestaurante().getIdRestaurante());
			grafica.getBarraProgreso().setValue(30);

		} else {
			logger.error("No se ha podido cargar los datos del restaurante con ID {}",
					ConfiguracionInicial.get().getCodigoRestaurante());
			new DialogoMostrarMensajeMetodos()
					.mostrarMensaje("ERROR: No se ha podido cargar los datos del restaurante");
			return false;
		}
		grafica.getBarraProgreso().setValue(40);
		return true;
	}

	private void cargarProductos(InicioApp grafica) {
		// Cargamos todos los productos en memoria
		grafica.getEstadoInicio().setText(language.findMessage("CARGA_PRODUCTOS_BBDD"));

		// Creamos el objeto de actualizacion de productos
		ActualizarListaProductos actualizarProductos = new ActualizarListaProductos();

		// Actualizamos todos los productos
		actualizarProductos.actualizarDatos();
		grafica.getBarraProgreso().setValue(80);

		// Iniciamos el hilo de actualizacion automatica del stock
		actualizarProductos.start();
		grafica.getBarraProgreso().setValue(85);
	}

}
