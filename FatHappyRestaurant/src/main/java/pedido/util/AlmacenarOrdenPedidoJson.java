package pedido.util;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import auxiliares.inicioAplicacion.ConfiguracionInicial;
import pedido.modelo.Pedido;

/**
 * Clase enfocada en almacenar de manera persistente en formato json la orden de
 * pedido
 */
public class AlmacenarOrdenPedidoJson {

	// Crear el logger
	static Logger logger = LogManager.getLogger(AlmacenarOrdenPedidoJson.class);

	private Pedido pedido;
	private File rutaLocal;

	public AlmacenarOrdenPedidoJson(Pedido pedido) {
		this.pedido = pedido;
		this.rutaLocal = new File(ConfiguracionInicial.get().getDirectorioLocal() + pedido.getRutaPedido());
	}

	/**
	 * Metodo que almacena en un fichero json toda la orden de pedido
	 */
	public void almacenarOrdenPedido() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(rutaLocal, pedido.getOrden());
			logger.info("Se ha almacenado la orden de pedido {}", pedido.getId());
		} catch (IOException e) {
			logger.error("Ha ocurrido un error al almacenar la orden del pedido", e);
		}
	}

}
