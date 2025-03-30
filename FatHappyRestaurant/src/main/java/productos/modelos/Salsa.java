package productos.modelos;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "salsas")
@PrimaryKeyJoinColumn(name = "codigo")
public class Salsa extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3467903908490343597L;

	@Column(name = "nivel_picante")
	private int nivelPicante;

	@Transient
	private transient boolean esGratis;

	/**
	 * Constructor de salsa
	 * 
	 * @param esGratis indica si la salsa solicitada esta incluida en el precio de
	 *                 un producto o es un extra
	 */
	public Salsa(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, boolean esGratis) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.esGratis = esGratis;
	}

	// Constructor sin parametros
	public Salsa() {

	}

	// Getters and setters
	public boolean isEsGratis() {
		return esGratis;
	}

	public void setEsGratis(boolean esGratis) {
		this.esGratis = esGratis;
	}

	// Método toString para representar la salsa de manera legible
	@Override
	public String toString() {
		if (esGratis) {
			return "\tCon " + getNombreProducto();
		} else {
			return "\tExtra " + getNombreProducto();
		}
	}
}
