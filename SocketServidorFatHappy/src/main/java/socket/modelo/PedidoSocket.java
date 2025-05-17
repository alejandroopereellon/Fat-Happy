package socket.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import productos.modelo.Producto;

/**
 * Clase destianada al envio de la informacion a traves del sotcket, el
 * principal objetivo es reducir el tamano de la clase al minimo, por eso no se
 * introduce informacion innecesaria como todos los datos de la clase
 * {@link Pedido} o {@link Restaurante}. Lo unico que se incluye completo son
 * los {@link Producto}s
 * 
 * @author Alejandro Perellón López
 */
public class PedidoSocket implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numeroRestaurante;

	private int numeroCaja;

	private int numeroPedido;

	/**
	 * -1 LLEVAR 0 TOMAR SIN MESA !0 LLevar a mesa al numer indicado
	 */
	private int estadoEntrega = 0;

	private List<Producto> listaProductos;

	private transient boolean confirmadoBebidas = false;

	private transient boolean confirmadoCocina = false;

	private final transient LocalDateTime momentoLlegada = LocalDateTime.now();

	// Constructor
	public PedidoSocket() {
	}

	// Getter && setters
	/**
	 * Retornamos la lista de producos para poder insertar los nuevos productos
	 * necesarios
	 * 
	 * @return
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNumeroRestaurante() {
		return numeroRestaurante;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public int getEstadoEntrega() {
		return estadoEntrega;
	}

	@Override
	public String toString() {
		return "PedidoSocket [numeroRestaurante=" + numeroRestaurante + ", numeroCaja=" + numeroCaja + ", numeroPedido="
				+ numeroPedido + ", estadoEntrega=" + estadoEntrega + ", listaProductos=" + listaProductos + "]";
	}

	public boolean isConfirmadoBebidas() {
		return confirmadoBebidas;
	}

	public void setConfirmadoBebidas(boolean confirmadoBebidas) {
		this.confirmadoBebidas = confirmadoBebidas;
	}

	public boolean isConfirmadoCocina() {
		return confirmadoCocina;
	}

	public void setConfirmadoCocina(boolean confirmadoCocina) {
		this.confirmadoCocina = confirmadoCocina;
	}

	public LocalDateTime getMomentoLlegada() {
		return momentoLlegada;
	}

}
