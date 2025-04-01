package pedido.dao;

import pedido.modelo.Pedido;

public interface PedidoDao {

	/**
	 * Metodo que inserta el pedido en la base de datos
	 * 
	 * @param pedido {@link pedido} a insertar
	 * @return TRUE si el pedido se ha insertado || FALSE si el pedido no se ha
	 *         podido insertar
	 */
	public boolean insertarPedido(Pedido pedido);

}
