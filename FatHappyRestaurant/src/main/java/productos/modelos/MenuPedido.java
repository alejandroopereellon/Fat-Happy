package productos.modelos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Esta clase permite la generacion de un menu, esta clase contiene de
 * constructor principal una hamburguesa y el tamano del menu (1 pequeno, 2
 * mediano, 3 grande).
 * 
 * A continuacion a traves de los metodos de insercion creados debemos de añadir
 * los diferentes productos adicionales, los cuales tanto la bebida como los
 * complementos son obligatorio, siendo el postre el anadido opcional.
 * 
 * Todos los complementos tienen su propio setter y getter que realizan
 * comprobaciones de requisitos previos, principalmente por el tipo de producto
 * o el precio
 * 
 * Adicionalmente existe el metodo de actualizacion de precio que realiza el
 * calculo del importe total del menu a traves del coste de la hamburguesa *
 * 1.65 y el coste del postre de suma adicionalmente al precio debido a que no
 * tiene descuento
 * 
 */
public class MenuPedido implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7326713190431164081L;

	private Hamburguesa hamburguesa;
	private Complemento complemento;
	private Bebida bebida;
	private Postre postre;
	/**
	 * 1 pequeno, 2 mediado, 3 grande
	 */
	private int tamano;
	/**
	 * Coste total de venta al cliente, este esta formado por el precio de la
	 * hamburguesa * 1.65, y en caso de anadir un postre se va a adicional el precio
	 * total del postre
	 */
	private BigDecimal precioMenu;
	/**
	 * Indicador de si el menu esta siendo promocionado (el coste es de 0 euros en
	 * caso de verdadero)
	 */
	private boolean menuPromocionado = false;

	/**
	 * @param hamburguesa es la hamburguesa del menu principal
	 * @param tamano      es el tamano del menu: 1 pequeno, 2 mediano, 3 grande
	 * @param precioMenu  es el precio de la hamburguesa multuiplicado por 1.65
	 */
	public MenuPedido(Hamburguesa hamburguesa, int tamano) {
		this.hamburguesa = hamburguesa;
		this.tamano = tamano;
		// Calculamos el importe del menu
		calcularImporte();
	}

	// Getters && Setters

	public Complemento getComplemento() {
		return complemento;
	}

	/**
	 * Este metodo añade un complemento al menu, este complemento debe ser
	 * obligatoriamente patatas y debe de cumplir obligatoriamente los requisitos de
	 * tamano del menu
	 * 
	 * @param complemento patatas que se van a añadir al producto que cumplan el
	 *                    tamano del menu
	 * @return TRUE en caso de que complan los requisitos || FALSE en caso de no
	 *         cumplir los requisitos
	 */
	public boolean setComplemento(Complemento complemento) {
		if (complemento.getTipoProducto().equalsIgnoreCase("patatas") && complemento.getTamano() == tamano) {
			this.complemento = complemento;
			return true;
		}
		return false;
	}

	public Bebida getBebida() {
		return bebida;
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
	public boolean setBebida(Bebida bebida) {
		if (bebida.getTamano() == 0 || bebida.getTamano() == tamano) {
			this.bebida = bebida;
			return true;
		}
		return false;
	}

	public Postre getPostre() {
		return postre;
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
	public boolean setPostre(Postre postre) {
		this.postre = postre;
		calcularImporte();
		return true;
	}

	public BigDecimal getPrecioMenu() {
		return precioMenu;
	}

	public void setPrecioMenu(BigDecimal precioMenu) {
		this.precioMenu = precioMenu;
	}

	public boolean isMenuPromocionado() {
		return menuPromocionado;
	}

	public void setMenuPromocionado(boolean menuPromocionado) {
		this.menuPromocionado = menuPromocionado;
	}

	public Hamburguesa getHamburguesa() {
		return hamburguesa;
	}

	public int getTamano() {
		return tamano;
	}

	// Metodos adicionales

	private void calcularImporte() {
		// Calculamos el precio de la hamburguesa mas el coste de intereses
		precioMenu = hamburguesa.getPrecioVenta().multiply(new BigDecimal("1.65"));
		// En caso de existir un postre en el menu se va a actualizar el precio
		if (postre != null) {
			precioMenu.add(postre.getPrecioVenta());
		}
	}

	// toString
	@Override
	public String toString() {
		String datos = "Menu " + tamano + " (" + precioMenu + " Eur)";
		// Si el articulo esta promocionado se informara
		if (menuPromocionado) {
			datos = datos + "\tPRM";
		}
		datos = datos + System.lineSeparator() + "\t" + hamburguesa.toString() + System.lineSeparator() + "\t"
				+ complemento.toString() + System.lineSeparator() + "\t" + bebida.toString();
		if (postre != null) {
			datos = datos + System.lineSeparator() + "\t" + postre.toString();
		}
		return datos;
	}
}
