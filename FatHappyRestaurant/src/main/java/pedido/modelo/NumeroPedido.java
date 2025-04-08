package pedido.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import restaurante.modelo.Restaurante;

@Entity
@Table(name = "empleados")
public class NumeroPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_restaurante")
	private Restaurante restaurante;

	@Column(name = "numero_pedido")
	private int numeroPedido;

	@Column(name = "fecha")
	private LocalDate fecha;

	// Constructor

	public NumeroPedido(Restaurante restaurante, int numeroPedido, LocalDate fecha) {
		this.restaurante = restaurante;
		this.numeroPedido = numeroPedido;
		this.fecha = fecha;
	}

	public NumeroPedido() {
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}
