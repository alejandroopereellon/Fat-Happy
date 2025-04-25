package productos.modelo;

import java.time.LocalDateTime;

import auxiliares.singleton.ClasesEstaticas;
import caja.modelo.Operacion;
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
@Table(name = "productos_vendidos")
public class ProductoVendido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_restaurante")
	private Restaurante restaurante = ClasesEstaticas.getRestaurante();

	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Producto producto;

	@ManyToOne
	@JoinColumn(name = "id_operacion", columnDefinition = "BINARY(16)")
	private Operacion operacion;

	@Column(name = "fecha_venta")
	private final LocalDateTime fechaHora = LocalDateTime.now();

	@Column(name = "datos_completos")
	String datosCompletos;

	public ProductoVendido() {
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public void setDatosCompletos(String datosCompletos) {
		this.datosCompletos = datosCompletos;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}
	
	

}
