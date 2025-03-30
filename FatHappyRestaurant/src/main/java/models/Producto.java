package models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Este metodo es el metodo principal de producto que permite la creacion y
 * modificacion de los objetos producto, de esta clase se van a heredar los
 * diferentes tipos de productos
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "productos")
public class Producto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1170377486509864736L;

	@Id
	@Column(name = "codigo")
	private int codigo;

	@Column(name = "nombre", nullable = false, length = 45)
	private String nombreProducto;

	@Column(name = "categoria", nullable = false, length = 45)
	private String categoria;

	@Column(name = "tipo", nullable = false, length = 45)
	private String tipoProducto;

	@Column(name = "precio", nullable = false)
	private BigDecimal precioVenta;

	@Column(name = "coste_empresa", nullable = false)
	private BigDecimal costeEmpresa;

	@Column(name = "producto_activo", nullable = false)
	private boolean productoActivo;

	@Transient
	private boolean productoPromocionado;

	@Column(name = "opcion_descuento", nullable = false)
	private boolean opcionDescuento;

	@Column(name = "imagen_producto_64", length = 150)
	private String imagenProducto64;

	@Column(name = "imagen_producto_128", length = 150)
	private String imagenProducto128;

	@Column(name = "imagen_producto_original", length = 150)
	private String imagenProductoOriginal;

	@Transient
	private boolean stockDisponible;

	/**
	 * Constructor dedicado a la creacion del objeto producto
	 *
	 * @param id                     codigo del producto
	 * @param nombreProducto         nombre del producto
	 * @param categoria              categoria a la que pertenece (hamburguesas,
	 *                               complemento, bebidas...)
	 * @param tipo_producto          es el tipo de producto( si es hamburguesa carne
	 *                               o pollo etc);
	 * @param precioVenta            es el PVP
	 * @param costeEmpresa           coste de la empresa
	 * @param productoActivo         indica si el producto esta activo
	 * @param productoPromocionado   indica si el producto esta promocionado o no
	 * @param opcionDescuento        indica si el producto tiene la opcion de
	 *                               descuento o no
	 * @param imagenProductoOriginal indica la ruta relativa donde esta el producto
	 *                               con la imagen original
	 * @param imagenProducto64       indica la ruta del archivo de 64x64
	 * @param imagenProducto128      indica la ruta del archivo de 128x128
	 */

	public Producto(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal) {
		this.codigo = codigo;
		this.nombreProducto = nombreProducto;
		this.categoria = categoria;
		this.tipoProducto = tipoProducto;
		this.precioVenta = precioVenta;
		this.costeEmpresa = costeEmpresa;
		this.productoActivo = productoActivo;
		this.productoPromocionado = productoPromocionado;
		this.opcionDescuento = opcionDescuento;
		this.imagenProducto64 = imagenProducto64;
		this.imagenProducto128 = imagenProducto128;
		this.imagenProductoOriginal = imagenProductoOriginal;
	}

	/**
	 * Este constructor sin parametros se utiliza para la creacion del objeto
	 * producto sin paramertros, dedicado a hibernate y json
	 */
	public Producto() {
	}

	// Getters && Setters

	public boolean isProductoActivo() {
		return productoActivo;
	}

	public void setProductoActivo(boolean productoActivo) {
		this.productoActivo = productoActivo;
	}

	public boolean isProductoPromocionado() {
		return productoPromocionado;
	}

	public void setProductoPromocionado(boolean productoPromocionado) {
		this.productoPromocionado = productoPromocionado;
	}

	public boolean isOpcionDescuento() {
		return opcionDescuento;
	}

	public void setOpcionDescuento(boolean opcionDescuento) {
		this.opcionDescuento = opcionDescuento;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public BigDecimal getCosteEmpresa() {
		return costeEmpresa;
	}

	public String getImagenProducto64() {
		return imagenProducto64;
	}

	public String getImagenProducto128() {
		return imagenProducto128;
	}

	public String getImagenProductoOriginal() {
		return imagenProductoOriginal;
	}

	public boolean isStockDisponible() {
		return stockDisponible;
	}

	public void setStockDisponible(boolean stockDisponible) {
		this.stockDisponible = stockDisponible;
	}

	// toString
	@Override
	public String toString() {
		return nombreProducto + "\t" + precioVenta;
	}
}
