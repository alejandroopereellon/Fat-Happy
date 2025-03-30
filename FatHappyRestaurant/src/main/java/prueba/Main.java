package prueba;

import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoHibernateImpl;

public class Main {

	public static void main(String[] args) {
		ProductosDAO dao = new ProductosDaoHibernateImpl();

	//	System.out.println(dao.obtenerBebida(30010101));
//
//		System.out.println(dao.obtenerComplemento(20010104));
//
//		Extra extra=  dao.obtenerExtra(500);
//		System.out.println(extra.toString());
//
//		System.out.println(dao.obtenerHamburguesa(10010101));
//
//		System.out.println(dao.obtenerIngrediente(400));
//
//		System.out.println(dao.obtenerPostre(40030501));
//
		//System.out.println(dao.obtenerProducto(10010503));
//
//	System.out.println(dao.obtenerSalsa(20050501));
//
		System.out.println(dao.listarProductos());

	}
}
