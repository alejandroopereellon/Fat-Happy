package pedido.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import pedido.interfazPedido.PanelPedido;
import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;
import pedido.modelo.PedidoDatos;
import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;
import productos.interfazProducto.SolicitarTamanoMenu.SolicitarTamanoMenu;
import productos.interfazProducto.listaProductosPedidos.ListaProductosPedidosMetodos;
import productos.modelo.Hamburguesa;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import productos.util.ModificarMenuPedido;

/**
 * Clase encargada de modificar la orden de pedido realizando las operaciones
 * necesarias para insertar o eliminar un producto o un menu
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

	public void crearMenuPedido() {
		// 1. Obtenemos el panel del pedido
		PanelPedido panel = PedidoDatos.getPanel();

		// Si el panel es nulo no continuamos
		if (panel == null) {
			logger.warn("El panel de pedido es nulo");
			return;
		}

		// 2. Obtenemos el producto seleccionado y comprobamos si es una hamburguesa
		Object obj = obtenerElementoSeleccionadoLista(panel);
		// Si el objeto es una hamburguesa
		if (obj instanceof Hamburguesa) {
			logger.debug("El objeto para crear un menu es una hamburguesa");
			// Convertimos el objeto en una hamburguesa
			Hamburguesa ham = (Hamburguesa) obj;
			// Si tiene opcion de menu continuamos
			if (ham.isOpcionMenu()) {
				// Solicitamos el tamano del menu
				int tamano = obtenerTamanoMenu();
				logger.debug("El tamano de hamburguesa es {}", tamano);
				// Comprobamos si el tamano del menu es diferente de 0 indicando 2 o 3
				if (tamano != 0) {
					MenuPedido menu = new MenuPedido(ham, tamano);
					logger.info("Se ha creado un menu con la hamburguesa {}", ham);

					// Eliminamos la hambruguesa
					eliminarProducto((Hamburguesa) obj);

					// Anadimos el menu a la lista de objetos
					PedidoDatos.getPanel().getModeloLista().addElement(menu);

					// Anadimos el menu a la orden de pedido
					PedidoDatos.getPedido().getOrden().getListaMenus().add(menu);
				}

			}
		}

	}

	/**
	 * Metodo que eliminar un {@link Producto} del modelo de la
	 * {@link ListaProductosPedidosMetodos} y de la {@link OrdenPedido}
	 * 
	 * @param pro es el {@link Producto} que se va a eliminar
	 */
	private void eliminarProducto(Producto pro) {
		// Eliminamos el producto de la lista de objetos
		PedidoDatos.getPanel().getModeloLista().removeElement(pro);

		// Eliminamos el producto de la orden de pedido
		if (PedidoDatos.getPedido().getOrden().getListaProductos().remove(pro)) {
			logger.debug("Se ha eliminado el producto {} de la lista de productos", pro);
		} else {
			logger.info("No se ha podido eliminar el producto {} de la lista de productos", pro);
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

		// 1. Obtenemos el panel del pedido
		PanelPedido panel = PedidoDatos.getPanel();

		// Si el panel es nulo no continuamos
		if (panel == null) {
			logger.warn("El panel de pedido es nulo");
			return;
		}

		// 2. Obtenemos el elemento seleccionado de la lista para saber si es un menu
		Object objeto = obtenerElementoSeleccionadoLista(panel);

		// Obtenemos el producto del dao
		Producto proNuevo = dao.obtenerProducto(pro.getCodigo());

		// Comprobamos si el producto recuperado no es nulo
		if (proNuevo != null) {
			// Si el objeto es un menuPedido se va a insertar el producto al menu
			if (objeto != null && objeto instanceof MenuPedido) {
				logger.debug("El objeto es un menuPedido");
				// Comprobamos si el menu ha insertado correctamente el producto
				if (!new ModificarMenuPedido((MenuPedido) objeto).añadirProducto(proNuevo)) {
					logger.debug("El producto no se ha podido anadir al menu");
					anadirProducto(proNuevo);
				}
			}
			// Si el objeto es un producto se va a insertar el producto directamente
			else {
				logger.debug("El objeto es un producto");
				anadirProducto(proNuevo);
			}
		} else {
			logger.info("El producto solicitado no se ha podido obtener correctamente");
		}

		// Seleccionamos el ultimo objeto de la lista
		PedidoDatos.getPanel().getListaProductosPedidos()
				.setSelectedIndex(PedidoDatos.getPanel().getModeloLista().getSize());

	}

	private Object obtenerElementoSeleccionadoLista(PanelPedido panel) {
		Object objeto = null;
		// Obtenemos la posicion del elemento seleccionado
		int posicion = panel.getListaProductosPedidos().getSelectedIndex();
		if (posicion != -1) {
			objeto = panel.getModeloLista().get(posicion);
		}
		return objeto;
	}

	// TODO LA IDEA PRINCIPAL ES HACER QUE AL SELECCIONAR UN PRODUCTO SI HAY UN MENU
	// SELECCIONADO SE AÑADA EL ELEMENTO A ESE MENU, EN CASO CONTRARIO SE AÑADE EL
	// PRODUCTO FUERA, HAY QUE HACER UNA CLASE PRINCIPAL, QUE ANALICE ESO, Y TAMBIEN
	// HAY QUE HACER QUE SI AL CONVERTIR UN PRODUCTO EN UN MENU SE ELIMINE DE UN
	// SITIO Y SE RECUPERE EN OTRO. TAM

	/**
	 * Metodo que añade un producto a la orden de pedido y realiza todos los
	 * calculos necesarios para actualizar el pedido
	 * 
	 * @param pro producto que se va a añadir a pedido
	 */
	private void anadirProducto(Producto pro) {
		if (pro != null) {
			// Anadimos el producto en la orden de pedido
			pedido.getOrden().getListaProductos().add(pro);
			// Actualizamos el importe del pedido
			pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
			logger.info("Se ha añadido el producto con id {} en la lista", pro.getCodigo());

			// Anadimos el producto pedido en la casilla del producto
			new ListaProductosPedidosMetodos(PedidoDatos.getPanel()).anadirElemento(pro);
		}
	}

	/**
	 * Metodo que añade un menu a la orden de pedido y realiza todos los calculos
	 * necesarios para actualizar el pedido
	 * 
	 * @param menu producto que se va a añadir a pedido
	 */
	private void anadirMenu(MenuPedido menu) {
		pedido.getOrden().getListaMenus().add(menu);
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
		logger.info("Se ha añadido el menu en la lista");
	}

	/**
	 * Metodo que elimina de la orden de pedido el producto que se situa en la
	 * posicion
	 * 
	 * @param posicion es la posicion del producto que se va a eliminar
	 */
	private void retirarProducto(int posicion) {
		List<Producto> lista = pedido.getOrden().getListaProductos();
		// Si el numero de elementos es menor o igual que la posicion se elimina el
		// objeto
		if (posicion >= 0 && posicion < lista.size()) {
			lista.remove(posicion);
			logger.info("Se ha retirado el producto de la posicion {}", posicion);
		} else {
			logger.error("La posicion es mayor que el numero de elementos en la lista", lista);
		}
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
	}

	/**
	 * Metodo que elimina de la orden de pedido el menu que se situa en la posicion
	 * indicada
	 * 
	 * @param posicion
	 */
	private void retirarMenu(int posicion) {
		List<MenuPedido> lista = pedido.getOrden().getListaMenus();
		// Si el numero de elementos es menor o igual que la posicion se elimina el
		// objeto
		if (posicion >= 0 && posicion < lista.size()) {
			lista.remove(posicion);
			logger.info("Se ha retirado el Menu de la posicion {}", posicion);
		} else {
			logger.error("La posicion es mayor que el numero de elementos en la lista", lista);
		}
		pedido.setImporteTotal(new CalcularImporte(pedido).obtenerImporteDescuento());
	}

}