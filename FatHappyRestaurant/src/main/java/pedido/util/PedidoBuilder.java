package pedido.util;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import caja.modelo.CajaDatos;
import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;
import restaurante.modelo.RestauranteDatos;

/**
 * Builder para crear pedidos de forma controlada y escalable.
 */
public class PedidoBuilder {

	// Datos obligatorios o configurables
	private int numeroPedido;
	private OrdenPedido orden;
	private int estadoPedido;

	public PedidoBuilder withNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
		return this;
	}

	public PedidoBuilder withOrden(OrdenPedido orden) {
		this.orden = orden;
		return this;
	}

	public PedidoBuilder withEstado(int estadoPedido) {
		this.estadoPedido = estadoPedido;
		return this;
	}

	public Pedido build() {
		Pedido pedido = new Pedido();

		pedido.setNumeroPedido(
				new ObtenerNumeroPedido().obtenerYReservarNumeroPedido(RestauranteDatos.get().getIdRestaurante()));
		pedido.setOrden(orden);
		pedido.setEstadoPedido(estadoPedido);
		pedido.setFechaHora(LocalDateTime.now());

		// Establecemos la ruta donde se almacenan los pedidos
		String ruta = File.separator + "R" + CajaDatos.get().getRestaurante().getIdRestaurante() + File.separator
				+ LocalDate.now().toString() + File.separator + numeroPedido;
		pedido.setRutaPedido(ruta);

		pedido.setDescuento(0);

		// Acciones adicionales tras creación
		new IniciarContadorPedido(pedido).start();
		new CalcularImporte(pedido).obtenerImporteDescuento();
		new AlmacenarOrdenPedidoJson(pedido).almacenarOrdenPedido();

		return pedido;
	}
}
