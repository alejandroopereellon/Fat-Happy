package productos.modelo;

import java.util.List;

/**
 * Metodo que almacena por lista todos los tipos de productos separados por
 * categoria del producto
 * 
 * @author Alejandro Perellón López
 */
public class ListaProductos {
	private List<Producto> listaHamburguesas;
	private List<Producto> listaComplementos;
	private List<Producto> listaBebidas;
	private List<Producto> listaPostres;
	private List<Producto> listaOtros;

	// Constructor vacio
	public ListaProductos() {
	}

	// Getters && setters
	public List<Producto> getListaHamburguesas() {
		return listaHamburguesas;
	}

	public void setListaHamburguesas(List<Producto> listaHamburguesas) {
		this.listaHamburguesas = listaHamburguesas;
	}

	public List<Producto> getListaComplementos() {
		return listaComplementos;
	}

	public void setListaComplementos(List<Producto> listaComplementos) {
		this.listaComplementos = listaComplementos;
	}

	public List<Producto> getListaBebidas() {
		return listaBebidas;
	}

	public void setListaBebidas(List<Producto> listaBebidas) {
		this.listaBebidas = listaBebidas;
	}

	public List<Producto> getListaPostres() {
		return listaPostres;
	}

	public void setListaPostres(List<Producto> listaPostres) {
		this.listaPostres = listaPostres;
	}

	public List<Producto> getListaOtros() {
		return listaOtros;
	}

	public void setListaOtros(List<Producto> listaOtros) {
		this.listaOtros = listaOtros;
	}

}
