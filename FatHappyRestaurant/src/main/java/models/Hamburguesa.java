package models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente1", referencedColumnName = "codigo")
	private Ingrediente ingrediente1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente2", referencedColumnName = "codigo")
	private Ingrediente ingrediente2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente3", referencedColumnName = "codigo")
	private Ingrediente ingrediente3;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente4", referencedColumnName = "codigo")
	private Ingrediente ingrediente4;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente5", referencedColumnName = "codigo")
	private Ingrediente ingrediente5;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente6", referencedColumnName = "codigo")
	private Ingrediente ingrediente6;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente7", referencedColumnName = "codigo")
	private Ingrediente ingrediente7;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra1", referencedColumnName = "codigo")
	private Extra extra1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra2", referencedColumnName = "codigo")
	private Extra extra2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra3", referencedColumnName = "codigo")
	private Extra extra3;

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
	 * @param ingrediente1           id del primer ingrediente de la hamburguesa
	 * @param ingrediente2           id del segundo ingrediente de la hamburguesa
	 * @param ingrediente3           id del tercer ingrediente de la hamburguesa
	 * @param ingrediente4           id del cuarto ingrediente de la hamburguesa
	 * @param ingrediente5           id del quinto ingrediente de la hamburguesa
	 * @param ingrediente6           id del sexto ingrediente de la hamburguesa
	 * @param ingrediente7           id del septimo ingrediente de la hamburguesa
	 * @param extra1                 id del primer extra de la hamburguesa
	 * @param extra2                 id del segundo extra de la hamburguesa
	 * @param extra3                 id del tercer extra de la hamburguesa
	 */

	public Hamburguesa(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, boolean opcionMenu,
			Ingrediente ingrediente1, Ingrediente ingrediente2, Ingrediente ingrediente3, Ingrediente ingrediente4,
			Ingrediente ingrediente5, Ingrediente ingrediente6, Ingrediente ingrediente7, Extra extra1, Extra extra2,
			Extra extra3) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.opcionMenu = opcionMenu;
		this.ingrediente1 = ingrediente1;
		this.ingrediente2 = ingrediente2;
		this.ingrediente3 = ingrediente3;
		this.ingrediente4 = ingrediente4;
		this.ingrediente5 = ingrediente5;
		this.ingrediente6 = ingrediente6;
		this.ingrediente7 = ingrediente7;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
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

	public Ingrediente getIngrediente1() {
		return ingrediente1;
	}

	public Ingrediente getIngrediente2() {
		return ingrediente2;
	}

	public Ingrediente getIngrediente3() {
		return ingrediente3;
	}

	public Ingrediente getIngrediente4() {
		return ingrediente4;
	}

	public Ingrediente getIngrediente5() {
		return ingrediente5;
	}

	public Ingrediente getIngrediente6() {
		return ingrediente6;
	}

	public Ingrediente getIngrediente7() {
		return ingrediente7;
	}

	public Extra getExtra1() {
		return extra1;
	}

	public Extra getExtra2() {
		return extra2;
	}

	public Extra getExtra3() {
		return extra3;
	}

	@Override
	public String toString() {

		StringBuilder datos = new StringBuilder(getNombreProducto());

		// Añadimos la información de los ingredientes
		if (ingrediente1 != null) {
			datos.append("\n\tIngrediente 1: ").append(ingrediente1.toString());
		}
		if (ingrediente2 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente2.toString());
		}
		if (ingrediente3 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente3.toString());
		}
		if (ingrediente4 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente4.toString());
		}
		if (ingrediente5 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente5.toString());
		}
		if (ingrediente6 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente6.toString());
		}
		if (ingrediente7 != null) {
			datos.append("\n\tIngrediente 2: ").append(ingrediente7.toString());
		}

		if (extra1 != null) {
			datos.append("\n\tExtra 5: ").append(extra1.toString());
		}
		if (extra2 != null) {
			datos.append("\n\tExtra 5: ").append(extra2.toString());
		}
		if (extra3 != null) {
			datos.append("\n\tExtra 5: ").append(extra3.toString());
		}

		// Añadimos la información de los extras
		if (extra1 != null) {
			datos.append("\n\tExtra 1: ").append(extra1.toString());
		}
		if (extra2 != null) {
			datos.append("\n\tExtra 2: ").append(extra2.toString());
		}
		if (extra3 != null) {
			datos.append("\n\tExtra 3: ").append(extra3.toString());
		}

		return datos.toString();
	}

	// toString
//	@Override
//	public String toString() {
//		String datos = super.toString();
//
//		// Mostramos los ingredientes
//		for (Ingrediente ing : ingrendientes) {
//			if (!ing.isActivo()) {
//				datos = datos + ing.toString();
//			}
//		}
//		// Mostramos los extras
//		for (Extra ext : extras) {
//			if (ext.getCantidadExtra() > 0) {
//				datos = datos + ext.toString();
//			}
//		}
//		return datos;
}
