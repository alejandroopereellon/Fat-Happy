package productos.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import productos.util.CalcularImporteMenu;

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

	private boolean menuSeleccionado = false;

	/**
	 * @param hamburguesa es la hamburguesa del menu principal
	 * @param tamano      es el tamano del menu: 1 pequeno, 2 mediano, 3 grande
	 * @param precioMenu  es el precio de la hamburguesa multuiplicado por 1.65
	 */
	public MenuPedido(Hamburguesa hamburguesa, int tamano) {
		this.hamburguesa = hamburguesa;
		this.tamano = tamano;
		new CalcularImporteMenu(this).calcularImporte();
	}

	private final UUID numeroIdentificacion = UUID.randomUUID();

	// Getters && Setters

	public Complemento getComplemento() {
		return complemento;
	}

	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

	public Postre getPostre() {
		return postre;
	}

	public void setPostre(Postre postre) {
		this.postre = postre;
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

	public boolean isMenuSeleccionado() {
		return menuSeleccionado;
	}

	public void setMenuSeleccionado(boolean menuSeleccionado) {
		this.menuSeleccionado = menuSeleccionado;
	}

	public UUID getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	// toString

	@Override
	public String toString() {
		StringBuilder stb = new StringBuilder();
		String tamanoString = "mediano";
		if (tamano == 3) {
			tamanoString = "grande";
		}

		// Anadimos el nombre y precio del producto
		stb.append("Menu " + tamanoString + "\t (" + precioMenu.setScale(2, RoundingMode.HALF_UP) + " Eur)");

		if (this.isMenuPromocionado()) {
			stb.append(System.lineSeparator());
			stb.append("Promocionado");
		}

		// Anadimos la informacion de la hamburguesa
		stb.append(System.lineSeparator() + informacionHamburguesa());

		// Trabajamos el complemento
		stb.append(System.lineSeparator() + informacionComplemento());

		// Trabajamos la bebida
		stb.append(System.lineSeparator() + informacionBebida());

		// Trabajamos el postre
		stb.append(System.lineSeparator() + informacionPostre());

		return stb.toString();
	}

	private StringBuilder informacionPostre() {
		StringBuilder texto = new StringBuilder();

		if (postre == null) {
			texto.append("Postre opcional");
		} else {
			texto.append(postre.getNombreProducto());
			texto.append("(" + postre.getPrecioVenta() + " Eur)");
			// Mostramos los ingredientes
			for (Ingrediente ing : postre.getListaIngredientes()) {
				if (!ing.isActivo()) {
					texto.append(ing.toString());
				}
			}
			// Mostramos los extras
			for (Extra ext : postre.getListaExtras()) {
				if (ext.getCantidadExtra() > 0) {
					texto.append(ext.toString());
				}
			}
		}
		return texto;
	}

	private StringBuilder informacionHamburguesa() {
		StringBuilder texto = new StringBuilder();

		texto.append(hamburguesa.getNombreProducto());

		// Mostramos los ingredientes
		for (Ingrediente ing : hamburguesa.getListaIngredientes()) {
			if (!ing.isActivo()) {
				texto.append(ing.toString());
			}
		}
		// Mostramos los extras
		for (Extra ext : hamburguesa.getExtras()) {
			if (ext.getCantidadExtra() > 0) {
				texto.append(ext.toString());
			}
		}
		return texto;
	}

	private StringBuilder informacionComplemento() {
		StringBuilder stb = new StringBuilder();

		if (complemento == null) {
			stb.append("Anadir complemento");
		} else {
			stb.append(complemento.getNombreProducto());
		}

		return stb;
	}

	private StringBuilder informacionBebida() {
		StringBuilder stb = new StringBuilder();
		if (bebida == null) {
			stb.append("Anadir bebida");
		} else {
			stb.append(bebida.getNombreProducto());
			stb.append(System.lineSeparator());
			if (!bebida.isExtraActivo() && bebida.getNombreExtra().toLowerCase().contains("hielo")) {
				stb.append(bebida.getNombreExtra());
			}
		}
		return stb;
	}
}
