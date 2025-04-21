package pedido.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import pedido.interfazPedido.PanelPedido;
import pedido.interfazPedido.filtros.SelectorBebida;
import pedido.modelo.Pedido;
import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;
import productos.interfazProducto.listaProductosPedidos.ListaProductosPedidosMetodos;
import productos.interfazProducto.solicitarTamanoMenu.SolicitarTamanoMenu;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import productos.util.ModificarComplemento;
import productos.util.ModificarMenuPedido;

/**
 * Clase encargada de modificar la orden de pedido realizando las operaciones
 * necesarias para insertar o eliminar un producto o un menu
 * 
 * @author Alejandro Perellón López
 */
public class ModificarOrdenPedido {
	private Pedido pedido;

	// Crear el logger
	static Logger logger = LogManager.getLogger(ModificarOrdenPedido.class);
	// Establecemos el dao
	private ProductosDAO dao = ProductosDaoGlobal.get();

	public ModificarOrdenPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Metodo que realiza las comprobaciones necesarias para insertar un producto en
	 * un pedido.
	 * 
	 * Primero comprueba si el elemento seleccionado de la lista es un
	 * {@link Producto} o un {@link MenuPedido}, en caso de ser {@link Producto} se
	 * va a insertar directamente en el pedido. En caso de ser un {@link MenuPedido}
	 * se va a comprobar si se puede insertar el producto en el menu con las
	 * caracteristicas que tiene, en caso contrario no se añade al menu y se inserta
	 * normal en la lista
	 * 
	 * @param pro es el {@link Producto} que se quiere insertar en el {@link Pedido}
	 */
	public void insertarProductoEnPedido(Producto pro) {

		// 2. Obtenemos el elemento seleccionado de la lista para saber si es un menu
		Object elementoSeleccionado = obtenerElementoSeleccionadoLista();

		// 3.Obtenemos el producto del dao
		Producto productoInsertar = dao.obtenerProducto(pro.getCodigo());

		// 4. Insertamos el producto en el menu o pedido
		// Comprobamos si el producto recuperado no es nulo
		if (productoInsertar != null && elementoSeleccionado != null) {
			insertarProductoEnMenuOPedido(elementoSeleccionado, productoInsertar);
		} else {
			logger.info("El producto solicitado no se ha podido obtener correctamente");
		}

		// Seleccionamos el ultimo objeto de la lista
		ClasesEstaticas.getPanelPedido().getListaProductosPedidos()
				.setSelectedIndex(ClasesEstaticas.getPanelPedido().getModeloLista().getSize());

	}

	private void insertarProductoEnMenuOPedido(Object elementoSeleccionado, Producto productoInsertar) {

		// Si el objeto es un menuPedido se va a insertar el producto al menu
		if (elementoSeleccionado instanceof MenuPedido) {
			logger.debug("El objeto es un menuPedido");
			// Comprobamos si el menu ha insertado correctamente el producto
			if (!new ModificarMenuPedido((MenuPedido) elementoSeleccionado).añadirProducto(productoInsertar)) {
				logger.debug("El producto no se ha podido anadir al menu");
				anadirProducto(productoInsertar);
			}
		}
		// Comprobamos si el producto es un complemento y si el producto es una salsa se
		// va a añadir la salsa al producto
		else if (elementoSeleccionado instanceof Complemento
				&& productoInsertar.getTipoProducto().equalsIgnoreCase("salsa")) {
			// Anadimos la salsa en el complemento
			if (!new ModificarComplemento((Complemento) elementoSeleccionado)
					.anadirSalsaComplemento(productoInsertar)) {
				logger.debug("La salsa no se ha podido anadir al menu");
				anadirProducto(productoInsertar);
			}
		}
		// Si el objeto es un producto se va a insertar el producto directamente
		else {
			logger.debug("El objeto es un producto");
			anadirProducto(productoInsertar);
		}
	}

	/**
	 * Metodo que genera un {@link MenuPedido} nuevo, para ello comprueba si un
	 * producto es una {@link Hamburguesa} y realiza las comprobaciones necesarias
	 * para saber si es apta para ser un menu
	 */
	public void crearMenuPedido() {

		// 1. Obtenemos el producto seleccionado y comprobamos si es una hamburguesa
		Object obj = obtenerElementoSeleccionadoLista();
		// Si el objeto es una hamburguesa
		if (obj instanceof Hamburguesa && ((Hamburguesa) obj).isOpcionMenu()) {
			convertirHamburguesaEnMenuPedido((Hamburguesa) obj);
		} else {
			logger.debug("El producto no tiene opcion de menu");
			new DialogoMostrarMensajeMetodos().mostrarMensaje("Este producto no tiene opción de menú");
		}
	}

	/**
	 * Metodo que busca a traves del panel el objeto seleccionado y lo elimina de
	 * una manera u otra dependiendo del tipo de producto
	 */
	public void eliminarElemento() {
		// 1. Obtenemos el objeto seleccionado
		Object obj = obtenerElementoSeleccionadoLista();

		// Comprobamos si el objeto es un producto y lo eliminamos
		if (obj instanceof Producto) {
			buscarProductoYEliminar(obj);
		} else if (obj instanceof MenuPedido) {
			buscarMenuYEliminar(obj);
		}
	}

