package pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.solicitarDatos.solicitudInicioSesion.InicioSesion;

public class HibernateUtil {

	private static final InicioSesion INICIO = ConfiguracionInicial.get().getDatosBBDD();
	private static final String DATABASE = "restaurante2";

	// Crear el logger
	static Logger logger = LogManager.getLogger(HibernateUtil.class);

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		try {

			Configuration configuration = new Configuration();

			configuration.configure();

			// Construir la URL dinámica
			String jdbcUrl = String.format("jdbc:mariadb://%s:%d/%s", INICIO.getDireccionIp(), INICIO.getPuerto(),
					DATABASE);

			// Cargar datos en caliente
			configuration.setProperty("hibernate.connection.url", jdbcUrl);
			configuration.setProperty("hibernate.connection.username", INICIO.getUsuario());
			configuration.setProperty("hibernate.connection.password", INICIO.getContrasena());

			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable e) {
			logger.error("Ha ocurrido un error al iniciar el sessionFactory", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void close() {
		sessionFactory.close();
	}

}
