package models;

import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Esta clase permite generar el objeto postre que hereda de {@link Producto}
 * 
 * @autor Alejandro Perellón López
 */
@Entity
@Table(name = "postres")
@PrimaryKeyJoinColumn(name = "codigo")
public class Postre extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161558963465451979L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente1", referencedColumnName = "codigo")
	private Ingrediente ingrediente1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ingrediente2", referencedColumnName = "codigo")
	private Ingrediente ingrediente2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra1", referencedColumnName = "codigo")
	private Extra extra1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra2", referencedColumnName = "codigo")
	private Extra extra2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra3", referencedColumnName = "codigo")
	private Extra extra3;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra4", referencedColumnName = "codigo")
	private Extra extra4;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "extra5", referencedColumnName = "codigo")
	private Extra extra5;

	@Transient
	private boolean recogerDespues;

	/**
	 * constructor para generar los postres de productos con todos sus parametros
	 * 
	 * @param codigo                 el codigo unico del postre
	 * @param nombreProducto         el nombre del postre
	 * @param categoria              la categoria a la que pertenece el producto,
	 *                               por ejemplo "postre"
	 * @param tipoProducto           el tipo del postre
	 * @param precioVenta            el precio de venta del postre
	 * @param costeEmpresa           el coste de la empresa por el postre
	 * @param productoActivo         indica si el producto esta activo
	 * @param productoPromocionado   indica si el producto esta promocionado
	 * @param opcionDescuento        indica si el producto tiene alguna opción de
	 *                               descuento
	 * @param imagenProducto64       la imagen del producto en tamaño reducido
	 * @param imagenProducto128      la imagen del producto en tamaño mediano
	 * @param imagenProductoOriginal la imagen original del producto
	 * @param ingrediente1           el primer ingrediente del postre
	 * @param ingrediente2           el segundo ingrediente del postre
	 * @param extra1                 el primer extra del postre
	 * @param extra2                 el segundo extra del postre
	 * @param extra3                 el tercer extra del postre
	 * @param extra4                 el cuarto extra del postre
	 * @param extra5                 el quinto extra del postre
	 */
	public Postre(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, Ingrediente ingrediente1,
			Ingrediente ingrediente2, Extra extra1, Extra extra2, Extra extra3, Extra extra4, Extra extra5) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.ingrediente1 = ingrediente1;
		this.ingrediente2 = ingrediente2;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
		this.extra4 = extra4;
		this.extra5 = extra5;
	}

	// Constructor sin parametros
	public Postre() {

	}

	// Getters y Setters

	public boolean isRecogerDespues() {
		return recogerDespues;
	}

	public void setRecogerDespues(boolean recogerDespues) {
		this.recogerDespues = recogerDespues;
	}

	public Ingrediente getIngrediente1() {
		return ingrediente1;
	}

	public Ingrediente getIngrediente2() {
		return ingrediente2;
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

	public Extra getExtra4() {
		return extra4;
	}

	public Extra getExtra5() {
		return extra5;
	}

	// toString
	@Override
	public String toString() {
		StringBuilder datos = new StringBuilder(getNombreProducto());

		if (getTipoProducto().equalsIgnoreCase("Helado")) {
			if (recogerDespues) {
				datos.append("\n\tRecoger después");
			}

			// Añadimos la información de los ingredientes
			if (ingrediente1 != null) {
				datos.append("\n\tIngrediente 1: ").append(ingrediente1.toString());
			}
			if (ingrediente2 != null) {
				datos.append("\n\tIngrediente 2: ").append(ingrediente2.toString());
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
			if (extra4 != null) {
				datos.append("\n\tExtra 4: ").append(extra4.toString());
			}
			if (extra5 != null) {
				datos.append("\n\tExtra 5: ").append(extra5.toString());
			}
		}

		return datos.toString();
	}

}