	/**
	 * Metodo que convierte una {@link Hamburguesa} en un {@link MenuPedido}
	 * 
	 * @param ham es la {@link Hamburguesa} que se va a convertir
	 */
	private void convertirHamburguesaEnMenuPedido(Hamburguesa ham) {
		// Solicitamos el tamano del menu
		int tamano = obtenerTamanoMenu();
		logger.debug("El tamano de hamburguesa es {}", tamano);
		// Comprobamos si el tamano del menu es diferente de 0 indicando 2 o 3
		if (tamano != 0) {
			MenuPedido menu = new MenuPedido(ham, tamano);
			logger.info("Se ha creado un menu con la hamburguesa {}", ham);

			// Eliminamos la hambruguesa
			buscarProductoYEliminar(ham);

			// Anadimos el menu a la lista de objetos
			ClasesEstaticas.getPanelPedido().getModeloLista().addElement(menu);

			// Anadimos el menu a la orden de pedido
			ClasesEstaticas.getPedido().getOrden().getListaMenus().add(menu);

			logger.debug("Se ha anadido el menu a la orden de pedido");
		}
	}

	/**
	 * Metodo que llama al dialogo modal {@link SolicitarTamanoMenu} y obtiene el
	 * menu solicitado por el empleado
	 * 
	 * @return {@link Integer} con el numero de menu solicitado
	 */
	private int obtenerTamanoMenu() {
		SolicitarTamanoMenu stm = new SolicitarTamanoMenu(ConfiguracionInicial.get().getVentanaPrincipal(), true);
		stm.setVisible(true);

		return stm.getTamano();
	}

	private Object obtenerElementoSeleccionadoLista() {
		PanelPedido panel = ClasesEstaticas.getPanelPedido();
		Object objeto = null;
		// Obtenemos la posicion del elemento seleccionado
		int posicion = panel.getListaProductosPedidos().getSelectedIndex();
		if (posicion != -1) {
			objeto = panel.getModeloLista().get(posicion);
		}
		return objeto;
	}

	/**
	 * Metodo que añade un producto a la orden de pedido y realiza todos los
	 * calculos necesarios para actualizar el pedido
	 * 
	 * @param pro producto que se va a añadir a pedido
	 */
	private void anadirProducto(Producto pro) {
		if (pro != null) {
			// Comprobamos primero si el producto es una bebida para solicita el tamano
			if (pro instanceof Bebida) {
				solicitarTamanoBebida((Bebida) pro);
			}

			// Anadimos el producto en la orden de pedido
			pedido.getOrden().getListaProductos().add(pro);
			// Actualizamos el importe del pedido
			pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
			logger.info("Se ha añadido el producto con id {} en la lista", pro.getCodigo());

			// Anadimos el producto pedido en la casilla del producto
			new ListaProductosPedidosMetodos(ClasesEstaticas.getPanelPedido()).anadirElemento(pro);
		}
	}

	/**
	 * Metodo que solicita el tamano de la bebida y modifica el producto para que
	 * sea del tamano solicitado
	 * 
	 * @param pro es la {@link Bebida} que se va a modificar
	 */
	private void solicitarTamanoBebida(Bebida pro) {
		// Creamos el objeto selectorBebida y lo hacemos visible
		SelectorBebida selector = new SelectorBebida();
		selector.setVisible(true);

		// Obtenemos el tamano del pedido
		int tamanoPedido = selector.getTamano();

		for (Producto producto : ClasesEstaticas.getListaProductos().getListaBebidas()) {
			// Convertimos el producto en una bebida
			Bebida bebida = (Bebida) producto;
			if (bebida.getTamano() == tamanoPedido && bebida.getNombreProducto().startsWith(pro.getNombreProducto())) {
				pro = bebida;
				logger.debug("Se ha establecido la bebida {} como bebida adecudada al menú seleccionado", bebida);
				break;
			}
		}

	}

	/**
	 * Metodo que busca el {@link MenuPedido} introducido por parametro y en caso de
	 * encontrarse se elimina del modelo y de la orden de pedido
	 * 
	 * @param obj es el {@link MenuPedido} que se va a eliminar
	 */
	private void buscarMenuYEliminar(Object obj) {
		// Obtenemos el menu
		MenuPedido menuObtenido = (MenuPedido) obj;

		// Recorremos los menus de la lista de la orden de pedido
		for (MenuPedido menuBucle : pedido.getOrden().getListaMenus()) {
			// Si el numero de identificacion del menu coincide con el del objeto obtenido
			if (menuBucle.getNumeroIdentificacion().equals(menuObtenido.getNumeroIdentificacion())) {
				// Eliminamos el menu de la orden de pedido
				pedido.getOrden().getListaMenus().remove(menuBucle);

				// Eliminamos el producto de la orden de pedido
				ClasesEstaticas.getPanelPedido().getModeloLista().removeElement(menuObtenido);

				logger.debug("Se ha eliminado el producto {} de la lista", menuObtenido);
				break;
			}
		}
	}

	/**
	 * Metodo que busca el {@link Producto} introducido por parametro y en caso de
	 * encontrarse se elimina del modelo y de la orden de pedido
	 * 
	 * @param obj es le {@link Producto} que se va a eliminar
	 */
	private void buscarProductoYEliminar(Object obj) {
		// Obtenemos el producto
		Producto productoObtenido = (Producto) obj;

		// Recorremos la lista de productos de la orden de pedido
		for (Producto productoBucle : pedido.getOrden().getListaProductos()) {

			// Si el numero de identificacion cuadra con el numero del objeto lo eliminamos
			if (productoBucle.getNumeroIdentificacion().equals(productoObtenido.getNumeroIdentificacion())) {
				pedido.getOrden().getListaProductos().remove(productoObtenido);
				ClasesEstaticas.getPanelPedido().getModeloLista().removeElement(obj);
				logger.debug("Se ha eliminado el producto {} de la lista", productoObtenido);
				break;
			}
		}
	}
}