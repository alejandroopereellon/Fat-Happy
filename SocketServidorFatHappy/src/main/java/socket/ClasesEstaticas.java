package socket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import socket.modelo.PedidoSocket;
import socket.modelo.SocketCliente;

public class ClasesEstaticas {
	private static List<SocketCliente> listaClientes = new CopyOnWriteArrayList<SocketCliente>();

	private static List<PedidoSocket> listaPedidos = new CopyOnWriteArrayList<PedidoSocket>();

	// Getters
	public static List<SocketCliente> getListaClientes() {
		return listaClientes;
	}

	public static List<PedidoSocket> getListaPedidos() {
		return listaPedidos;
	}
}
