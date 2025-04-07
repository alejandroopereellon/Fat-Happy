package productos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.modelo.Pedido;
import pedido.util.ModificarOrdenPedido;
import productos.dao.ProductosDaoGlobal;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.MenuPedido;
import productos.modelo.Postre;
import productos.modelo.Producto;

/**
 * Clase dedicada a realizar modificaciones en {@link MenuPedido}
 * 
 * @author Alejandro Perellón López
 */
public class ModificarMenuPedido {

	// Crear el logger
	static Logger logger = LogManager.getLogger(ModificarMenuPedido.class);

	private Pedido pedido;
	private MenuPedido menu;

	public ModificarMenuPedido(Pedido pedido) {
		this.pedido = pedido;
		// Obtenemos el menu seleccionado
		obtenerMenuSeleccionado();
	}

	/**
	 * Buscamos el menu seleccionado de toda la lista de menus para añadir el
	 * producto seleccionado
	 */
	public void obtenerMenuSeleccionado() {
		for (MenuPedido menu : pedido.getOrden().getListaMenus()) {
			if (menu.isMenuSeleccionado()) {
				this.menu = menu;
				break;
			}
		}
	}

	public void comprobarDondeInsertar(Producto pro) {
		// Si el producto seleccionado no puede se añadido por no cumplir con los
		// requisitos se va a añadir a la lista de productos
		if (!añadirProducto(pro)) {
			new ModificarOrdenPedido(pedido).anadirProducto(pro);
			logger.info(
					"Se ha añadido el producto a la lista de productos debido a que no cumple los requisitos del menu");
		}
	}

	/**
	 * Metodo al que añades un producto, y dependiendo del tipo de producto que sea
	 * lo inserta o no
	 * 
	 * @param pro {@link Producto} que se va a anadir al menu
	 */
	protected boolean añadirProducto(Producto pro) {
		/**
		 * Recogemos el producto de la base de datos y generamos un nuevo objeto
		 * producto
		 */
		Producto productoInsertar = ProductosDaoGlobal.get().obtenerProducto(pro.getCodigo());

		// Comprobamos si el producto es una bebida
		if (productoInsertar instanceof Bebida) {
			Bebida bebida = (Bebida) productoInsertar;
			return anadirBebida(bebida);
		} else if (productoInsertar instanceof Complemento) {
			Complemento complemento = (Complemento) productoInsertar;
			return anadirComplemento(complemento);
		} else if (productoInsertar instanceof Postre) {
			Postre postre = (Postre) productoInsertar;
			return anadirPostre(postre);
		}
		return false;
	}

	/**
	 * Metodo para la insercion de un nuevo postre, este solo comprueba que el
	 * producto sea un postre.
	 * 
	 * Adicionalmente, en caso de insertarse el postre, se va a volver a calcular el
	 * nuevo importe del menu debido a que el postre incrementa el valor del menu
	 * sin tener opcion a descuento
	 * 
	 * @param postre postre que se va a añadir al producto
	 * @return TRUE en caso de que el postre se haya incorporado correctamente ||
	 *         FALSE en caso de que el postre no se haya podido añadir
	 */
	private boolean anadirPostre(Postre postre) {
		menu.setPostre(postre);
		new CalcularImporteMenu(menu).calcularImporte();
		logger.info("Se ha insertado el postre id {} en el menu", postre.getCodigo());
		return true;
	}

	/**
	 * Este metodo añade un complemento al menu, este complemento debe ser
	 * obligatoriamente patatas y debe de cumplir obligatoriamente los requisitos de
	 * tamano del menu
	 * 
	 * @param productoInsertar es el producto que se va a insertar
	 * 
	 * @return TRUE si el complemento se inserta correctamente || FALSE si no se
	 *         inserta
	 */
	private boolean anadirComplemento(Complemento complemento) {
		if (complemento.getTamano() == menu.getTamano() && complemento.getTipoProducto().equalsIgnoreCase("patatas")) {
			menu.setComplemento(complemento);
			logger.info("Se ha insertado el complemento id {} en el menu", complemento.getCodigo());
			return true;
		} else {
			logger.info("El producto a anadir no cumple los requistos del menu");
			return false;
		}
	}

	/**
	 * Metodo para la insercion de una nueva bebida, esta debe comprobar que el
	 * producto sea de tamano unico o que sea del mismo tamano que el asigando en el
	 * menu, en caso contrario no se puede anadir al menu
	 * 
	 * @param bebida bebida que se va a añadir al menu
	 * @return TRUE en caso de que la bebida se haya incorporado correctamente
	 *         cumpliendo los requisitos de tamano || FALSE en caso de que la bebida
	 *         no se haya podido añadir
	 */
	private boolean anadirBebida(Bebida bebida) {
		// Si el producto es de tamaño unico o es del mismo tamaño que el menu se acepta
		if (bebida.getTamano() == 0 || bebida.getTamano() == menu.getTamano()) {
			menu.setBebida(bebida);
			logger.info("Se ha insertado la bebida id {} en el menu", bebida.getCodigo());
			return true;
		} else {
			logger.info("El producto a anadir no cumple los requistos del menu");
			return false;
		}
	}

}
