package productos.interfazPedido.SeleccionProductos.ProductoSeleccionado;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.List;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Extra;
import productos.modelo.Hamburguesa;
import productos.modelo.Ingrediente;
import productos.modelo.Postre;
import productos.modelo.Producto;
import productos.modelo.Salsa;

/**
 * Clase que contiene todos los metodos de {@link PanelProductoSeleccionado} y
 * permite su ejecucion
 * 
 * @author Alejandro Perellón López
 */
public class PanelProductoSeleccionadoMetodos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelProductoSeleccionadoMetodos.class);
	// Establecemos la interfaz
	private PanelProductoSeleccionado interfaz;

	public PanelProductoSeleccionadoMetodos(PanelProductoSeleccionado interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarPanel() {
		// Obtenemos el producto
		Producto pro = interfaz.getProducto();

		// Establecemos el nombre del producto
		interfaz.getNombreProducto().setText(pro.getNombreProducto());
		logger.debug("Se ha establecido el nombre del producto a {}", pro.getNombreProducto());
		// Establecemos el precio del prodcuto
		interfaz.getPrecioProducto().setText(pro.getPrecioVenta().toString());
		logger.debug("Se ha establecido el precio del producto a {}", pro.getPrecioVenta());
		// Establecemos la informacion adicional del producto
		obtenerInformacionAdicional(pro);
		// Actualizamos la altura del texto
		ajustarAlturaTexto(interfaz.getInformacionAdicional());
	}

	/**
	 * Metodo que dependiendo del tipo de producto que sea se va a insertar va a
	 * realizar unas acciones u otras
	 * 
	 * @param pro es el {@link Producto} que se va a añadir
	 */
	private void obtenerInformacionAdicional(Producto pro) {
		if (pro instanceof Hamburguesa) {
			informacionHamburguesa(pro);
		} else if (pro instanceof Postre) {
			informacionPostres(pro);
		} else if (pro instanceof Complemento) {
			informacionComplementos(pro);
		} else if (pro instanceof Bebida) {
			informacionBebidas(pro);
		}

	}

	/**
	 * Metodo que establece la informacion sobre una bebida
	 * 
	 * @param pro es el {@link Producto}
	 */
	private void informacionBebidas(Producto pro) {
		logger.debug("El producto es una bebida ");
		Bebida beb = (Bebida) pro;

		// Si la bebida tiene su extra activo lo mostramos
		if (beb.isExtraActivo()) {
			interfaz.getInformacionAdicional().append(beb.getNombreExtra() + System.lineSeparator());
		}
	}

	/**
	 * Metodo que establece la informacion sobre un complemento
	 * 
	 * @param pro es el {@link Producto}
	 */
	private void informacionComplementos(Producto pro) {
		logger.debug("El producto es un complemento ");
		Complemento com = (Complemento) pro;

		// Establecemos la informacion de las salsas
		Boolean noMasSalsas = false;
		// Recorremos todas las salsas que existan
		for (Salsa sal : com.getSalsas()) {
			// Controlamos si el usuario no quiere mas salsas
			if (sal.getCodigo() == 20050509) {
				noMasSalsas = true;
				break;
			} else {
				interfaz.getInformacionAdicional().append(sal.toString() + System.lineSeparator());
			}

		}
		/**
		 * Si la cantidad de salsas seleccionadas es menor que el numero de salsas
		 * disponibles y quiere mas salsas se mostrará la opcion de seleccionar salsa el
		 * numero de veces que no se hayan seleccionado salsas
		 */
		if ((com.getSalsas().size() < com.getNumeroSalsas()) && !noMasSalsas) {
			for (int i = 0; i < (com.getNumeroSalsas() - com.getSalsas().size()); i++) {
				interfaz.getInformacionAdicional().append(System.lineSeparator() + "Escoger salsa");
			}
		}

	}

	/**
	 * Metodo que establece la informacion sobre un postre
	 * 
	 * @param pro es el {@link Producto}
	 */
	private void informacionPostres(Producto pro) {
		logger.debug("El producto es un postre ");
		Postre pos = (Postre) pro;
		// Establecemos los ingredientes en la informacion adicional
		establecerIngredientes(pos.getListaIngredientes());
		// Establecemos los extras
		establecerExtras(pos.getListaExtras());

		// Si el producto es para despues lo añadimos
		if (pos.isOpcionRecogerDespues()) {
			interfaz.getInformacionAdicional().append("Para recoger despues");
		}
	}

	/**
	 * Metodo que establece la informacion sobre una hamburguesa
	 * 
	 * @param pro es el {@link Producto}
	 */
	private void informacionHamburguesa(Producto pro) {
		logger.debug("El producto es una hamburguesa ");
		Hamburguesa ham = (Hamburguesa) pro;
		// Establecemos los ingredientes en la informacion adicional
		establecerIngredientes(ham.getListaIngredientes());
		// Establecemos los extras
		establecerExtras(ham.getExtras());
	}

	/**
	 * Metodo que añade al {@link JTextArea} de informacion los datos sobre los
	 * ingredientes
	 * 
	 * @param listaIngredientes {@link List}a de {@link Ingrediente}s
	 */
	private void establecerIngredientes(List<Ingrediente> listaIngredientes) {
		for (Ingrediente ingrediente : listaIngredientes) {
			if (!ingrediente.isActivo()) {
				interfaz.getInformacionAdicional()
						.append("SIN " + ingrediente.getNombreIngrediente() + System.lineSeparator());
				logger.debug("Se ha marcado el ingrediente {}", ingrediente.getNombreIngrediente());
			}
		}

	}

	/**
	 * Metodo que añade al {@link JTextArea} de informacion los datos sobre los
	 * extras
	 * 
	 * @param listaIngredientes {@link List}a de {@link Ingrediente}s
	 */
	private void establecerExtras(List<Extra> listaExtras) {
		for (Extra extra : listaExtras) {
			if (extra.getCantidadExtra() > 0) {
				interfaz.getInformacionAdicional().append(
						"EXTRA " + extra.getCantidadExtra() + " " + extra.getNombreExtra() + System.lineSeparator());
				logger.debug("Se ha marcado el extra {}", extra.getNombreExtra());
			}
		}

	}

	/**
	 * Metodo que establece el tamaño de la informacion del producto
	 * 
	 * @param area es el {@link JTextArea} que se va a cambiar el tamano
	 */
	private void ajustarAlturaTexto(JTextArea area) {
		// Obtén el ancho actual del área
		int ancho = area.getWidth();
		if (ancho <= 0)
			ancho = 200; // Valor por defecto si aún no está renderizado

		// Fuente y métrica
		FontMetrics fm = area.getFontMetrics(area.getFont());
		int altoLinea = fm.getHeight();

		// Calculamos las líneas necesarias según el contenido y ancho
		int lineas = 0;
		for (String linea : area.getText().split("\n")) {
			int anchoLinea = fm.stringWidth(linea);
			lineas += Math.max(1, (int) Math.ceil((double) anchoLinea / ancho));
		}

		// Altura total necesaria
		int alturaTotal = altoLinea * lineas + 10; // 10px extra por margen/padding
		area.setPreferredSize(new Dimension(ancho, alturaTotal));
		area.revalidate();
	}

}
