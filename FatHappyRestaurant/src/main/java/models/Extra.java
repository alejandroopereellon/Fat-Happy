package models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * clase que representa los extras disponibles para los productos en este caso,
 * cada extra tiene un codigo unico, nombre, cantidad, coste y una imagen
 * asociada
 */
@Entity

public class Extra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990084695199830136L;

	// Atributo que representa el id del extra
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private int codigo;

	// Atributo que representa el nombre del extra
	@Column(name = "nombre", length = 45, nullable = false)
	private String nombreExtra;

	// Atributo que representa la cantidad del extra
	@Column(name = "cantidadExtra", nullable = false)
	private int cantidadExtra;

	// Atributo que representa el coste del extra
	@Column(name = "costeExtra", nullable = false)
	private BigDecimal costeExtra;

	// Atributo que representa el maximo de extras permitidos
	@Column(name = "maximoExtras", nullable = false)
	private int maximoExtras;

	// Atributo que representa la ruta de la imagen asociada al extra
	@Column(name = "rutaImagen", length = 120, nullable = false)
	private final String rutaImagen;

	// Constructor con parámetros
	public Extra(int codigo, String nombreExtra, int cantidadExtra, BigDecimal costeExtra, int maximoExtras,
			String rutaImagen) {
		this.codigo = codigo;
		this.nombreExtra = nombreExtra;
		this.cantidadExtra = cantidadExtra;
		this.costeExtra = costeExtra;
		this.maximoExtras = maximoExtras;
		this.rutaImagen = rutaImagen;
	}

	// Getters y setters

	public int getCantidadExtra() {
		return cantidadExtra;
	}

	public void setCantidadExtra(int cantidadExtra) {
		this.cantidadExtra = cantidadExtra;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNombreExtra() {
		return nombreExtra;
	}

	public BigDecimal getCosteExtra() {
		return costeExtra;
	}

	public int getMaximoExtras() {
		return maximoExtras;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	// ToString
	@Override
	public String toString() {
		if (cantidadExtra > 0) {
			return "\n\tExtra " + cantidadExtra + " " + nombreExtra;
		}
		return "";
	}
}
