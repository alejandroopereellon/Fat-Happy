package productos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import caja.modelo.Operacion;
import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;
import productos.modelo.Producto;
import productos.modelo.ProductoVendido;

public class ProcesarProductoVendido {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ProcesarProductoVendido.class);
	// Creamos el objeto pedido
	private final Producto producto;
	// Creamos el objeto operacion
	private final Operacion operacion;
	// Creamos el dao
	ProductosDAO dao = ProductosDaoGlobal.get();

	public ProcesarProductoVendido(Producto producto, Operacion operacion) {
		this.producto = producto;
		this.operacion = operacion;
	}

	public boolean procesar() {
		ProductoVendido productoVendido = new ProductoVendido();

		// Anadimos los datos al objeto productoVendido
		// Establecemos la operacion
		productoVendido.setOperacion(operacion);
		// Establecemos el producto
		productoVendido.setProducto(producto);
		// Establecemos el restaurante
		productoVendido.setRestaurante(ClasesEstaticas.getRestaurante());
		// Establecemos los datos completos
		productoVendido.setDatosCompletos(producto.toString());

		logger.debug("Se ha añadido el contenido al objeto productoVendido: {}", productoVendido);
		return dao.insertarProductoVendido(productoVendido);
	}
}
