package prueba;

import models.Dao.ProductosDAO;
import models.Dao.ProductosHibernate;

public class Main {

	public static void main(String[] args) {
		ProductosDAO dao = new ProductosHibernate();

		System.out.println(dao.obtenerBebida(3011));

		System.out.println(dao.obtenerComplemento(2061));

		System.out.println(dao.obtenerExtra(500));

		System.out.println(dao.obtenerHamburguesa(1001));

		System.out.println(dao.obtenerIngrediente(400));

		System.out.println(dao.obtenerPostre(4101));

		System.out.println(dao.obtenerProducto(2105));

		System.out.println(dao.obtenerSalsa(2102));

		System.out.println(dao.listarProductos());

	}
}
