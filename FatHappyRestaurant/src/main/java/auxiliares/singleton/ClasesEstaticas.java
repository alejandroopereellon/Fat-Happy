package auxiliares.singleton;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos.CifradoDatos;
import caja.interfazCaja.panelPrincipalCaja.PanelCaja;
import caja.modelo.Caja;
import empleados.modelo.Empleado;
import pedido.interfazPedido.PanelPedido;
import pedido.modelo.Pedido;
import pedido.util.PedidoBuilder;
import productos.modelo.ListaProductos;
import productos.modelo.Producto;
import restaurante.modelo.Restaurante;
import socket.modelo.Pong;
import socket.modelo.SocketCliente;
import socket.util.ConectarAlServidor;

public class ClasesEstaticas {

	// Dato estatico que mantiene en memoria la variable de cifrado
	private static final String NOMBRE_VARIABLE_ENTORNO = "java_password";

	// Dato estatico que mantiene en memoria el restaurante
	private static Restaurante restauranteActual;

	// Dato estatico que mantiene en memoria el listado de productos
	private static ListaProductos listaProductos;

	// Dato estatico que mantiene en memoria el pedido actual
	private static Pedido pedidoActual;

	// Dato estatico que mantiene en memoria el panel de pedido
	private static PanelPedido panelPedido;

	// Dato estatico que mantiene en memoria el empleado
	private static Empleado empleadoActual;

	// Dato estatico que mantiene en memoria la caja
	private static Caja cajaActual;

	// Dato estatico que mantiene en memoria el panel de la caja
	private static PanelCaja panelCaja;

	// Dato estatico que mantiene en memoria el socket del sevidor
	private static SocketCliente socket;

	// Dato estatico que mantiene en memoria los pongs recibidos
	public static final BlockingQueue<Pong> colaPong = new LinkedBlockingQueue<>();

	// Dato estatico que mantiene en memoria el check de actualizar constantemente
	// el servidor
	public static boolean reconexionAutomatica = true;

	// Dato estatico que mantiene en memoria el hilo que mantiene la conexion al
	// servidor
	public static ConectarAlServidor hiloConexionServidor = new ConectarAlServidor();

	// Dato estatico que mantiene en memoria el sistema de cifrado de datos
	public static CifradoDatos cifrado = new CifradoDatos();

	// Dato estatico que mantiene en memoria la direccion ip del servidor
	public static String direccionIPservidor = "79.116.12.169";

	/**
	 * Guarda el pedido actual en memoria.
	 * 
	 * @param pedido el objeto Pedido
	 */
	public static void setPedido(Pedido pedido) {
		pedidoActual = pedido;
	}

	/**
	 * Obtiene el pedido actual.
	 * 
	 * @return el objeto Pedido guardado, o null si no hay ninguno.
	 */
	public static Pedido getPedido() {
		return pedidoActual;
	}

	/**
	 * Establece manualmente el panel de pedido. Útil si creas el panel en un flujo
	 * personalizado.
	 * 
	 * @param panel PanelPedido activo
	 */
	public static void setPanelPedido(PanelPedido panel) {
		panelPedido = panel;
	}

	/**
	 * Devuelve el panel del pedido actual. Si no se ha establecido manualmente, lo
	 * intenta obtener desde ConfiguracionInicial automáticamente.
	 * 
	 * @return PanelPedido actual o null si no está disponible
	 */
	public static PanelPedido getPanelPedido() {
		if (panelPedido == null) {
			try {
				Object panelSecundario = ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario();

				if (panelSecundario instanceof PanelPedido) {
					panelPedido = (PanelPedido) panelSecundario;
				}
			} catch (NullPointerException e) {
				// Puede que la ventana principal aún no esté disponible
				panelPedido = null;
			}
		}
		return panelPedido;
	}

	/**
	 * Reinicia los datos en memoria del pedido actual.
	 */
	public static void reset() {
		pedidoActual = null;
		panelPedido = null;
	}

