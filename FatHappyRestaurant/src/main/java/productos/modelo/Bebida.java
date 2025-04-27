package productos.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Esta clase permite generar el objeto bebidas que hereda de {@link Producto}
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "bebidas")
@PrimaryKeyJoinColumn(name = "codigo")
public class Bebida extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782110912587635477L;

	@Column(name = "nombre_extra")
	private String nombreExtra;

	@Column(name = "extra_activo")
	private boolean extraActivo;

	@Column(name = "tamano")
	private int tamano;

	// Constructor para la clase Bebidas
	public Bebida(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, String nombreExtra,
			boolean extraActivo, int tamano) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.nombreExtra = nombreExtra;
		this.extraActivo = extraActivo;
		this.tamano = tamano;
	}

	public Bebida() {

	}

	// Getters && Setters
	public boolean isExtraActivo() {
		return extraActivo;
	}

	public void setExtraActivo(boolean extraActivo) {
		this.extraActivo = extraActivo;
	}

	public String getNombreExtra() {
		return nombreExtra;
	}

	public int getTamano() {
		return tamano;
	}

	@Override
	public String toString() {

		StringBuilder texto = new StringBuilder();

		texto.append(this.getNombreProducto());
		texto.append(" (" + this.getPrecioVenta() + " Eur)");

		if (this.isProductoPromocionado()) {
			texto.append(System.lineSeparator());
			texto.append("Promocionado");
		}

		if (nombreExtra != null) {
			if (!extraActivo && nombreExtra.equalsIgnoreCase("hielo")) {
				texto.append(System.lineSeparator() + "\tSin hielo");
			} else if (extraActivo && nombreExtra.equalsIgnoreCase("del tiempo")) {
				texto.append(System.lineSeparator() + "\tExtra del tiempo");
			}
		}
		return texto.toString();
	}
}
