package auxiliares.singleton;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import interfazGrafica.ventanaPrincipal.VentanaPrincipal;
import socket.modelo.PedidoSocket;
import socket.modelo.Pong;
import socket.modelo.SocketCliente;

public class ClasesEstaticas {
	public static VentanaPrincipal ventana;

	public static int numeroRestaurante;

	public static int rolCliente;

	public static SocketCliente socket;

	public static final BlockingQueue<Pong> colaPong = new LinkedBlockingQueue<Pong>();

	public static CopyOnWriteArrayList<PedidoSocket> listaPedidos = new CopyOnWriteArrayList<PedidoSocket>();

	public static final List<Object> listaObjetosPendientes = new CopyOnWriteArrayList<Object>();

	public static BlockingQueue<Pong> getColaPong() {
		return colaPong;
	}

	public static SocketCliente getSocket() {
		return socket;
	}

	public static void setSocket(SocketCliente socket) {
		ClasesEstaticas.socket = socket;
	}

	public static VentanaPrincipal getVentana() {
		return ventana;
	}

	public static int getRolcliente() {
		return rolCliente;
	}

	public static int getNumerorestaurante() {
		return numeroRestaurante;
	}

	public static void setNumeroRestaurante(int numeroRestaurante) {
		ClasesEstaticas.numeroRestaurante = numeroRestaurante;
	}

	public static void setRolCliente(int rolCliente) {
		ClasesEstaticas.rolCliente = rolCliente;
	}

	public static void setVentana(VentanaPrincipal ventana) {
		ClasesEstaticas.ventana = ventana;
	}

	public static CopyOnWriteArrayList<PedidoSocket> getListapedidos() {
		return listaPedidos;
	}

	public static List<Object> getListaobjetospendientes() {
		return listaObjetosPendientes;
	}

	public static void setListaPedidos(CopyOnWriteArrayList<PedidoSocket> listaPedidos) {
		ClasesEstaticas.listaPedidos = listaPedidos;
	}
}
