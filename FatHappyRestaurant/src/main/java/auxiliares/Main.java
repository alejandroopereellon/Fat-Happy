package auxiliares;

import auxiliares.inicioAplicacion.ConfiguracionRestaurante;
import auxiliares.inicioAplicacion.FTPDownloader;
import restaurante.dao.RestauranteDao;
import restaurante.dao.RestauranteDaoHibernateImpl;

public class Main {

	public static void main(String[] args) {
		System.out.println("Iniciando aplicación...");

		// Iniciar descarga de imágenes
		FTPDownloader downloader = new FTPDownloader();
		downloader.iniciarConexionYDescargar();

		// Obtener número de restaurante
		int codigoRestaurante = ConfiguracionRestaurante.get().getCodigoRestaurante();
		System.out.println("Código de restaurante: {}" + codigoRestaurante);

		System.out.println(new RestauranteDaoHibernateImpl().obtenerRestaurante(codigoRestaurante));

	}

}
