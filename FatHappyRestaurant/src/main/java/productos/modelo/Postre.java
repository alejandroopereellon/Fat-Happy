package productos.modelo;

import java.math.BigDecimal;
import java.util.List;

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "postre_ingredientes", joinColumns = @JoinColumn(name = "codigo_postre"), inverseJoinColumns = @JoinColumn(name = "codigo_ingrediente"))
	private List<Ingrediente> listaIngredientes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "postre_extras", joinColumns = @JoinColumn(name = "codigo_postre"), inverseJoinColumns = @JoinColumn(name = "codigo_extra"))
	private List<Extra> listaExtras;

	@Column(name = "opcion_recoger_despues")
	private boolean opcionRecogerDespues;

	@Transient
	private boolean recogerDespues;

	/**
	 * constructor para generar los postres de productos con todos sus parametros
	 * 
	 * @param codigo               el codigo unico del postre
	 * @param nombreProducto       el nombre del postre
	 * @param categoria            la categoria a la que pertenece el producto, por
	 *                             ejemplo "postre"
	 * @param tipoProducto         el tipo del postre
	 * @param precioVenta          el precio de venta del postre
	 * @param costeEmpresa         el coste de la empresa por el postre
	 * @param productoActivo       indica si el producto esta activo
	 * @param productoPromocionado indica si el producto esta promocionado
	 * @param opcionDescuento      indica si el producto tiene alguna opción de
	 */
	public Postre(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal,
			List<Ingrediente> listaIngredientes, List<Extra> listaExtras, boolean opcionRecogerDespues,
			boolean recogerDespues) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.listaIngredientes = listaIngredientes;
		this.listaExtras = listaExtras;
		this.opcionRecogerDespues = opcionRecogerDespues;
		this.recogerDespues = recogerDespues;
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

	public List<Ingrediente> getListaIngredientes() {
		return listaIngredientes;
	}

	public List<Extra> getListaExtras() {
		return listaExtras;
	}

	public boolean isOpcionRecogerDespues() {
		return opcionRecogerDespues;
	}

	// toString
	@Override
	public String toString() {
		StringBuilder texto = new StringBuilder();
		texto.append(this.getNombreProducto());

		texto.append("(" + this.getPrecioVenta() + " Eur)");

		// Mostramos los ingredientes
		for (Ingrediente ing : this.getListaIngredientes()) {
			if (!ing.isActivo()) {
				texto.append(ing.toString());
			}
		}
		// Mostramos los extras
		for (Extra ext : this.getListaExtras()) {
			if (ext.getCantidadExtra() > 0) {
				texto.append(ext.toString());
			}
		}
		return texto.toString();
	}

}
