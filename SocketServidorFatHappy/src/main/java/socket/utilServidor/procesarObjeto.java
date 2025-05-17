package socket.utilServidor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import socket.ClasesEstaticas;
import socket.modelo.Confirmacion;
import socket.modelo.PedidoSocket;
import socket.modelo.Ping;
import socket.modelo.Pong;
import socket.modelo.RolSocket;
import socket.modelo.SocketCliente;

/**
 * Clase que contiene los metodos necesarios para procesar los objetos que puede
 * llegar a recibir el servidor
 * 
 * @author Alejandro Perellón López
 */
public class procesarObjeto extends Thread {
	// Crear el logger
	static Logger logger = LogManager.getLogger(procesarObjeto.class);

	public final Object objeto;

	public final SocketCliente socket;

	public procesarObjeto(Object objeto, SocketCliente cliente) {
		this.objeto = objeto;
		this.socket = cliente;
	}

	public void run() {
		// Si el objeto es una peticion de que estamos activo
		if (objeto instanceof Ping) {
			logger.debug("El servidor ha enviado una peticion {} de vida al cliente", ((Ping) objeto));
			int numero = ((Ping) objeto).getNumeroComprobacion();
			socket.getListaObjetosPendientes().add(new Pong(numero));
		}
		// Si el objeto es una respuesta de que estamos activos
		else if (objeto instanceof Pong) {
			logger.debug("El cliente ha enviado una peticion de vida {} al servidor", ((Pong) objeto));
			socket.getColaPong().offer((Pong) objeto);
		}
		// Comprobamos si el objeto es un rolSocket para configurarlo primero
		else if (objeto instanceof RolSocket) {
			logger.debug("El objeto es un RolSocket");
			procesarRolCliente((RolSocket) objeto, socket);
		}
		// Comrpobamos si el objeto es un pedido
		else if (objeto instanceof PedidoSocket) {
			logger.debug("El objeto es un pedido");
			procesarPedido((PedidoSocket) objeto);
		}
		// Comprobamos si el objeto es una comfirmacion
		else if (objeto instanceof Confirmacion) {
			logger.debug("El objeto es una confirmacion del pedido {} y del rol {}",
					((Confirmacion) objeto).getNumeroPedido(), ((Confirmacion) objeto).getRol());
			procesarConfirmacion((Confirmacion) objeto);

		}
		// Si el objeto no es ninguno de los anteriores
		else {
			logger.warn("Objeto desconocido {}", objeto.getClass().getSimpleName());
		}
	}

	/**
	 * Metodo que procesa la confirmacion, comprueba si coinciden las
	 * caracteristicas del pedido y en caso de coindicidan, se va a confirmar la
	 * recepcion.
	 * 
	 * Si ambas recepciones (bebidas y cocina) estan confirmadas se envia a
	 * expeditor
	 * 
	 * Esquema de roles: 0. Caja 1. Expeditor 2. Cocina 3. Bebidas
	 * 
	 * @param confirmacion
	 */
	private void procesarConfirmacion(Confirmacion confirmacion) {
		logger.debug("Se esta procesando la {}", confirmacion);
		// Obtenemos la confirmacion y confirmamos la parte del pedido
		for (PedidoSocket pedido : ClasesEstaticas.getListaPedidos()) {

			// Si el numero de restaurante y el pedido coinciden procesamos a enviar al
			// pedido
			if (pedido.getNumeroRestaurante() == confirmacion.getNumeroRestaurante()
					&& pedido.getNumeroPedido() == confirmacion.getNumeroPedido()) {

				logger.debug("El pedido {}, restaurante {} esta confirmando una parte del pedido",
						pedido.getNumeroPedido(), pedido.getNumeroRestaurante());

				// Comprobamos las condiciones del pedido
				// 1. Expeditor 2. Cocina 3. Bebidas
				switch (confirmacion.getRol()) {
				case 1 /* Expeditor */:
					// Eliminamos el pedido del servidor
					ClasesEstaticas.getListaPedidos().remove(pedido);
					logger.debug("El expeditor ha confirmado el pedido, se va a borrar del servidor");
					break;
				case 2 /* Cocina */:
					pedido.setConfirmadoCocina(true);
					comprobarConfirmaciones(pedido);
					break;
				case 3 /* Bebidas */:
					pedido.setConfirmadoBebidas(true);
					comprobarConfirmaciones(pedido);
					break;
				}
				// Finalizamos el procesamiento
				break;
			}
		}
	}

