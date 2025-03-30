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
@Table(name = "extras")
public class Extra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990084695199830136L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private int codigo;

	@Column(name = "nombre", length = 45, nullable = false)
	private String nombreExtra;

	@Column(name = "coste_extra", nullable = false)
	private BigDecimal costeExtra;

	@Column(name = "cantidad_maxima", nullable = false)
	private int maximoExtras;

	@Column(name = "ruta_imagen", length = 120, nullable = false)
	private String rutaImagen;

	@Transient
	private int cantidadExtra;

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

	// Constructor sin parametros para hibernate
	public Extra() {
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

	// toString
	@Override
	public String toString() {
		if (nombreExtra != null &&cantidadExtra > 0) {
			return "\n\tExtra " + cantidadExtra + " " + nombreExtra;
		}
		return "";
	}
}
