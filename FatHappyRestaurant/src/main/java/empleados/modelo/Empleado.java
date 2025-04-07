package empleados.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;

/**
 * Esta clase empleados reune todos los datos de los empleados que estan en
 * plantilla en la empresa
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "empleados")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empleado")
	private Integer idEmpleado;

	@Column(name = "id_restaurante", nullable = false)
	private Integer idRestaurante;

	@Column(name = "id_restaurante_empleado", nullable = false)
	private Integer idRestauranteEmpleado;

	@Column(name = "dni", length = 9, nullable = false, unique = true)
	private String dni;

	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;

	@Column(name = "apellido", length = 50, nullable = false)
	private String apellido;

	@Column(name = "fecha_nacimiento", nullable = false)
	private LocalDate fechaNacimiento;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "puesto", length = 50, nullable = false)
	private String puesto;

	@Column(name = "permisos")
	private Integer permisos;

	// Constructores

	public Empleado() {
	}

	public Empleado(Integer idRestaurante, Integer idRestauranteEmpleado, String dni, String nombre, String apellido,
			LocalDate fechaNacimiento, String email, String puesto, Integer permisos) {
		this.idRestaurante = idRestaurante;
		this.idRestauranteEmpleado = idRestauranteEmpleado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.puesto = puesto;
		this.permisos = permisos;
	}

	// Getters y setters

	public Integer getIdRestauranteEmpleado() {
		return idRestauranteEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getPermisos() {
		return permisos;
	}

	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public String getApellido() {
		return apellido;
	}

	// toString
	@Override
	public String toString() {
		return "Empleado{" + "idEmpleado=" + idEmpleado + ", dni='" + dni + '\'' + ", nombre='" + nombre + '\''
				+ ", apellido='" + apellido + '\'' + ", puesto='" + puesto + '\'' + '}';
	}

}