package pedido.interfazPedido;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.utilidadesGraficas.PanelUtil;
import productos.interfazPedido.casillaProducto.CasillaProducto;
import productos.interfazPedido.casillaProducto.CasillaProductoMetodos;
import productos.modelo.Producto;
import productos.modelo.ProductoDatos;

public class PanelPedidoMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelPedidoMetodos.class);
	// Establecemos el panel de pedido
	private PanelPedido interfaz;

	// Constructor
	public PanelPedidoMetodos(PanelPedido interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarPanelPedido() {
		// Cargamos inicialmente las hamburguesas
		mostrarHamburguesas();
		// Establecemos el tamano del panel de productos
		interfaz.getPanelProductos().setLayout(new GridLayout(0, 3));
		logger.debug("Se ha estableciod el layout en gridLayout");
	}

	protected void mostrarHamburguesas() {
		// Vaciamos el panel de contenido
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de bebidas
		List<Producto> listaHamburguesas = ProductoDatos.get().getListaHamburguesas();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaHamburguesas) {
			// Creamos el panel del producto
			CasillaProducto casilla = new CasillaProducto(producto, interfaz.getPedido());

			// Iniciamos los datos de la casilla
			new CasillaProductoMetodos(casilla).establecerDatosProducto();

			// Anadimos cada bebida en el panel
			new PanelUtil().insertarEnPanelSinBorrar(interfaz.getPanelProductos(), casilla);

			logger.debug("Se ha insertado la bebida {}", producto);
		}

		logger.debug("Se han insertado todas las hamburburguesas");
	}

	protected void mostrarComplementos() {
		// Vaciamos el panel de contenido
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de bebidas
		List<Producto> listaComplementos = ProductoDatos.get().getListaComplementos();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaComplementos) {
			// Creamos el panel del producto
			CasillaProducto casilla = new CasillaProducto(producto, interfaz.getPedido());

			// Iniciamos los datos de la casilla
			new CasillaProductoMetodos(casilla).establecerDatosProducto();

			// Anadimos cada bebida en el panel
			new PanelUtil().insertarEnPanelSinBorrar(interfaz.getPanelProductos(), casilla);

			logger.debug("Se ha insertado el complemento {}", producto);
		}

		logger.debug("Se han insertado todos los complementos");
	}

	protected void mostrarPostres() {
		// Vaciamos el panel de contenido
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de bebidas
		List<Producto> listaPostres = ProductoDatos.get().getListaPostres();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaPostres) {
			// Creamos el panel del producto
			CasillaProducto casilla = new CasillaProducto(producto, interfaz.getPedido());

			// Iniciamos los datos de la casilla
			new CasillaProductoMetodos(casilla).establecerDatosProducto();

			// Anadimos cada bebida en el panel
			new PanelUtil().insertarEnPanelSinBorrar(interfaz.getPanelProductos(), casilla);

			logger.debug("Se ha insertado el postre {}", producto);
		}

		logger.debug("Se han insertado todos los postres");
	}

	protected void mostrarBebidas() {
		// Vaciamos el panel de contenido
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de bebidas
		List<Producto> listaBebidas = ProductoDatos.get().getListaBebidas();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaBebidas) {
			// Creamos el panel del producto
			CasillaProducto casilla = new CasillaProducto(producto, interfaz.getPedido());

			// Iniciamos los datos de la casilla
			new CasillaProductoMetodos(casilla).establecerDatosProducto();

			// Anadimos cada bebida en el panel
			new PanelUtil().insertarEnPanelSinBorrar(interfaz.getPanelProductos(), casilla);

			logger.debug("Se ha insertado la bebida {}", producto);
		}
		logger.info("Se han cargado todas las bebidas");
	}

}
