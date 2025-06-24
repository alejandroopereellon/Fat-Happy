package testProductos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoHibernateImpl;
import productos.modelo.Bebida;
import productos.modelo.Producto;

public class ProductosDAOTest {
/*
	private ProductosDAO productosDAO;

	@BeforeEach
	public void setUp() {
		productosDAO = new ProductosDaoHibernateImpl();
	}

	@Test
	public void testListarProductos() {
		List<Producto> productos = productosDAO.listarProductos();
		assertNotNull(productos, "La lista de productos no debe ser nula");
		assertFalse(productos.isEmpty(), "La lista de productos no debe estar vacía");
	}

	@Test
	public void testObtenerProductoPorId() {
		Producto producto = productosDAO.obtenerProducto(20010104);
		assertNotNull(producto, "El producto con ID 20010104 no debe ser nulo");
		assertEquals(20010104, producto.getCodigo(), "El ID del producto debe ser 20010104");
	}

	@Test
	public void testObtenerBebidaPorId() {
		Bebida bebida = productosDAO.obtenerBebida(30010101);
		assertNotNull(bebida, "La bebida con ID 30010101 no debe ser nula");
		assertEquals(30010101, bebida.getCodigo(), "El ID de la bebida debe ser 30010101");
	}

	@Test
	public void testConsultarStockProducto() {
		Producto producto = productosDAO.obtenerProducto(30010101);
		assertNotNull(producto, "Producto debe existir");

		boolean modificado = productosDAO.consultarStockProducto(producto);
		assertTrue(modificado, "El stock debería haberse consultado correctamente");

		assertTrue(producto.isStockDisponible(), "El stock debería ser mayor o igual a 0");
	}*/
}
