package socket.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import pedido.CrearPedido;
import socket.modelo.PedidoSocket;
import socket.modelo.Ping;
import socket.modelo.Pong;

public class ProcesarObjetos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ProcesarObjetos.class);

	protected void procesar(Object objeto) {

		// Si el objeto es una peticion de que estamos activo
		if (objeto instanceof Ping) {
			logger.debug("El servidor ha enviado una peticion de vida al cliente");
			int numero = ((Ping) objeto).getNumeroComprobacion();
			ClasesEstaticas.getListaobjetospendientes().add(new Pong(numero));
		}
		// Si el objeto es una respuesta de que estamos activos
		else if (objeto instanceof Pong) {
			logger.debug("El cliente ha enviado una peticion de vida al servidor");
			ClasesEstaticas.getColaPong().offer((Pong) objeto);
		}
		// Si el objeto es un pedido
		else if (objeto instanceof PedidoSocket) {
			logger.debug("Se ha recibido un pedido, se va a procesar");
			new CrearPedido((PedidoSocket) objeto).start();
		} else {
			logger.warn("Objeto desconocido {}", objeto.getClass().getSimpleName());
		}
	}
}
