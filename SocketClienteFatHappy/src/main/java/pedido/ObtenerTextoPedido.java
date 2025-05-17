package pedido;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import interfazGrafica.util.DatosProductos;
import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Hamburguesa;
import productos.modelo.Postre;
import productos.modelo.Producto;
import socket.modelo.PedidoSocket;

/**
 * Metodo que a traves de todos los {@link Producto}s de un {@link PedidoSocket}
 * se va a generar un {@link StringBuilder} con la lista de los
 * {@link Producto}s
 * 
 * @author Alejandro Perellón López
 */
public class ObtenerTextoPedido {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ObtenerTextoPedido.class);

	public final PedidoSocket pedido;

	public ObtenerTextoPedido(PedidoSocket pedido) {
		this.pedido = pedido;
	}

	/**
	 * Metodo que dependiendo del rol del cliente se va a recopilar unos objetos u
	 * otros
	 * 
	 * @return {@link StringBuilder} con la informacion de los productos
	 */
	protected StringBuilder separarFormatearProductos() {
		List<Producto> listaProductos = new ArrayList<Producto>();

		switch (ClasesEstaticas.getRolcliente()) {
		case 1:
			// Si el rol es de expeditor anadimos todos los producto para mostrar
			listaProductos.addAll(pedido.getListaProductos());
			break;
		case 2:
			// Si el rol es del cocina se van a anadir todos los productos de comida
			for (Producto producto : pedido.getListaProductos()) {
				boolean esHamburguesa = producto.getCategoria().equalsIgnoreCase("Hamburguesa");
				boolean esComplemento = producto.getCategoria().equalsIgnoreCase("Complemento")
						&& !producto.getTipoProducto().equalsIgnoreCase("salsa");

				if (esHamburguesa || esComplemento) {
					listaProductos.add(producto);
				}
			}
			logger.debug("Se han filtrado las hamburguesas y complementos del pedido {}", pedido.getNumeroPedido());
			break;
		case 3:
			// Si el rol es de bebidas y postres se va a anadir todos los productos de
			// comida
			for (Producto producto : pedido.getListaProductos()) {
				boolean esBebida = producto.getCategoria().equalsIgnoreCase("Bebida");
				boolean esPostre = producto.getCategoria().equalsIgnoreCase("Postre");

				if (esBebida || esPostre) {
					listaProductos.add(producto);
				}
			}
			logger.debug("Se han filtrado las bebidas y postres del pedido {}", pedido.getNumeroPedido());
			break;
		}

		// Si la lista de producto contiene mas de un producto se formatea y retorna
		if (listaProductos.size() > 0) {
			return formatearProductos(listaProductos);
		}
		// Si no tiene ningun producto se devuelve null
		return null;

	}

	/**
	 * Metodo que crea un {@link StringBuilder} con el texto de la informacion del
	 * pedido
	 * 
	 * @param listaProductos es la {@link List}a de {@link Producto}s
	 * @return {@link StringBuilder} con toda la informacion de productos
	 */
	private StringBuilder formatearProductos(List<Producto> listaProductos) {

		StringBuilder sb = new StringBuilder();

		DatosProductos datos = new DatosProductos();

		for (Producto producto : listaProductos) {
			if (producto instanceof Hamburguesa) {
				sb.append(datos.informacionHamburguesa((Hamburguesa) producto));
			} else if (producto instanceof Complemento) {
				sb.append(datos.informacionComplemento((Complemento) producto));
			} else if (producto instanceof Bebida) {
				sb.append(datos.informacionBebida((Bebida) producto));
			} else if (producto instanceof Postre) {
				sb.append(datos.informacionPostre((Postre) producto));
			} else {
				sb.append(producto.toString());
			}
		}
		logger.debug("Se han almacenado los datos de los prodcutos");

		return sb;
	}

}
