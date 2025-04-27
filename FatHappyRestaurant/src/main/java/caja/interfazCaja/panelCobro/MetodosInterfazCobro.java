package caja.interfazCaja.panelCobro;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.metodosBigDecimal.OperacionesBigDecimal;
import auxiliares.singleton.ClasesEstaticas;
import auxiliares.solicitarNumeroDecimal.GestionDecimales;
import auxiliares.utilidadesGraficas.PanelUtil;
import caja.interfazCaja.descuentos.SolicitarDescuento;
import caja.util.HiloFinalizarOperacion;
import empleados.modelo.Empleado;
import empleados.util.ActividadEmpleados;
import pedido.interfazPedido.configuracionPromocion.MetodoPromocionMetodos;
import pedido.modelo.Pedido;
import pedido.util.CalcularImporte;
import ventanaPrincipal.InterfazVentanaPrincipalMetodos;

/**
 * Clase que reune todos los metodos que se aplican en la interfaz grafica de
 * {@link MetodosInterfazCobro}
 * 
 * @author Alejandro Perellón Lopez
 */
public class MetodosInterfazCobro {

	// Crear el logger
	static Logger logger = LogManager.getLogger(MetodosInterfazCobro.class);

	// Establecemos la interfaz de cobro
	private InterfazCobro interfaz;

	// Establecemos el pedido
	private Pedido pedido = ClasesEstaticas.getPedido();

	public MetodosInterfazCobro(InterfazCobro interfaz) {
		this.interfaz = interfaz;
	}

	public void iniciarInterfazCobro() {
		// Anadimos la interfaz
		new PanelUtil().insertarEnPanel(ConfiguracionInicial.get().getVentanaPrincipal().getPanelSecundario(),
				interfaz);
		pedido.setEstadoPedido(4);
	}

	/**
	 * Metodo que actualiza en la interfaz grafica la cantidad total pagada y la
	 * cantidad total a pagar
	 */
	protected void actualizarPantalla() {
		interfaz.getTextoCantidadPagado()
				.setText(interfaz.getCantidadPropuesta().setScale(2, RoundingMode.HALF_UP) + " €");
		interfaz.getTextoCantidadTotalPagar()
				.setText(pedido.getImporteTotal().setScale(2, RoundingMode.HALF_UP) + " €");
		logger.debug("Se ha actualizado la pantalla");
	}

	/**
	 * Metodo que actualiza el importe entregado por el cliente, cuando introduces
	 * un numero se añade a la cadena de caracteres, se formatea haciendo uso de
	 * {@link GestionDecimales}, se retorna la cantidad y se actualiza la pantalla
	 * 
	 * @param numero es el numero que se va a añadir a la cantidad
	 */
	protected void introducirNumero(int numero) {
		// Añadimos el numero a la cantidad pagada
		String cantidad = interfaz.getCantidadPropuesta() + String.valueOf(numero);
		logger.debug("El total del texto es {}", cantidad);
		// Calculamos el nuevo importe
		cantidad = new GestionDecimales().procesarDecimales(cantidad);
		// Modificamos la cantidad propuesta
		interfaz.setCantidadPropuesta(new BigDecimal(cantidad));
		// Actualizamos la pantalla
		actualizarPantalla();
		logger.debug("Se ha introducido el numero {}, dando un total de {}", numero,
				interfaz.getTextoCantidadPagado().getText());
	}

	/**
	 * Metodo que se encarga de borrar la cantidad entregada por el cliente, ya sea
	 * por error del {@link Empleado} o del cliente
	 */
	protected void borrarCantidad() {
		// Establecemos el importe en 0
		interfaz.setCantidadPropuesta(new BigDecimal("0.00"));
		// Actualizamos la pantalla
		actualizarPantalla();
		logger.info("Se ha borrado la cantidad");
	}

	/**
	 * Metodo que realiza las gestiones necesarias al introducir un billete, si el
	 * billete introducido es mayor que la cantidad a pagar se realiza el cobro
	 * automaticamente, en caso contrario se resta la cantidad al importe
	 * 
	 * @param billete es el billete que se ha introducido y la cantidad se va a
	 *                restar al importe
	 */
	protected void billetes(int billete) {
		// Cambiamos la cantidad introcudida
		interfaz.setCantidadPropuesta(new BigDecimal(billete + ".00"));
		// Comprobamos si se puede cobrar la operacion automaticamente si la cantidad
		// pendiente es menor o 0 que la total
		cobrarOperacion();
		logger.info("Se ha introducido un billete de {} euros", billete);
	}

