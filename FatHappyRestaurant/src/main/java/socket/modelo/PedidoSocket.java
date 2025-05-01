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

	@SuppressWarnings("unused")
	private int numeroRestaurante = ClasesEstaticas.getRestaurante().getIdRestaurante();

	@SuppressWarnings("unused")
	private int numeroCaja = ClasesEstaticas.getCaja().getNumeroCaja();

	private int numeroPedido = ClasesEstaticas.getPedido().getNumeroPedido();

	/**
	 * -1 LLEVAR 0 TOMAR SIN MESA !0 LLevar a mesa al numer indicado
	 */
	@SuppressWarnings("unused")
	private int estadoEntrega = 0;

	private List<Producto> listaProductos = new ArrayList<Producto>();

	public PedidoSocket(int estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

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

}
