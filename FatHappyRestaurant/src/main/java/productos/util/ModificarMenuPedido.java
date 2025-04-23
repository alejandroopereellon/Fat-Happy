package productos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
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

	private MenuPedido menu;

	public ModificarMenuPedido(MenuPedido menu) {
		this.menu = menu;
	}

	/**
	 * Metodo al que añades un producto, y dependiendo del tipo de producto que sea
	 * lo inserta o no
	 * 
	 * @param pro {@link Producto} que se va a anadir al menu
	 */
	public boolean añadirProducto(Producto pro) {
		// Comprobamos si el producto es una bebida
		if (menu.getBebida() == null && pro instanceof Bebida) {
			return anadirBebida((Bebida) pro);
		} else if (menu.getComplemento() == null && pro instanceof Complemento) {
			return anadirComplemento((Complemento) pro);
		} else if (menu.getPostre() == null && pro instanceof Postre) {
			return anadirPostre((Postre) pro);
		}
		logger.debug("Ya existe un producto del mismo tipo en el menu, se anadira como producto adicional");
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
		if (bebida.getTamano() == 0) {
			menu.setBebida(bebida);
			logger.info("Se ha insertado la bebida id {} en el menu", bebida);
			return true;
		} else {
			return obtenerBebidaTamanoAdecuado(bebida);
		}
	}

	/**
	 * Metodo que obtiene la bebida si el tamano de la bebida no es unico se va a
	 * obtener la ebida adecuada para el menu
	 * 
	 * @return {@link Bebida} adecuada al menu
	 */
	private boolean obtenerBebidaTamanoAdecuado(Bebida beb) {
		for (Producto producto : ClasesEstaticas.getListaProductos().getListaBebidas()) {
			// Convertimos el producto en una bebida
			Bebida bebida = (Bebida) producto;
			if (bebida.getTamano() == menu.getTamano()
					&& bebida.getNombreProducto().startsWith(beb.getNombreProducto())) {
				menu.setBebida(bebida);
				logger.debug("Se ha establecido la bebida {} como bebida adecudada al menú seleccionado", bebida);
				return true;
			}
		}
		logger.debug("No se ha establecido la bebida {} debido a que no se ha encontrado la adecuada", beb);
		return false;
	}

}
