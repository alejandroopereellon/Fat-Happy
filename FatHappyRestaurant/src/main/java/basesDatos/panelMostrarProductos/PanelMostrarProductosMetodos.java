package basesDatos.panelMostrarProductos;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.solicitarDatos.tecladoPantalla.TecladoEnPantalla;
import auxiliares.solicitarDatos.tecladoPantalla.TecladoEnPantallaMetodos;
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
	 * @param lista es la lista de productos que se van a mostrar
	 */
	private void anadirProductosAlPanel(List<Producto> lista) {
		// Filtramos por el tipo de producto
		String filtroProductos = panelMuestra.getFiltroCategoria().getSelection().getActionCommand();
		// Filtramos por la actividad del producto
		String filtroEstado = panelMuestra.getFiltroEstado().getSelection().getActionCommand();
		// Filtramos por la busqueda de producto
		String filtroBusqueda = panelMuestra.getCasillaBusqueda().getText().trim();

		int numeroElementos = 0, numeroActivos = 0, numeroInactivos = 0;

		for (Producto pro : lista) {
			// Comprobamos si pasa el filtro de busqueda
			if (filtrarBusqueda(pro, filtroBusqueda)) {
				// Comprobamos si pasa el filtro de actividad
				if (filtrarEstado(pro, filtroEstado)) {
					// Comprobamos si pasa el filtro de categoria
					if (filtrarProductos(pro, filtroProductos)) {
						insertarPanelProducto(pro);

						// Establecemos los contadores de los productos
						numeroElementos++;
						if (pro.isStockDisponible()) {
							numeroActivos++;
						} else {
							numeroInactivos++;
						}
					}
				}
			}
		}

		// Establecemos las variables en el panel
		panelMuestra.getNumeroTotales().setText(String.valueOf(numeroElementos));
		panelMuestra.getNumeroActivos().setText(String.valueOf(numeroActivos));
		panelMuestra.getNumeroInactivos().setText(String.valueOf(numeroInactivos));
	}

	/**
	 * Metodo que comprueba si un producto cumple los requisitos de categoria o no
	 * 
	 * @param pro             es el {@link Producto} que se va a filtrar
	 * @param filtroProductos es el filtro que se va a utilizar
	 * @return TRUE si cumple el requisito || False si no se cumple
	 */
	private boolean filtrarProductos(Producto pro, String filtroProductos) {
		// Comprobamos si el producto contiene todos los productos
		if (filtroProductos.equalsIgnoreCase("todasCategorias")) {
			return true;
		} else
		// Comprobamos si el producto pertenece a la categoria o no
		if (pro.getCategoria().equalsIgnoreCase(filtroProductos)) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que comprueba si un producto cumple los requisitos de actividad o no
	 * 
	 * @param pro          es el {@link Producto} que se va a filtrar
	 * @param filtroEstado es el filtro que se va a utilizar
	 * @return TRUE si cumple el requisito || False si no se cumple
	 */
	private boolean filtrarEstado(Producto pro, String filtroEstado) {
		// Comprobamos si el producto contiene todos los productos
		if (filtroEstado.equalsIgnoreCase("todosactivosDesactivados")) {
			return true;
		} else
		// Comprobamos si el producto esta activo y la seleccion es activa
		if (pro.isStockDisponible() && filtroEstado.contentEquals("activado")) {
			return true;
		} else
		// Comprobamos si el producto esta desactivado y la seleccion es desactivado
		if (!pro.isStockDisponible() && filtroEstado.equalsIgnoreCase("desactivado")) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que comprueba si un producto cumple los requisitos de busqueda o no
	 * 
	 * @param pro             es el {@link Producto} que se va a filtrar
	 * @param filtrarBusqueda es el filtro que se va a utilizar
	 * @return TRUE si cumple el requisito || False si no se cumple
	 */
	private boolean filtrarBusqueda(Producto pro, String filtrarBusqueda) {
		// Si la busqueda el completa retornamos true
		if (filtrarBusqueda.equalsIgnoreCase("Introduce el nombre de un producto")) {
			return true;
		} else
		// Si el nombre del producto contiene el texto del filtro
		if (pro.getNombreProducto().toLowerCase().contains(filtrarBusqueda.toLowerCase())) {
			return true;
		}
		return false;
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

	/**
	 * Metodo que busca los productos que contengan el contenido escrito en el
	 * buscador
	 */
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

	/**
	 * Metodo encargado de reiniciar todos los parametros
	 */
	protected void reiniciarParametros() {
		// Restablecemos la busqueda
		panelMuestra.getCasillaBusqueda().setText("Introduce el nombre de un producto");

		// Restablecemos el panel de activos e inactivos
		panelMuestra.getTodosProductos().setSelected(true);

		// Restablecemos el panel de categorias
		panelMuestra.getTodasCategorias().setSelected(true);

		// Volvemos a mostrar todos los productos
		mostrarProductos();

	}
}
