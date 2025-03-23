package productos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import auxiliares.Escaner;
import productos.bebidas.Bebidas;
import productos.complementos.Complemento;
import productos.hamburguesas.Hamburguesa;
import productos.postres.Postres;

@SuppressWarnings("serial")
public class MenuPedido implements Serializable {
	private Hamburguesa hamburguesa;
	private Complemento complemento;
	private Bebidas bebida;
	private Postres postre;
	private String tamano;
	private Double precioMenu;
	private boolean menuPromocionado;

	public MenuPedido(Hamburguesa hamburguesa, Complemento complemento, Bebidas bebida, Postres postre, String tamano,
			Double precioMenu) {
		this.hamburguesa = hamburguesa;
		this.complemento = complemento;
		this.bebida = bebida;
		this.postre = postre;
		this.tamano = tamano;
		this.precioMenu = precioMenu;
		this.menuPromocionado = false;
	}

	/**
	 * Crea un menú de pedido con una hamburguesa, complemento, bebida y
	 * opcionalmente un postre.
	 * 
	 * @param ham la hamburguesa seleccionada para el menú
	 * @return un objeto MenuPedido que contiene los elementos seleccionados y su
	 *         precio
	 */
	public static MenuPedido crearMenu(Hamburguesa ham) {
		String tam = establecerTamanoMenu();
		Complemento com = seleccionarComplementoMenu(tam);
		Bebidas beb = seleccionarBebida(tam);
		// Anadimos un postre
		Postres pos = null;
		System.out.println("Deseas anadir un postre al pedido?\n\t1. Si\n\t2. No");
		if (Escaner.solicitarOpcion(2) == 1) {
			pos = Postres.seleccionarPostre();
		}
		// Establecemos el precio de los postres
		Double precioPos = 0.00;
		if (pos != null) {
			precioPos = pos.getPrecioVenta();
		}

		// Establecemos el precio de los productos
		Double precio = (ham.getPrecioVenta() + com.getPrecioVenta() + precioPos);
		return new MenuPedido(ham, com, beb, pos, tam, precio);
	}

	/**
	 * Permite personalizar un menú de pedido modificando sus componentes:
	 * hamburguesa, complemento, bebida y opcionalmente el postre.
	 * 
	 * @return el menú de pedido personalizado
	 */
	public MenuPedido personalizarMenu() {
		int maximoOpciones = 4;
		System.out.println(
				"Selecciona el producto que deseas modificar\n\t1. Hamburguesa\n\t2. Complemento\n\t3. Bebida");
		if (postre != null) {
			System.out.print("\t4. Postres\n\t5. Volver atras");
			maximoOpciones = 5;
		} else {
			System.out.println("\t4. Volver atras");
		}

		// Modificamos los ingredientes
		switch (Escaner.solicitarOpcion(maximoOpciones)) {
		case 1:
			hamburguesa = ((Hamburguesa) hamburguesa.personalizarHamburguesa());
			break;
		case 2:
			System.out.println("Deseas cambiar o modificar el complemento?\n\t1. cambiar\n\t2. modificar");
			if (Escaner.solicitarOpcion(2) == 1) { // Modificamos la bebida
				complemento = seleccionarComplementoMenu(tamano);
			}
			complemento.setSalsas(Salsas.seleccionarSalsa(complemento));
			break;
		case 3:
			System.out.println("Deseas cambiar o modificar la bebida?\n\t1. cambiar\n\t2. modificar");
			if (Escaner.solicitarOpcion(2) == 1) { // Modificamos la bebida
				bebida = seleccionarBebida(tamano);
			}
			bebida.personalizarBebida();
			break;
		case 4:
			if (postre != null) { // Si el postre existe
				System.out.println("Deseas modificar o eliminar el postre?\n\t1. Modificar\n\t2. Eliminar");
				if (Escaner.solicitarOpcion(2) == 1) { // Modificamos el postre
					postre.personalizarPostre();
				} else { // Eliminamos el postre
					System.out.println("Se ha eliminado el postre");
					postre = null;
				}
			}
		case 5:
			return this;
		}
		personalizarMenu();
		return this;
	}

