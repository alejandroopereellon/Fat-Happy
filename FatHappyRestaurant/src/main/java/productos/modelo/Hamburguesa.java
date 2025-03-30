package productos.modelo;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

/**
 * Esta clase permite generar el objeto hamburguesa que hereda de
 * {@link Producto}
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "hamburguesas")
@PrimaryKeyJoinColumn(name = "codigo")
public class Hamburguesa extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5792154736849070272L;

	@Column(name = "opcionMenu")
	private boolean opcionMenu;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "hamburguesa_ingredientes", joinColumns = @JoinColumn(name = "codigo_hamburguesa"), inverseJoinColumns = @JoinColumn(name = "codigo_ingrediente"))
	private List<Ingrediente> listaIngredientes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "hamburguesa_extras", joinColumns = @JoinColumn(name = "codigo_hamburguesa"), inverseJoinColumns = @JoinColumn(name = "codigo_extra"))
	private List<Extra> listaExtras;

	/**
	 * constructor para generar las hamburguesas de productos con todos sus
	 * parametros
	 * 
	 * @param codigo                 el codigo unico de la hamburguesa
	 * @param nombreProducto         el nombre de la hamburguesa
	 * @param categoria              la categoria a la que pertenece el producto,
	 *                               por ejemplo "hamburguesa"
	 * @param tipoProducto           el tipo de la hamburguesa, por ejemplo "carne",
	 *                               "pollo", etc.
	 * @param precioVenta            el precio de venta de la hamburguesa
	 * @param costeEmpresa           el coste de la hamburguesa para la empresa
	 * @param productoActivo         indica si el producto esta activo (disponible
	 *                               para la venta)
	 * @param productoPromocionado   indica si el producto esta promocionado
	 * @param opcionDescuento        indica si el producto tiene alguna opcion de
	 *                               descuento
	 * @param imagenProducto64       la imagen del producto en tamaño reducido
	 *                               (64px)
	 * @param imagenProducto128      la imagen del producto en tamaño mediano
	 *                               (128px)
	 * @param imagenProductoOriginal la imagen original del producto
	 * @param opcionMenu             indica si la hamburguesa es parte de un menu
	 *                               (true o false)
	 */

	public Hamburguesa(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, boolean opcionMenu,
			List<Ingrediente> listaIngredientes, List<Extra> extras) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.opcionMenu = opcionMenu;
		this.listaIngredientes = listaIngredientes;
		this.listaExtras = extras;
	}

	// Constructor sin argumentos para hibernate
	public Hamburguesa() {

	}

	// Getters && setters
	public boolean isOpcionMenu() {
		return opcionMenu;
	}

	public void setOpcionMenu(boolean opcionMenu) {
		this.opcionMenu = opcionMenu;
	}

	public List<Ingrediente> getListaIngredientes() {
		return listaIngredientes;
	}

	public List<Extra> getExtras() {
		return listaExtras;
	}

	// toString
	@Override
	public String toString() {
		String datos = super.toString();

		// Mostramos los ingredientes
		for (Ingrediente ing : listaIngredientes) {
			if (ing.isActivo()) {
				datos = datos + ing.toString() + System.lineSeparator();
			}
		}
		// Mostramos los extras
		for (Extra ext : listaExtras) {
			if (ext.getCantidadExtra() > 0) {
				datos = datos + ext.toString() + System.lineSeparator();
			}
		}
		return datos;
	}
}
