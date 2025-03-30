package restaurante.modelo;

import jakarta.persistence.*;

/**
 * Esta clase retaurante reune todos los datos del restaurante en el que se esta
 * trabajando, esta informacion se utilizará para otras gestiones, como
 * recuperar el stock de un producto en un restaurante concreto, o tambien a la
 * hora de generar logs, o tickets de factura
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "restaurantes")
public class Restaurante {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_restaurante", length = 11)
	private int idRestaurante;

	@Column(name = "razon_social", length = 60)
	private String razon_social;

	@Column(name = "direccion", length = 70)
	private String direccion;

	@Column(name = "provincia", length = 45)
	private String provincia;

	@Column(name = "ciudad", length = 45)
	private String ciudad;

	@Column(name = "codigo_postal", length = 11)
	private String codigoPostal;

	@Column(name = "nif", length = 9)
	private String nif;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "gerente", referencedColumnName = "id_empleado")
	private Empleado gerente;

	@Column(name = "telefono_contacto", length = 11)
	private int telefonoContacto;

	@Column(name = "email_contacto", length = 45)
	private String email_contacto;

	/**
	 * Constructor vacio del objeto restaurante enfocado para el hibernate
	 */
	public Restaurante() {
	}

	// Getters && setters

	public int getIdRestaurante() {
		return idRestaurante;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public String getNif() {
		return nif;
	}

	public Empleado getGerente() {
		return gerente;
	}

	public int getTelefonoContacto() {
		return telefonoContacto;
	}

	public String getEmail_contacto() {
		return email_contacto;
	}

}
