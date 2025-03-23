package productos;

import java.math.BigDecimal;

import productos.productos.Producto;

public class Salsas extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3467903908490343597L;

	private transient boolean esGratis;

	public Salsas(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, boolean esGratis) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.esGratis = esGratis;
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
