package caja.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import empleados.modelo.Empleado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import pedido.modelo.Pedido;
import restaurante.modelo.Restaurante;

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
	@JoinColumn(name = "id_restaurante")
	private Restaurante restaurante;

	/** Empleado responsable de la caja. */
	@ManyToOne
	@JoinColumn(name = "id_empleado")
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
	@Column(name = "importe_inicio")
	private BigDecimal importeInicial;

	/** Importe final registrado al cerrar la caja. */
	@Column(name = "importe_final")
	private BigDecimal importeFinal;

	@Transient
	private List<Operacion> listaOperaciones;

	@Transient
	private Pedido pedidoActual;

	/**
	 * Constructor vacio para servicios como hibernate y el builder
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

	public void setId(int id) {
		this.id = id;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

	public void setNumeroSesion(int numeroSesion) {
		this.numeroSesion = numeroSesion;
	}

	public void setMomentoApertura(LocalDateTime momentoApertura) {
		this.momentoApertura = momentoApertura;
	}

	public void setMomentoCierre(LocalDateTime momentoCierre) {
		this.momentoCierre = momentoCierre;
	}

	public void setImporteInicial(BigDecimal importeInicial) {
		this.importeInicial = importeInicial;
	}

	public void setImporteFinal(BigDecimal importeFinal) {
		this.importeFinal = importeFinal;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public int getNumeroSesion() {
		return numeroSesion;
	}

	public LocalDateTime getMomentoApertura() {
		return momentoApertura;
	}

	public LocalDateTime getMomentoCierre() {
		return momentoCierre;
	}

	public BigDecimal getImporteInicial() {
		return importeInicial;
	}

	public BigDecimal getImporteFinal() {
		return importeFinal;
	}

	public List<Operacion> getListaOperaciones() {
		return listaOperaciones;
	}

	public void setListaOperaciones(List<Operacion> listaOperaciones) {
		this.listaOperaciones = listaOperaciones;
	}

	public Pedido getPedidoActual() {
		return pedidoActual;
	}

	public void setPedidoActual(Pedido pedidoActual) {
		this.pedidoActual = pedidoActual;
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
