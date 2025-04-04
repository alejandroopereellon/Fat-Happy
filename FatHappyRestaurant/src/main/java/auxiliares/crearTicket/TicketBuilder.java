package auxiliares.crearTicket;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que permite construir un ticket de texto con formato fijo y almacenarlo
 * en un archivo. Pensado para cierres de caja, pedidos, etc.
 * 
 * @author Alejandro Perellón López
 */
public class TicketBuilder {

	private static final Logger logger = LogManager.getLogger(TicketBuilder.class);
	private static final int ANCHO_LINEA = 40;

	private StringBuilder ticket;

	/**
	 * Constructor que inicia un ticket vacio.
	 */
	public TicketBuilder() {
		ticket = new StringBuilder();
	}

	/**
	 * Añade una linea divisoria al ticket.
	 * 
	 * @return la instancia actual de TicketBuilder
	 */
	public TicketBuilder linea() {
		ticket.append("-".repeat(ANCHO_LINEA)).append("\n");
		return this;
	}

	/**
	 * Añade una linea centrada al ticket.
	 * 
	 * @param texto el texto a centrar
	 * @return la instancia actual de TicketBuilder
	 */
	public TicketBuilder centrar(String texto) {
		int espacios = (ANCHO_LINEA - texto.length()) / 2;
		ticket.append(" ".repeat(Math.max(0, espacios))).append(texto).append("\n");
		return this;
	}

	/**
	 * Añade una linea alineada a la izquierda.
	 * 
	 * @param texto el texto a añadir
	 * @return la instancia actual de TicketBuilder
	 */
	public TicketBuilder izquierda(String texto) {
		ticket.append(texto).append("\n");
		return this;
	}

	/**
	 * Añade una linea con dos columnas, una alineada a la izquierda y otra alineada
	 * a la derecha.
	 * 
	 * @param izq texto izquierdo
	 * @param der texto derecho
	 * @return la instancia actual de TicketBuilder
	 */
	public TicketBuilder izquierdaYderecha(String izq, String der) {
		int espacios = ANCHO_LINEA - izq.length() - der.length();
		ticket.append(izq).append(" ".repeat(Math.max(0, espacios))).append(der).append("\n");
		return this;
	}

	/**
	 * Añade una linea en blanco.
	 * 
	 * @return la instancia actual de TicketBuilder
	 */
	public TicketBuilder nuevaLinea() {
		ticket.append("\n");
		return this;
	}

	/**
	 * Devuelve el contenido completo del ticket.
	 * 
	 * @return el texto del ticket como cadena
	 */
	public String construir() {
		return ticket.toString();
	}

	/**
	 * Almacena el contenido del ticket en un archivo.
	 * 
	 * @param archivo archivo de destino
	 */
	public void almacenarEnArchivo(File archivo) {
		try {
			// Crear las carpetas si no existen
			archivo.getParentFile().mkdirs();

			Files.writeString(archivo.toPath(), ticket.toString(), StandardCharsets.UTF_8);
			logger.info("Ticket almacenado en la ruta: {}", archivo.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Error al almacenar el ticket en archivo", e);
		}
	}
}
