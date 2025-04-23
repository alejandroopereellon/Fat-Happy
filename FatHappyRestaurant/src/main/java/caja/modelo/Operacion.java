package caja.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import pedido.modelo.Pedido;

/**
 * Clase que genera una operacion
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "operaciones")
public class Operacion {

    @Id
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "id_operacion", columnDefinition = "BINARY(16)")
    private UUID id = UUID.randomUUID();


	@ManyToOne
	@JoinColumn(name = "id_caja")
	private Caja caja;

	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora = LocalDateTime.now();

	@Column(name = "tipo_operacion")
	private String tipoOperacion;

	@Column(name = "importe")
	private BigDecimal importe;

	@Column(name = "metodo_cobro", length = 45)
	private String metodoCobro;

	// Constructor por defecto
	public Operacion() {
	}

	// Getters y setters
	public UUID getId() {
		return id;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getMetodoCobro() {
		return metodoCobro;
	}

	public void setMetodoCobro(String metodoCobro) {
		this.metodoCobro = metodoCobro;
	}
}
