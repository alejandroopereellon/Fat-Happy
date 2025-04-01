package auxiliares;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.inicioAplicacion.FTPDownloader;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import restaurante.dao.RestauranteDaoHibernateImpl;
import restaurante.modelo.RestauranteDatos;

public class Main {

	public static void main(String[] args) {
		System.out.println("Iniciando aplicación...");

		// Iniciar descarga de imágenes del servidor ftp
		FTPDownloader downloader = new FTPDownloader();
		downloader.iniciarConexionYDescargar();

		// Cargar datos del restaurante
		RestauranteDatos.set(new RestauranteDaoHibernateImpl()
				.obtenerRestaurante(ConfiguracionInicial.get().getCodigoRestaurante()));

		System.out.println("Código de restaurante: {}" + RestauranteDatos.get().getIdRestaurante());

		// Establecemos el dao
		ProductosDaoGlobal.set(new ProductosDaoHibernateImpl());

	}

}
