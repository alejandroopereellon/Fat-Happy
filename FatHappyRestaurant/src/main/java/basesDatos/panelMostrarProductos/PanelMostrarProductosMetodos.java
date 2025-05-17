package basesDatos.panelMostrarProductos;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.tecladoPantalla.TecladoEnPantalla;
import auxiliares.tecladoPantalla.TecladoEnPantallaMetodos;
import auxiliares.utilidadesGraficas.PanelUtil;
import basesDatos.casillaProductos.CasillaProducto;
import basesDatos.casillaProductos.CasillaProductoMetodos;
import productos.modelo.Producto;

public class PanelMostrarProductosMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelMuestraProductos.class);

	private final PanelMuestraProductos panelMuestra;

	private static final PanelUtil UTIL = new PanelUtil();

	public PanelMostrarProductosMetodos(PanelMuestraProductos panelMuestra) {
		this.panelMuestra = panelMuestra;
	}

	/**
	 * Metodo que muestra todos los productos
	 */
	public void mostrarProductos() {
		// Cargamos la lista de todos los productos
		List<Producto> lista = cargarListaCompletaProductos();

		// Establecemos el layout del panel
		panelMuestra.getPanelProductos().setLayout(new GridLayout(0, 1));

		// Borramos todos los elementos del panel
		panelMuestra.getPanelProductos().removeAll();

		// Anadimos todos los paneles
		anadirProductosAlPanel(lista);
	}

	/**
	 * Metodo que inserta en el panel de productos todos los productos, teniendo en
	 * cuenta si la casilla de busqueda contiene algun nombre
	 * 
	 * @param lista
	 */
	private void anadirProductosAlPanel(List<Producto> lista) {
		String textoBusqueda = panelMuestra.getCasillaBusqueda().getText().trim();
		boolean filtrar = !textoBusqueda.isEmpty()
				&& !textoBusqueda.equalsIgnoreCase("Introduce el nombre de un producto");

		for (Producto pro : lista) {
			if (!filtrar || (filtrar && pro.getNombreProducto().toLowerCase()
					.contains((panelMuestra.getCasillaBusqueda().getText().toLowerCase())))) {
				insertarPanelProducto(pro);
			}
		}
	}

	/**
	 * Metodo que insetar en el panel la casilla del producto introducido
	 * 
	 * @param pro es el {@link Producto} que se va mostrar en el panel
	 */
	private void insertarPanelProducto(Producto pro) {
		// Creamos la casilla de producto
		CasillaProducto casilla = new CasillaProducto(true, pro);

		// Iniciamos la casilla de producto
		new CasillaProductoMetodos(casilla, pro).iniciarConfiguracion();

		// Insertamos la casilla en el panel
		UTIL.insertarEnPanelSinBorrar(panelMuestra.getPanelProductos(), casilla);
	}

	/**
	 * Metodo que recupera todos los objetos de la base de datos
	 * 
	 * @return {@link List}a de {@link Producto}s
	 */
	private List<Producto> cargarListaCompletaProductos() {
		logger.debug("Se va a cargar la lista completa de productos");
		List<Producto> lista = new ArrayList<Producto>();

		// Anadimos todas las hamburguesas
		lista.addAll(ClasesEstaticas.getListaProductos().getListaHamburguesas());
		// Anadimos todos los complementos
		lista.addAll(ClasesEstaticas.getListaProductos().getListaComplementos());
		// Anadimos todas las bebidas
		lista.addAll(ClasesEstaticas.getListaProductos().getListaBebidas());
		// Anadimos todos los postres
		lista.addAll(ClasesEstaticas.getListaProductos().getListaPostres());

		logger.debug("Se ha cargado la lista de productos con {} elementos", lista.size());
		return lista;
	}

	void iniciarBusqueda() {

		// Iniciamos el teclado en pantalla
		String busqueda = new TecladoEnPantallaMetodos(new TecladoEnPantalla()).iniciarTecladoPantalla();

		// Establecemos el texto de busqueda en el area de busqueda
		panelMuestra.getCasillaBusqueda().setText(busqueda);

		// Mostramos los productos
		mostrarProductos();
	}

	/**
	 * Metodo que cambia el panel actual por el principal del sistema
	 */
	protected void volverPantallaPrincipal() {
		// Añadimos el panel pedido al panel principal
		new PanelUtil().insertarEnPanel(ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario(),
				ClasesEstaticas.getPanelPedido());
	}
}
