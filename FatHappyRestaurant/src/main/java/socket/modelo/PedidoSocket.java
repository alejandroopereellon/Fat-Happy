package socket.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import auxiliares.singleton.ClasesEstaticas;
import pedido.modelo.Pedido;
import productos.modelo.Producto;
import restaurante.modelo.Restaurante;

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

	private final int numeroRestaurante = ClasesEstaticas.getRestaurante().getIdRestaurante();

	private final int numeroCaja = ClasesEstaticas.getCaja().getNumeroCaja();

	private final int numeroPedido;

	/**
	 * -1 LLEVAR 0 TOMAR SIN MESA !0 LLevar a mesa al numer indicado
	 */
	private final int estadoEntrega;

	private final List<Producto> listaProductos = new ArrayList<Producto>();

	public PedidoSocket(int numeroPedido, int estadoEntrega) {
		this.numeroPedido = numeroPedido;
		this.estadoEntrega = estadoEntrega;
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
				+ numeroPedido + "]";
	}

}
