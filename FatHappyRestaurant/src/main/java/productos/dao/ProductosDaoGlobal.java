package productos.dao;

/**
 * Metodo singleton que establece de manera global que el dao va a ser a traves
 * de uno de las instancias de la interfaz {@link ProductosDAO}
 */
public class ProductosDaoGlobal {

	private static ProductosDAO instancia;

	private ProductosDaoGlobal() {
	}

	public static void set(ProductosDAO dao) {
		instancia = dao;
	}

	public static ProductosDAO get() {
		if (instancia == null) {
			// Por defecto: Hibernate
			instancia = new ProductosDaoHibernateImpl();
		}
		return instancia;
	}
}
