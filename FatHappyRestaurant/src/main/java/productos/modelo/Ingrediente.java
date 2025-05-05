package productos.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * clase que representa los ingredientes disponibles para los productos cada
 * ingrediente tiene un id unico, un nombre, un estado activo y una ruta de
 * imagen
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private int id;

	@Column(name = "nombre", length = 45, nullable = false)
	private String nombreIngrediente;

	@Transient
	private boolean activo=true;

	@Column(name = "imagen_ingrediente", length = 120, nullable = false)
	private String rutaImagenIngrediente;

	// Constructor vacío para Hibernate
	public Ingrediente() {
	}

	// Constructor con parámetros
	public Ingrediente(int id, String nombreIngrediente, String rutaIngredientes) {
		this.id = id;
		this.nombreIngrediente = nombreIngrediente;
		this.activo = true; // Por defecto, el ingrediente estará activo
		this.rutaImagenIngrediente = rutaIngredientes;
	}

	// Getters y setters

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getId() {
		return id;
	}

	public String getNombreIngrediente() {
		return nombreIngrediente;
	}

	public String getRutaIngredientes() {
		return rutaImagenIngrediente;
	}

	// toString
	@Override
	public String toString() {
		if (!activo) {
			return System.lineSeparator() + "\tSin " + nombreIngrediente;
		}
		return nombreIngrediente; // Si el ingrediente está activo, retorna su nombre
	}
}
