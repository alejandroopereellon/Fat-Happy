package pedido.util;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import pedido.modelo.OrdenPedido;
import pedido.modelo.Pedido;

/**
 * Builder para crear pedidos de forma controlada y escalable.
 */
public class PedidoBuilder {
	// Crear el logger
	static Logger logger = LogManager.getLogger(PedidoBuilder.class);

	public Pedido build() {
		if (ClasesEstaticas.getCaja() == null) {
			new DialogoMostrarMensajeMetodos().mostrarMensaje("La caja está cerrada");
			logger.warn("La caja no está iniciada");
			return null;
		}

		logger.info("Se va a iniciar un nuevo pedido");
		Pedido pedido = new Pedido();

		// Establecemos el numero de pedido
		pedido.setNumeroPedido(
				new ObtenerNumeroPedido().obtenerYReservarNumeroPedido(ClasesEstaticas.getRestaurante().getIdRestaurante()));
		logger.debug("Se ha establecido el numero de pedido: ", pedido.getNumeroPedido());

		// Establecemos el orden de pedido
		pedido.setOrden(new OrdenPedido());
		logger.debug("Se ha creado el objeto ordenPedido");

		// Establecemos la orden de pedido a "EN PREPARACION"
		pedido.setEstadoPedido(1);
		logger.debug("Se ha establecido el estado del pedido a 'En preparacion'");

		// Establecemos la hora de inicio del pedido
		pedido.setFechaHora(LocalDateTime.now());

		// Establecemos la ruta donde se almacenan los pedidos
		String ruta = File.separator + "R" + ClasesEstaticas.getCaja().getRestaurante().getIdRestaurante() + File.separator
				+ LocalDate.now().toString() + File.separator + pedido.getNumeroPedido();
		pedido.setRutaPedido(ruta);
		logger.debug("Se ha establecido la ruta de almacenamiento de pedidos en :", ruta);

		// Se ha establecido el descuento en le 0%
		pedido.setDescuento(0);
		logger.debug("Se ha establecido el descuento en el 0%");

		// Acciones adicionales tras creación
		new IniciarContadorPedido(pedido).start();
		logger.debug("Se ha iniciado el hilo del pedido");
		new CalcularImporte(pedido).obtenerImporteDescuento();
		logger.debug("Se ha calculado el importe del pedido");
		// new AlmacenarOrdenPedidoJson(pedido).almacenarOrdenPedido();

		return pedido;
	}
}
