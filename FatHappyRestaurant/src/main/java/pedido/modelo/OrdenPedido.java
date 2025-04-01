package pedido.modelo;

import java.util.List;

import productos.modelo.MenuPedido;
import productos.modelo.Producto;

/**
 * Clase que contiene toda la informacion del pedido que se va a realizar, entre
 * los datos se almacenan los productos solicitados y las condiciones del
 * pedido, por ejemplo si el pedido es para llevar, el numero de mesa y demas
 */
public class OrdenPedido {

	private List<Producto> listaProductos;

	private List<MenuPedido> listaMenus;

	private boolean llevar;

	private int numeroMesa;

	public OrdenPedido(List<Producto> listaProductos, List<MenuPedido> listaMenus, boolean llevar, int numeroMesa) {
		this.listaProductos = listaProductos;
		this.listaMenus = listaMenus;
		this.llevar = llevar;
		this.numeroMesa = numeroMesa;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public List<MenuPedido> getListaMenus() {
		return listaMenus;
	}

	public boolean isLlevar() {
		return llevar;
	}

	public int getNumeroMesa() {
		return numeroMesa;
	}

}