	/**
	 * Metodo que comprueba si el pedido ha sido confirmado por la bebida y cocina,
	 * en caso correcto se envia al expeditor
	 * 
	 * @param pedido es el pedido que se comprueba si se ha enviado
	 */
	private void comprobarConfirmaciones(PedidoSocket pedido) {
		// Comprobamos si se ha confirmado la bebida y la cocina
		if (pedido.isConfirmadoBebidas() && pedido.isConfirmadoCocina()) {
			logger.debug("El pedido {} del restaurante {} ha sido confirmado por cocina y bebidas",
					pedido.getNumeroPedido(), pedido.getNumeroRestaurante());

			// Obtenemos el expeditor de ese restaurante
			for (SocketCliente cliente : ClasesEstaticas.getListaClientes()) {
				// Si el numero de restaurante coincide con el pedido, y el rol es expeditor
				if (cliente.getNumeroRestaurante() == pedido.getNumeroRestaurante() && cliente.getRolCliente() == 1) {
					cliente.getListaObjetosPendientes().add(pedido);
					logger.debug("Se ha enviado al expeditor el pedido {} al cliente {}", pedido, cliente);
					return;
				}
			}
		} else {
			logger.debug("El pedido aun no ha sido confirmado, bebidas {} Cocina {}", pedido.isConfirmadoBebidas(),
					pedido.isConfirmadoCocina());
		}
	}

	/**
	 * Metodo que se encarga de analizar todos los clientes, y el que cumpla las
	 * mismas caracteristicas que el pedido se le va a enviar los datos
	 * 
	 * 1. Expeditor 2. Cocina 3. Bebidas
	 * 
	 * @param pedido es el {@link PedidoSocket} que se va a procesar
	 */
	private void procesarPedido(PedidoSocket pedido) {
		// Anadimos el pedido en la clase estatica
		ClasesEstaticas.getListaPedidos().add(pedido);
		logger.debug("Se ha añadido el pedido en la clase estatica, actualmente hay {} pedidos",
				ClasesEstaticas.getListaPedidos().size());

		boolean enviadoCocina = false;
		boolean enviadoBebida = false;

		for (SocketCliente cliente : ClasesEstaticas.getListaClientes()) {
			// Buscamos si el rol del cliente es 2 o 3
			boolean rolCliente = cliente.getRolCliente() == 2 || cliente.getRolCliente() == 3;

			// Buscamos si el cliente pertenece al restaurante de la caja
			boolean numeroRestaurante = cliente.getNumeroRestaurante() == pedido.getNumeroRestaurante();

			// Si el rol del cliente es apto para acceder
			if (rolCliente && numeroRestaurante) {
				logger.debug("El cliente {} es apto para enviar el contenido", cliente);
				cliente.getListaObjetosPendientes().add(pedido);
				logger.info("Se ha enviado el pedido al cliente {}", cliente);

				// Comprobamos si se ha enviado el pedido a cada cliente
				if (cliente.getRolCliente() == 2) {
					enviadoCocina = true;
					logger.debug("Se ha marcado el pedido como entregado a la cocina {}", cliente);
				} else if (cliente.getRolCliente() == 3) {
					enviadoBebida = true;
					logger.debug("Se ha marcado el pedido como entregado a las bebidas {}", cliente);
				}

				// Si ya se han enviado los datos a los dos roles finalizamos la ejecucion del
				// bucle for
				if (enviadoBebida && enviadoCocina) {
					logger.debug("Se ha enviado el pedido {} al los clientes de bebidas y cocina", pedido);
					break;
				}
			}
		}

		// Comprobamos si no existe un rol y lo omitimos
		if (!enviadoCocina) {
			logger.debug("Se ha omitido el envio a cocina del pedido {} por ausencia de clientes", pedido);
			pedido.setConfirmadoCocina(true);
		}
		if (!enviadoBebida) {
			logger.debug("Se ha omitido el envio a cocina del pedido {} por ausencia de clientes", pedido);
			pedido.setConfirmadoBebidas(true);
		}

		/**
		 * En caso de que se hayan enviado a los dos roles el pedi
		 * 
		 * En caso de que no se haya enviado a ninguno de los dos roles se va a enviar
		 * al expeditor directamente
		 */
		comprobarConfirmaciones(pedido);
	}

	/**
	 * Metodo que establece los datos del rol del cliente
	 * 
	 * @param rol es el {@link RolSocket} que se va a utilizar para al configuracion
	 */
	private void procesarRolCliente(RolSocket rol, SocketCliente cliente) {
		// Establecemos el numero de restaurante al cliente
		cliente.setNumeroRestaurante(rol.getNumeroRestaurante());
		logger.debug("Se ha establecido el numero de restaurante {} al cliente {}", rol.getNumeroRestaurante(),
				cliente);

		// Establecemos el rol del cliente
		cliente.setRolCliente(rol.getRolCliente());
		logger.debug("Se ha establecido el rol {} al cliente {}", rol.getRolCliente(), cliente);
	}
}