	/**
	 * Permite seleccionar una bebida compatible con el tamaño del menú.
	 * 
	 * @param tamano el tamaño del menú para filtrar las bebidas compatibles
	 * @return la bebida seleccionada por el usuario
	 */
	private static Bebidas seleccionarBebida(String tamano) {
		// Seleccionamos la bebida
		List<Bebidas> listadoProductos = Bebidas.listadoRefrescos();
		List<Bebidas> refrescosCompatibles = new ArrayList<Bebidas>();
		for (Bebidas ref : listadoProductos) {
			if (ref.getTamano().equals(tamano) || ref.getTamano().equalsIgnoreCase("Unico")) {
				refrescosCompatibles.add(ref);
			}
		}

		// Mostramos los refrescos disponibles
		System.out.println("Selecciona una bebida");
		for (int i = 0; i < refrescosCompatibles.size(); i++) {
			System.out.println("\t" + (i + 1) + ". " + refrescosCompatibles.get(i).getNombreProducto());
		}
		System.out.print("Seleccion: ");
		return refrescosCompatibles.get(Escaner.solicitarOpcion(refrescosCompatibles.size()) - 1);

	}

	/**
	 * Permite seleccionar un complemento compatible con el tamaño del menú.
	 * 
	 * @param tamano el tamaño del menú para filtrar los complementos compatibles
	 * @return el complemento seleccionado por el usuario
	 */
	private static Complemento seleccionarComplementoMenu(String tamano) {
		// Seleccionamos el complemento
		// Almacenamos en una variable todos los complementos
		List<Complemento> listadoProductos = Complemento.listadoComplementos();
		List<Complemento> complementosCompatibles = new ArrayList<Complemento>();
		for (Complemento com : listadoProductos) {
			if (com.getTipo_producto().equals("Patatas")) {
				if (com.getTamano().equals(tamano) || com.getTamano().equals("Unico")) {
					complementosCompatibles.add(new Complemento(com.getId()));
				}
			}
		}

		return Complemento.seleccionarComplemento(complementosCompatibles, true);
	}

	/**
	 * Permite seleccionar el tamaño del menú.
	 * 
	 * @return una cadena que indica el tamaño seleccionado ("Mediano" o "Grande")
	 */
	private static String establecerTamanoMenu() {
		// Establecemos el tamano del menu
		System.out.println("Selecciona el tamano\n\t1. Mediano \n\t2. Grande");
		if (Escaner.solicitarOpcion(2) == 1) {
			return "Mediano";
		} else {
			return "Grande";
		}
	}

	/**
	 * Este metodo permite promocionar el menu y establecer todos los datos de la
	 * promocion
	 */
	public void promocionarMenu() {
		// Establecemos el atributo de promocionado a los productos
		hamburguesa.promocionarProducto();
		complemento.promocionarProducto();
		bebida.promocionarProducto();
		if (postre != null) {
			postre.promocionarProducto();
		}
		// Establecemos el atributo de promocionado o no
		if (menuPromocionado) {
			menuPromocionado = false;
		} else {
			menuPromocionado = true;
		}
		// Establecemos el precio del menu
		precioMenu = 0.00;
	}

	@Override
	public String toString() {
		String datos = "Menu " + tamano + " (" + precioMenu + " Eur)";
		// Si el articulo esta promocionado se informara
		if (menuPromocionado) {
			datos = datos + "\tPRM";
		}
		datos = datos + System.lineSeparator() + "\t" + hamburguesa.toString() + System.lineSeparator() + "\t" + complemento.toString()
				+ System.lineSeparator() + "\t" + bebida.toString();
		if (postre != null) {
			datos = datos + System.lineSeparator() + "\t" + postre.toString();
		}
		return datos;
	}

	// Getters && Setters
	public Double getPrecioMenu() {
		return precioMenu;
	}

	public boolean isMenuPromocionado() {
		return menuPromocionado;
	}

	public void setMenuPromocionado(boolean menuPromocionado) {
		this.menuPromocionado = menuPromocionado;
	}

	public void setPrecioMenu(Double precioMenu) {
		this.precioMenu = precioMenu;
	}

}
