package auxiliares;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.inicioAplicacion.FTPDownloader;
import auxiliares.solicitarNumeroDecimal.SolicitarNumeroDecimal;
import caja.util.IniciarCaja;
import productos.dao.ProductosDaoGlobal;
import productos.dao.ProductosDaoHibernateImpl;
import restaurante.dao.RestauranteDaoHibernateImpl;
import restaurante.modelo.RestauranteDatos;

public class Main {

	public static void main(String[] args) {
		if (new InicioAplicacion().cargarDatosAplicacion()) {

		}
	}

}