	/**
	 * Metodo que al pulsar el boton de cobrar o introducir un billete se calcula el
	 * importe, en caso de que la cantidad introducida sea mayor que la cantidad de
	 * cobro.
	 * 
	 * En caso de que la cantidad de cobro sea menor se va a restar las cantidades
	 */
	public void cobrarOperacion() {

		// Comprobamos que hay permisos de cobros de mas de 100 euros
		if (interfaz.getCantidadPropuesta().compareTo(new BigDecimal("100.00")) >= 0
				&& !new ActividadEmpleados().solicitarPermisos("Cobro de mas de 100 euros", 2)) {
			return;
		}

		// Calculamos el resto de la cantidad pagada por el cliente
		BigDecimal cantidadRestante = new OperacionesBigDecimal().restar(pedido.getImporteTotal(),
				interfaz.getCantidadPropuesta());
		logger.debug("La cantidad restante es {}", cantidadRestante);

		// Si el pendiente es negativo o 0 se va a generar la operacion de pago
		if (cantidadRestante.compareTo(BigDecimal.ZERO) <= 0) {
			// Realizamos las operaciones de cierre de pedido
			new HiloFinalizarOperacion(ClasesEstaticas.getPedido()).start();

			// Ponemos el pedido actual y el panel de pedido en nulo
			ClasesEstaticas.setPanelPedido(null);
			ClasesEstaticas.setPedido(null);

			// Desactivamos los botones y mostramos el boton de continuar
			cambiarEstadoElementos(false);
			interfaz.getBotonContinuar().setVisible(true);

			// Modificamos el texto pagado para mostrar la devolucion
			interfaz.getTextoCantidadPagado().setText(cantidadRestante.setScale(2, RoundingMode.HALF_UP) + " €");
			interfaz.getTextoPagado().setText("DEVOLUCION");
			interfaz.getTextoCantidadPagado().setForeground(Color.decode("#FF0000"));

		} else {
			logger.info("El importe pagado es inferior al importe total, no se ha realizado ninguna operacion");
			// Establecemos el nuevo importe
			pedido.setImporteTotal(cantidadRestante);
			// Establecemos el importe de pago a 0
			interfaz.setCantidadPropuesta(new BigDecimal("0.00"));
			actualizarPantalla();
		}

	}

	protected void establecerDescuento() {
		// Solicitamos el descuento
		SolicitarDescuento descuento = new SolicitarDescuento();
		descuento.setVisible(true);
		int cantidadDescuento = descuento.getCantidadDescuento();

		// Establecemos al pedido el nuevo descuento
		pedido.setDescuento(cantidadDescuento);

		// Actualizamos el importe del pedido
		new CalcularImporte(pedido).obtenerImporteDescuento();

		// Actualizamos la pantalla
		actualizarPantalla();

		logger.info("Se ha aplicado un descuento del {} al pedido", pedido.getDescuento());
	}

	/**
	 * Metodo que establece el importe pagado con la misma cantidad de importe a
	 * pagar dando un resultado restante de 0 euros
	 */
	protected void importeExacto() {
		// Establecemos el importe pagado como el importe a pagar
		interfaz.setCantidadPropuesta(pedido.getImporteTotal());

		actualizarPantalla();
		logger.info("Se ha establecido un pago del importe total del pedido");
		// Cobramos la operacion
		cobrarOperacion();
	}

	/**
	 * Metodo que activa o desactiva todos los botones de la interfaz de cobro. Se
	 * puede usar para bloquear la pantalla cuando se realiza una operacion o se
	 * finaliza el cobro.
	 *
	 * @param estado true para habilitar los botones, false para deshabilitarlos
	 */
	private void cambiarEstadoElementos(boolean estado) {
		interfaz.getBoton0().setEnabled(estado);
		interfaz.getBoton1().setEnabled(estado);
		interfaz.getBoton2().setEnabled(estado);
		interfaz.getBoton3().setEnabled(estado);
		interfaz.getBoton4().setEnabled(estado);
		interfaz.getBoton5().setEnabled(estado);
		interfaz.getBoton6().setEnabled(estado);
		interfaz.getBoton7().setEnabled(estado);
		interfaz.getBoton8().setEnabled(estado);
		interfaz.getBoton9().setEnabled(estado);

		interfaz.getBotonBillete5().setEnabled(estado);
		interfaz.getBotonBillete10().setEnabled(estado);
		interfaz.getBotonBillete20().setEnabled(estado);
		interfaz.getBotonBillete50().setEnabled(estado);

		interfaz.getBotonBorrar().setEnabled(estado);
		interfaz.getBotonCobrar().setEnabled(estado);
		interfaz.getBotonDesc().setEnabled(estado);
		interfaz.getBotonPromo().setEnabled(estado);
		interfaz.getImporteExacto().setEnabled(estado);

		logger.info("Se ha establecido el estado de los botones de la interfaz a {}", estado);
	}

	protected void continuar() {
		// Eliminamos el pedido
		ClasesEstaticas.setPedido(null);
		// Establecemos un nuevo panel de pedido
		new InterfazVentanaPrincipalMetodos(ConfiguracionInicial.get().getVentanaPrincipal())
				.configurarPanelPrincipal();
	}

	protected void promocionarArticulo() {
		// Buscamos el elemento seleccionado
		new MetodoPromocionMetodos(pedido, interfaz).solicitarMetodoPromocion();
		/**
		 * Una vez cerrado el metodo de solicitarMetodoPromocion comprobamos si el
		 * pedido esta promocionado, en este caso se calcula el importe y se paga con
		 * dinero exacto
		 */
		if (pedido.isPedidoPromocionado()) {
			new CalcularImporte(pedido).obtenerImporteDescuento();
			importeExacto();
		}

		/**
		 * Tambien comprobamos si solo hay un unico articulo en la orden de pedido, entonces se cobrará automaticamente
		 */
		if (pedido.getOrden().getListaMenus().size() + pedido.getOrden().getListaProductos().size() == 1) {
			new CalcularImporte(pedido).obtenerImporteDescuento();
			importeExacto();
		}
	}

}
