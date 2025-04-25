package pedido.modelo;

import java.util.ArrayList;
import java.util.List;

import productos.modelo.MenuPedido;
import productos.modelo.Producto;

/**
 * Clase que contiene toda la informacion del pedido que se va a realizar, entre
 * los datos se almacenan los productos solicitados y las condiciones del
 * pedido, por ejemplo si el pedido es para llevar, el numero de mesa y demas
 */
public class OrdenPedido {

	private List<Producto> listaProductos = new ArrayList<Producto>();

	private List<MenuPedido> listaMenus = new ArrayList<MenuPedido>();

	private boolean llevar = false;

	private int numeroMesa = 0;

	public OrdenPedido() {
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
