package pedido.interfazPedido;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import auxiliares.utilidadesGraficas.PanelUtil;
import pedido.interfazPedido.filtros.FiltroBebidas;
import pedido.interfazPedido.filtros.FiltroComplementos;
import pedido.interfazPedido.filtros.FiltroHamburguesa;
import pedido.interfazPedido.filtros.FiltroPostre;
import pedido.interfazPedido.filtros.FiltroTodosLosProductos;
import pedido.util.ModificarOrdenPedido;
import productos.interfazProducto.casillaProducto.CasillaProducto;
import productos.interfazProducto.casillaProducto.CasillaProductoMetodos;
import productos.interfazProducto.listaProductosPedidos.ToStringRenderer;
import productos.modelo.Complemento;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Postre;
import productos.modelo.Producto;
import productos.modelo.Salsa;

/**
 * Clase que contiene todos los metodos necesarios para el funcionamiento del
 * {@link PanelPedido}
 * 
 * @author Alejandro Perellón López
 */
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

		// Establecemos el cell renderer del lista
		interfaz.getListaProductosPedidos().setCellRenderer(new ToStringRenderer<Object>());
		interfaz.getListaProductosPedidos().setFixedCellHeight(-1);

		// Establecemos el layout del panel de filtros
		interfaz.getPanelFiltros().setLayout(new FlowLayout());
	}

	public void mostrarHamburguesas() {
		vaciarPanelesCategorias();

		// Anadimos el panel de filtros
		FiltroHamburguesa filtro = new FiltroHamburguesa();
		new PanelUtil().insertarEnPanel(interfaz.getPanelFiltros(), filtro);
		logger.debug("Se ha insertado el filtro en el panel");

		mostraCasillasHamburguesas(filtro);
	}

	/**
	 * Metodo que muestra las casillas de la hamburguesa con los correspondientes
	 * filtrados necesarios
	 * 
	 * @param filtro es el {@link FiltroHamburguesa} que se aplica para mostrar o no
	 *               una casilla
	 */
	public void mostraCasillasHamburguesas(FiltroHamburguesa filtro) {
		// Obtenemos la lista de bebidas
		List<Producto> listaHamburguesas = ClasesEstaticas.getListaProductos().getListaHamburguesas();

		// Vaciamos el panel de casillas
		interfaz.getPanelProductos().removeAll();

		String opcionCarne = filtro.getTipoProducto().getSelection().getActionCommand();
		String opcionMenu = filtro.getOpcionMenu().getSelection().getActionCommand();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaHamburguesas) {
			// Obtenemos si el tipo de carne coincide
			boolean tipoCarne = producto.getTipoProducto().equalsIgnoreCase(opcionCarne)
					|| opcionCarne.equalsIgnoreCase("todos");
			// Obtenemos si el producto tiene la opcion de menu seleccionada
			Hamburguesa ham = (Hamburguesa) producto;
			boolean tipoOpcionMenu = String.valueOf(ham.isOpcionMenu()).equalsIgnoreCase(opcionMenu)
					|| opcionMenu.equalsIgnoreCase("todos");

			if (tipoCarne && tipoOpcionMenu) {
				crearCasillaProducto(producto);
			}
		}
		logger.debug("Se han insertado todas las hamburburguesas");
	}

	protected void mostrarComplementos() {
		vaciarPanelesCategorias();

		// Anadimos el panel de filtros
		FiltroComplementos filtro = new FiltroComplementos();
		new PanelUtil().insertarEnPanel(interfaz.getPanelFiltros(), filtro);
		logger.debug("Se ha insertado el filtro en el panel");

		mostrarCasillasComplementos(filtro);
	}

	/**
	 * Metodo que muestra las casillas de los complementos con los correspondientes
	 * filtrados necesarios
	 * 
	 * @param filtro es el {@link FiltroComplementos} que se aplica para mostrar o
	 *               no una casilla
	 */
	public void mostrarCasillasComplementos(FiltroComplementos filtro) {
		interfaz.getPanelProductos().removeAll();

		// Configuramos los filtros si es un menu
		configurarFiltrosComplementos(filtro);

		// Obtenemos la lista de complementos
		List<Producto> listaComplementos = ClasesEstaticas.getListaProductos().getListaComplementos();

		// Obtenemos la seleccion de tipo de producto
		String tipoProducto = filtro.getGrupoTipo().getSelection().getActionCommand();

		// Obtenemos la seleccion del tamaño del producto
		String tipoTamano = filtro.getGrupoTamano().getSelection().getActionCommand();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaComplementos) {

			boolean tipo = true;
			boolean tamano = true;

			// Si el producto es una salsa
			if (producto instanceof Salsa) {
				tipo = tipoProducto.equalsIgnoreCase("todos")
						|| producto.getTipoProducto().equalsIgnoreCase(tipoProducto);
				tamano = true;
			} else {
				// Convertimos el producto en un complemento
				Complemento comple = (Complemento) producto;

				// Analizamos el filtro de tipo de producto
				tipo = tipoProducto.equalsIgnoreCase("todos") || comple.getTipoProducto().equalsIgnoreCase(tipoProducto)
						|| (tipoProducto.equalsIgnoreCase("pollo")
								&& (comple.getTipoProducto().equalsIgnoreCase("nuggets")
										|| comple.getTipoProducto().equalsIgnoreCase("alitas")
										|| comple.getTipoProducto().equalsIgnoreCase("bites")));

				tamano = true;
				try {
					tamano = tipoTamano.equalsIgnoreCase("todos")
							|| tipoTamano.equalsIgnoreCase(String.valueOf(comple.getTamano()));
				} catch (NumberFormatException e) {
					logger.warn("Ha ocurrido un error al filtrar el tamaño del complemento {}", comple);
				}
			}

			if (tamano && tipo) {
				crearCasillaProducto(producto);
			}

		}

		logger.debug("Se han insertado todos los complementos");
	}

	/**
	 * Metodo que busca si el producto seleccionado es un {@link MenuPedido} y
	 * aplica el filtro de patatas y de tamano segun corresponda al pedido
	 * 
	 * @param filtro es el {@link FiltroComplementos} que se aplica
	 */
	private void configurarFiltrosComplementos(FiltroComplementos filtro) {
		// Obtenemos la informacion de si es un menu y establecemos los filtros para el
		// menu
		Object objeto = new ModificarOrdenPedido(interfaz.getPedido()).obtenerElementoSeleccionadoLista();
		if (objeto instanceof MenuPedido && ((MenuPedido) objeto).getComplemento() == null) {
			MenuPedido menu = (MenuPedido) objeto;
			filtro.getSoloPatatas().setSelected(true);

			// Seleccionamos el tamano del menu
			if (menu.getTamano() == 2) {
				filtro.getSoloMediano().setSelected(true);
			} else {
				filtro.getSoloGrande().setSelected(true);
			}
		}
	}

	/**
	 * Metodo que inicia la casilla de los postres mostrando el filtro y las
	 * casillas de los {@link Postre}s
	 */
	protected void mostrarPostres() {

		vaciarPanelesCategorias();

		// Anadimos el panel de filtros
		FiltroPostre filtro = new FiltroPostre();
		new PanelUtil().insertarEnPanel(interfaz.getPanelFiltros(), filtro);
		logger.debug("Se ha insertado el filtro en el panel");

		mostrarCasillasPostres(filtro);
	}

	/**
	 * Metodo que muestra las casillas de los postres con los correspondientes
	 * filtrados necesarios
	 * 
	 * @param filtro es el {@link FiltroPostre} que se aplica para mostrar o no una
	 *               casilla
	 */
	public void mostrarCasillasPostres(FiltroPostre filtro) {
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de postres
		List<Producto> listaPostres = ClasesEstaticas.getListaProductos().getListaPostres();
		// Obtenemos la seleccion de topping
		String seleccionTopping = filtro.getGrupoTopping().getSelection().getActionCommand();
		// Obtenemos la seleccion del tipo de producto
		String seleccionTipo = filtro.getGrupoTipoHelado().getSelection().getActionCommand();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaPostres) {
			// Comprobamos el topping de helado
			boolean topping = seleccionTopping.equalsIgnoreCase("todos")
					|| producto.getNombreProducto().contains(seleccionTopping);
			// Comprobamos el tipo de helado
			boolean tipoHelado = seleccionTipo.equalsIgnoreCase("todos")
					|| producto.getNombreProducto().startsWith(seleccionTipo);

			if (topping && tipoHelado) {
				crearCasillaProducto(producto);
			}
		}
		logger.debug("Se han insertado todos los postres");

	}

	protected void mostrarBebidas() {

		vaciarPanelesCategorias();

		// Anadimos el panel de filtros
		FiltroBebidas filtro = new FiltroBebidas();
		new PanelUtil().insertarEnPanel(interfaz.getPanelFiltros(), filtro);
		logger.debug("Se ha insertado el filtro en el panel");

		mostrarCasillasBebidas(filtro);
	}

	/**
	 * Metodo que muestra las casillas de las bebidas con los correspondientes
	 * filtrados necesarios
	 * 
	 * @param filtro es el {@link FiltroBebidas} que se aplica para mostrar o no una
	 *               casilla
	 */
	public void mostrarCasillasBebidas(FiltroBebidas filtro) {
		interfaz.getPanelProductos().removeAll();

		// Obtenemos la lista de bebidas
		List<Producto> listaBebidas = ClasesEstaticas.getListaProductos().getListaBebidas();

		// Obtenemos los parametros de las bebidas
		String tipoBebida = filtro.getGrupoTipoBebida().getSelection().getActionCommand();

		// Recorremos toda la lista de bebidas disponibles
		for (Producto producto : listaBebidas) {

			// Comprobamos el filtro tipo de bebida
			boolean mostrarTipo = tipoBebida.equalsIgnoreCase("todos")
					|| producto.getTipoProducto().equalsIgnoreCase(tipoBebida);

			boolean productoMostrar = String.valueOf(producto.getCodigo()).endsWith("0")
					|| producto.getTipoProducto().equalsIgnoreCase("singas");

			// filtramos las bebidas por tamanos (si terminan en 0 o 9)
			if (productoMostrar && mostrarTipo) {
				crearCasillaProducto(producto);
			}
		}
		logger.info("Se han cargado todas las bebidas");
	}

	/**
	 * Metodo que anade al {@link PanelPedido} la casilla del producto
	 * 
	 * @param producto es el {@link Producto} que se va a añadir al
	 *                 {@link PanelPedido}
	 */
	private void crearCasillaProducto(Producto producto) {
		// Creamos el panel del producto
		CasillaProducto casilla = new CasillaProducto(producto, interfaz.getPedido());

		// Iniciamos los datos de la casilla
		new CasillaProductoMetodos(casilla).establecerDatosProducto();

		// Anadimos cada bebida en el panel
		new PanelUtil().insertarEnPanelSinBorrar(interfaz.getPanelProductos(), casilla);

		logger.debug("Se ha insertado la casilla del producto {}", producto);
	}

	/**
	 * Metodo que crea el menu a traves del producto selecionado
	 */
	protected void crearMenu() {
		new ModificarOrdenPedido(ClasesEstaticas.getPedido()).crearMenuPedido();
	}

	void eliminarProducto() {
		new ModificarOrdenPedido(ClasesEstaticas.getPedido()).eliminarElemento();
	}

	/**
	 * Metodo que actualiza la lista para que cuadre con el modelo
	 */
	public void actualizarLista() {
		interfaz.getListaProductosPedidos().updateUI();
	}

	/**
	 * Metodo que se utiliza en cada carga de listado de productos para que se
	 * vacien los elementos del panel de productos y del filtro
	 */
	private void vaciarPanelesCategorias() {
		// Vaciamos el panel de contenido
		interfaz.getPanelProductos().removeAll();
		// Vaciamos el panel de filtros
		interfaz.getPanelFiltros().removeAll();
	}

	/**
	 * Metodo que muestra todos los productos de todas las categorias para permitir
	 * la busqueda
	 */
	protected void mostrarListaCompleta() {
		vaciarPanelesCategorias();

		// Anadimos el panel de filtros
		FiltroTodosLosProductos filtro = new FiltroTodosLosProductos();
		new PanelUtil().insertarEnPanel(interfaz.getPanelFiltros(), filtro);
		logger.debug("Se ha insertado el filtro en el panel");

		mostrarTodasCasillas(filtro);
	}

	/**
	 * Metodo que muestra todos los productos sin categorias
	 * 
	 * En caso de haber una busqueda realizada mostrará los productos que contengan
	 * esa cadena de caracteres
	 * 
	 * @param filtro es el {@link FiltroTodosLosProductos}
	 */
	public void mostrarTodasCasillas(FiltroTodosLosProductos filtro) {
		// Borramos todos los elementos del panel
		interfaz.getPanelProductos().removeAll();

		// Comprobamos que la cadena de texto es diferente
		String cadena = filtro.getTextoBuscado().getText();
		Boolean buscar = !cadena.equalsIgnoreCase("busqueda...");

		// Buscamos en todas las hamburguesas
		mostrarCasillasLista(ClasesEstaticas.getListaProductos().getListaHamburguesas(), buscar, cadena);
		// Buscamos en todos los complementos
		mostrarCasillasLista(ClasesEstaticas.getListaProductos().getListaComplementos(), buscar, cadena);
		// Buscamos en todas las bebidas
		mostrarCasillasLista(ClasesEstaticas.getListaProductos().getListaBebidas(), buscar, cadena);
		// Buscamos en todos los postres
		mostrarCasillasLista(ClasesEstaticas.getListaProductos().getListaPostres(), buscar, cadena);
	}

	/**
	 * Metodo que recorre la lista de productos comprobando si hay que realizar
	 * busqueda y contiene los datos
	 * 
	 * @param lista  es la {@link List}a de {@link Producto} que se va a recorrer
	 * @param buscar indica si hay que realizar una busqueda por parametro o no
	 * @param cadena es la cadena de caracteres que hay que buscar
	 */
	private void mostrarCasillasLista(List<Producto> lista, boolean buscar, String cadena) {

		for (Producto producto : lista) {
			if (buscar && producto.getNombreProducto().toUpperCase().contains(cadena.toUpperCase())) {
				crearCasillaProducto(producto);
				logger.debug("Se ha insertado la casilla con el patron de busqueda '{}'", cadena);
			} else if (!buscar) {
				crearCasillaProducto(producto);
				logger.debug("Se ha insertado la casilla sin patron de busqueda");
			}
		}
	}

}