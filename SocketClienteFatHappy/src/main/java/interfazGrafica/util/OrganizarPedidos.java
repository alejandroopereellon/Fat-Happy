package interfazGrafica.util;

import java.util.concurrent.CopyOnWriteArrayList;

import auxiliares.singleton.ClasesEstaticas;
import socket.modelo.PedidoSocket;

public class OrganizarPedidos {
	/**
	 * Metod que se encarga de organizar los pedidos, poniendo en primer puesto los
	 * pedidos que estan sin confirmar y en segundo lugar los pedidos confirmados
	 */
	public void organizarPedidos() {
		CopyOnWriteArrayList<PedidoSocket> original = ClasesEstaticas.getListapedidos();
		CopyOnWriteArrayList<PedidoSocket> reordenada = new CopyOnWriteArrayList<PedidoSocket>();

		// Añadir primero los NO confirmados
		for (PedidoSocket pedido : original) {
			if (!pedido.isConfirmado()) {
				reordenada.add(pedido);
			}
		}

		// Añadir después los confirmados
		for (PedidoSocket pedido : original) {
			if (pedido.isConfirmado()) {
				reordenada.add(pedido);
			}
		}

		// Reemplazar el contenido de la lista original (si es mutable)
		ClasesEstaticas.setListaPedidos(reordenada);

	}
}
