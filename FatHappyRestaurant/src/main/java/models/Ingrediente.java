package models;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * clase que representa los ingredientes disponibles para los productos cada
 * ingrediente tiene un id unico, un nombre, un estado activo y una ruta de
 * imagen
 * 
 * @author Alejandro Perellón López
 */
@Entity
public class Ingrediente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6963124789621050534L;

	// Atributo que representa el id unico del ingrediente
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private int id;

	// Atributo que representa el nombre del ingrediente
	@Column(name = "nombre", length = 45, nullable = false)
	private String nombreIngrediente;

	// Atributo que indica si el ingrediente esta activo (disponible para el menu)
	@Transient // Indica que este campo no se debe persistir en la base de datos
	private boolean activo;

	// Atributo que representa la ruta de la imagen asociada al ingrediente
	@Column(name = "imagen_ingrediente", length = 120, nullable = false)
	private String rutaImagenIngrediente;

	// Constructor vacío para Hibernate
	public Ingrediente() {
		this.activo = true; // inicializamos el ingrediente como activo
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
