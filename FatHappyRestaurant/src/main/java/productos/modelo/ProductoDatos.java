package productos.modelo;

import java.util.List;

/**
 * Clase singleton que pemite obtener en cualquier momento los datos de todos
 * los productos para el sistema de pedidos
 * 
 * Se inicia al inio del programa con la lista de productos
 * 
 * @author Alejandro Perellón López
 */
public class ProductoDatos {

	// Dato estatico que mantiene en memoria el listado de productos
	private static ListaProductos lista;

	// Constructor vacio
	private ProductoDatos() {

	}

	/**
	 * Setter para almacenar en memoria la lista
	 * 
	 * @param listProductos el objeto {@link List} de {@link Producto} que se va a
	 *                      cargar en memoria
	 */
	public static void set(ListaProductos listaProd) {
		lista = listaProd;
	}

	/**
	 * Getter para obtener de memoria el objeto de {@link ListaProductos}
	 * 
	 * @return {@link ListaProductos}
	 */
	public static ListaProductos get() {
		return lista;

	}
}
