package auxiliares.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.solicitarDatos.solicitudInicioSesion.DatosInicioSesion;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.CifradoDatos;

public class HibernateUtil {

	// Obtenemos los datos de la BBDD
	private static final DatosInicioSesion INICIO = ConfiguracionInicial.get().getDatosBBDD();
	private static final String DATABASE = "restaurante2";

	// Declaramos el cifrado de datos
	private static final CifradoDatos CIFRADO = ClasesEstaticas.getCifrado();

	// Declaramos el logger
	private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

	// Declaramos el sessionFactory
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// 1) Carga la cfg estática
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");

			// 2) Monta la URL dinámica descrifando la direccion
			String jdbcUrl = String.format("jdbc:mariadb://%s:%d/%s", CIFRADO.desCifrarTexto(INICIO.getDireccionIp()),
					INICIO.getPuerto(), DATABASE);

			// 3) Inyecta URL, usuario y contraseña en la misma Configuration
			configuration.setProperty("hibernate.connection.url", jdbcUrl);
			configuration.setProperty("hibernate.connection.username", CIFRADO.desCifrarTexto(INICIO.getUsuario()));
			configuration.setProperty("hibernate.connection.password", CIFRADO.desCifrarTexto(INICIO.getContrasena()));

			// 4) Usamos esta configuración (con las properties dinámicas) en lugar de
			// instanciar una nueva.
			return configuration.buildSessionFactory();

		} catch (Throwable e) {
			logger.error("Ha ocurrido un error al iniciar el sessionFactory", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/** Devuelve el singleton SessionFactory */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/** Cierra el SessionFactory al apagar la app */
	public static void close() {
		sessionFactory.close();
	}
}
