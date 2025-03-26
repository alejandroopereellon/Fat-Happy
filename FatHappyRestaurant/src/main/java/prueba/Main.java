package prueba;

import models.Bebida;
import models.Dao.ProductosDAO;
import models.Dao.ProductosHibernate;

public class Main {

	public static void main(String[] args) {
		ProductosDAO dao = new ProductosHibernate();
		
		Bebida beb = dao.obtenerBebida(3011);
		
		System.out.println(beb.toString());
	}
}
