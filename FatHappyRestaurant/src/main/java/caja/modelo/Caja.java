package caja.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import empleados.modelo.Empleado;
import empleados.modelo.EmpleadoDatos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import restaurante.modelo.Restaurante;
import restaurante.modelo.RestauranteDatos;

/**
 * Clase que representa una caja en un sistema de gestión de cajas. Una caja
 * tiene un número identificador, un empleado responsable, un importe inicial y
 * final, una lista de operaciones realizadas, su estado, un número de sesión,
 * una ruta asociada para almacenar datos, y momentos de apertura y cierre.
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "cajas")
public class Caja implements Serializable {

	/** Serializacion para json */
	private static final long serialVersionUID = -1088705188844985876L;

	/** Identificacion de la caja */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/** Restaurante al que pertenece la caja */
	@ManyToOne
	@Column(name = "id_restaurante")
	private Restaurante restaurante;

	/** Empleado responsable de la caja. */
	@ManyToOne
	@Column(name = "id_empleado")
	private Empleado empleado;

	/** Número identificador de la caja. */
	@Column(name = "numero_caja")
	private int numeroCaja;

	/** Número de sesión asociado a la apertura de la caja. */
	@Column(name = "numero_sesion")
	private int numeroSesion;

	/** Momento exacto en que se abrió la caja. */
	@Column(name = "fecha_hora_inicio")
	private LocalDateTime momentoApertura;

	/** Momento exacto en que se cerró la caja. */
	@Column(name = "fecha_hora_final")
	private LocalDateTime momentoCierre;

	/** Importe inicial depositado al abrir la caja. */
	@Column(name = "importe_inicial")
	private BigDecimal importeInicial;

	/** Importe final registrado al cerrar la caja. */
	@Column(name = "importe_final")
	private BigDecimal importeFinal;

	/**
	 * Constructor que inicializa una nueva instancia de la clase Cajas. Crea la
	 * estructura de directorios correspondiente a la caja y sesión si no existe.
	 * 
	 * @param numeroCaja     Número identificador de la caja.
	 * @param empleado       Empleado responsable de la caja.
	 * @param importeInicial Importe inicial depositado al abrir la caja.
	 * @param numeroSesion   Número de sesión asociado a la apertura de la caja.
	 */
	public Caja(int numeroCaja, int numeroSesion, LocalDateTime momentoApertura, LocalDateTime momentoCierre,
			BigDecimal importeInicial, BigDecimal importeFinal) {
		this.restaurante = RestauranteDatos.get();
		this.empleado = EmpleadoDatos.get();
		this.numeroCaja = numeroCaja;
		this.numeroSesion = numeroSesion;
		this.momentoApertura = momentoApertura;
		this.momentoCierre = momentoCierre;
		this.importeInicial = importeInicial;
		this.importeFinal = importeFinal;
	}

	/**
	 * Constructor vacio para servicios como hibernate
	 */
	public Caja() {
	}

	// Getters y setters

	public int getId() {
		return id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	// toString
	@Override
	public String toString() {
		return "Cajas [id=" + id + ", restaurante=" + restaurante + ", empleado=" + empleado + ", numeroCaja="
				+ numeroCaja + ", numeroSesion=" + numeroSesion + ", momentoApertura=" + momentoApertura
				+ ", momentoCierre=" + momentoCierre + ", importeInicial=" + importeInicial + ", importeFinal="
				+ importeFinal + "]";
	}

}
