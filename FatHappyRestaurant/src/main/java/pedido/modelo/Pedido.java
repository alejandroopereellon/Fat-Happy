package pedido.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import caja.modelo.Caja;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private int id;

	@ManyToOne
	@Column(name = "id_caja")
	private Caja caja;

	@Column(name = "numero_pedido")
	private int numeroPedido;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora;

	@Column(name = "tiempo_pedido")
	private int tiempoPedido;

	@Column(name = "ruta_fichero_pedido", length = 200)
	private String rutaPedido;

	@Column(name = "importe_total")
	private BigDecimal importeTotal;

	@Column(name = "descuento")
	private int descuento;

	@Transient
	private int estadoPedido;

	@Transient
	private OrdenPedido orden;

	// Constructor
	public Pedido() {
	}

	// Getters and setter

	public int getId() {
		return id;
	}

	public void setTiempoPedido(int tiempoPedido) {
		this.tiempoPedido = tiempoPedido;
	}

	public int getEstadoPedido() {
		return estadoPedido;
	}

	public String getRutaPedido() {
		return rutaPedido;
	}

	/**
	 * Este metodo cambia el estado del pedido:
	 * 
	 * 1. Preparacion 2. Edicion 3. Pendiente de pago 4. Proceso de pago 5. Pagado
	 * 
	 * @param estadoPedido es el indice numerico que indica el estado del pedido
	 */
	public void setEstadoPedido(int estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public OrdenPedido getOrden() {
		return orden;
	}

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public void setOrden(OrdenPedido orden) {
		this.orden = orden;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public void setRutaPedido(String rutaPedido) {
		this.rutaPedido = rutaPedido;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getDescuento() {
		return descuento;
	}

}
