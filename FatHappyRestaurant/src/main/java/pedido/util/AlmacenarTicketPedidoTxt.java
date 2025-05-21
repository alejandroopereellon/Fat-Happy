package pedido.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.crearTicket.TicketBuilder;
import auxiliares.metodosBigDecimal.OperacionesBigDecimal;
import auxiliares.singleton.ClasesEstaticas;
import pedido.modelo.Pedido;
import productos.modelo.MenuPedido;
import productos.modelo.Producto;
import restaurante.modelo.Restaurante;

/**
 * Escribe el ticket de un pedido en formato TXT. El fichero se guarda en la
 * misma ruta que {@link AlmacenarOrdenPedidoJson}, cambiando solo la extension
 * (.txt).
 * 
 * @author Alejandro Perellón López
 */
public final class AlmacenarTicketPedidoTxt {

	// Crear el logger
	static Logger logger = LogManager.getLogger(AlmacenarTicketPedidoTxt.class);

	/**
	 * Metodo que almacena en formato TXT los tickets de los pedidos
	 * 
	 * @param pedido es el {@link Pedido} que se va a almacenar
	 */
	public static void guardar(Pedido pedido) {
		try {
			Path destino = construirRuta(pedido);
			Files.createDirectories(destino.getParent());

			String cuerpo = generarTicket(pedido);
			Files.writeString(destino, cuerpo, StandardCharsets.UTF_8);

			logger.info("ticket almacenado en {}", destino);
		} catch (IOException e) {
			logger.error("error al guardar ticket", e);
		}
	}

	/**
	 * Metodo encargado de crear la ruta del pedido
	 */
	private static Path construirRuta(Pedido pedido) {
		String base = System.getProperty("user.home") + File.separator + "fathappyrestaurant";

		String idRestaurante = "R" + ClasesEstaticas.getRestaurante().getIdRestaurante();
		String fecha = pedido.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String idPedido = String.valueOf(pedido.getNumeroPedido());

		String nombreFichero = "TicketPedido" + idPedido + ".txt";

		return Paths.get(base, idRestaurante, fecha, idPedido, nombreFichero);
	}

	/** Usa TicketBuilder y el toString() de tus entidades. */
	private static String generarTicket(Pedido pedido) {

		TicketBuilder tb = new TicketBuilder();

		Restaurante r = ClasesEstaticas.getRestaurante();

		tb.centrar(r.toString()).linea(); // cabecera simple

		tb.izquierda("pedido: " + pedido.getNumeroPedido());
		tb.izquierda("fecha: " + pedido.getFechaHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
		tb.linea();

		// productos sueltos
		for (Producto p : pedido.getOrden().getListaProductos()) {
			tb.izquierda(p.toString());
		}
		// menus
		for (MenuPedido m : pedido.getOrden().getListaMenus()) {
			tb.izquierda(m.toString());
		}

		tb.linea();

		BigDecimal total = new OperacionesBigDecimal().aplicarDescuento(pedido.getImporteTotal(), 10);
		BigDecimal iva = new OperacionesBigDecimal().restar(pedido.getImporteTotal(), total);

		tb.izquierda("total:  " + String.format("%.2f €", total));
		tb.izquierda("iva 10%: " + String.format("%.2f €", iva));
		tb.linea().centrar("gracias por su visita!");

		return tb.construir();
	}
}