	/**
	 * Comprueba si hay un pedido en curso.
	 * 
	 * @return true si hay pedido y panel asignado
	 */
	public static boolean hayPedidoEnCurso() {
		return pedidoActual != null && getPanelPedido() != null;
	}

	public static boolean iniciarPedido() {
		// Iniciamos el pedido y lo añadimos al pedidoActual
		pedidoActual = new PedidoBuilder().build();
		// Establecemos la ventana
		panelPedido = getPanelPedido();

		if (pedidoActual != null && panelPedido != null) {
			return true;
		}
		return false;
	}

	public ClasesEstaticas() {
	}

	/**
	 * Setter para almacenar en memoria el restaurante
	 * 
	 * @param restaurante el objeto {@link Restaurante} que se va a cargar en
	 *                    memoria
	 */
	public static void setRestaurante(Restaurante restaurante) {
		restauranteActual = restaurante;
	}

	/**
	 * Getter para obtener de memoria el restaurante
	 * 
	 * @return objeto {@link Restaurante} al que pertenece la caja
	 */
	public static Restaurante getRestaurante() {
		return restauranteActual;
	}

	/**
	 * Setter para almacenar en memoria la lista
	 * 
	 * @param listProductos el objeto {@link List} de {@link Producto} que se va a
	 *                      cargar en memoria
	 */
	public static void setListaProductos(ListaProductos listaProd) {
		listaProductos = listaProd;
	}

	/**
	 * Getter para obtener de memoria el objeto de {@link ListaProductos}
	 * 
	 * @return {@link ListaProductos}
	 */
	public static ListaProductos getListaProductos() {
		return listaProductos;

	}

	/**
	 * Setter para almacenar en memoria el empleado
	 * 
	 * @param empleado el objeto {@link empleado} que se va a cargar en memoria
	 */
	public static void setEmpleado(Empleado empleado) {
		empleadoActual = empleado;
	}

	/**
	 * Getter para obtener de memoria el empleado
	 * 
	 * @return objeto {@link empleado} al que pertenece la caja
	 */
	public static Empleado getEmpleado() {
		return empleadoActual;
	}

	/**
	 * Setter para almacenar en memoria la caja
	 * 
	 * @param caja objeto {@link caja} que se va a cargar en memoria
	 */
	public static void setCaja(Caja caja) {
		cajaActual = caja;
	}

	/**
	 * Getter para obtener de memoria el caja
	 * 
	 * @return objeto {@link caja} al que pertenece la caja
	 */
	public static Caja getCaja() {
		return cajaActual;
	}

	public static PanelCaja getPanelCaja() {
		return panelCaja;
	}

	public static void setPanelCaja(PanelCaja panel) {
		panelCaja = panel;
	}

	public static SocketCliente getSocket() {
		return socket;
	}

	public static void setSocket(SocketCliente socket) {
		ClasesEstaticas.socket = socket;
	}

	public static BlockingQueue<Pong> getColapong() {
		return colaPong;
	}

	public static boolean isReconexionautomatica() {
		return reconexionAutomatica;
	}

	public static void setReconexionAutomatica(boolean reconexionAutomatica) {
		ClasesEstaticas.reconexionAutomatica = reconexionAutomatica;
	}

	public static ConectarAlServidor getHiloconexionservidor() {
		return hiloConexionServidor;
	}

	public static void setHiloConexionServidor(ConectarAlServidor hiloConexionServidor) {
		ClasesEstaticas.hiloConexionServidor = hiloConexionServidor;
	}

	public static String getNombreVariableEntorno() {
		return NOMBRE_VARIABLE_ENTORNO;
	}

	public static CifradoDatos getCifrado() {
		return cifrado;
	}

	public static void setCifrado(CifradoDatos cifrado) {
		ClasesEstaticas.cifrado = cifrado;
	}

	public static String getDireccionIPservidor() {
		return direccionIPservidor;
	}

}
