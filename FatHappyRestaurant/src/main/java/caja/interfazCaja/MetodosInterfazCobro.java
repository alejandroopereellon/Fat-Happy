package caja.interfazCaja;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.solicitarNumeroDecimal.GestionDecimales;
import caja.util.OperacionBuilder;
import empleados.modelo.Empleado;
import pedido.util.CalcularImporte;

/**
 * Clase que reune todos los metodos que se aplican en la interfaz grafica de
 * {@link MetodosInterfazCobro}
 * 
 * @author Alejandro Perellón Lopez
 */
public class MetodosInterfazCobro {

	// Crear el logger
	static Logger logger = LogManager.getLogger(MetodosInterfazCobro.class);

	private InterfazCobro interfaz;

	protected MetodosInterfazCobro(InterfazCobro interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Metodo que actualiza en la interfaz grafica la cantidad total pagada y la
	 * cantidad total a pagar
	 */
	protected void actualizarPantalla() {
		interfaz.getTextoCantidadPagado().setText(interfaz.getCantidadPropuesta().toString() + " €");
		interfaz.getTextoCantidadTotalPagar().setText(interfaz.getPedido().getImporteTotal().toString() + " €");
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
		String cantidad = interfaz.getTextoCantidadPagado() + String.valueOf(numero);
		// Calculamos el nuevo importe
		cantidad = new GestionDecimales().procesarDecimales(cantidad);
		// Establecemos la nueva cantidad actualizada
		interfaz.getTextoCantidadPagado().setText(cantidad);
		// Actualizamos la pantalla
		actualizarPantalla();
		logger.debug("Se ha introducido el numero {}, dando un total de {}", numero, interfaz.getTextoCantidadPagado());
	}

	/**
	 * Metodo que se encarga de borrar la cantidad entregada por el cliente, ya sea
	 * por error del {@link Empleado} o del cliente
	 */
	protected void borrarCantidad() {
		// Establecemos el importe en 0
		interfaz.getTextoCantidadPagado().setText("0.00");
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
		interfaz.getTextoCantidadPagado().setText(String.valueOf(billete) + .00);
		// Comprobamos si se puede cobrar la operacion automaticamente si la cantidad
		// pendiente es menor o 0 que la total
		cobrarOperacion();
		logger.info("Se ha introducido un billete de {} euros", billete);
	}

	/**
	 * Metodo que al pulsar el boton de cobrar o introducir un billete se calcula el
	 * importe, en caso de que la cantidad introdu
	 */
	protected void cobrarOperacion() {
		// Restamos al total pediente de pago lo que esta pagado por el cliente
		interfaz.getTextoCantidadTotalPagar()
				.setText(restarCantidades(obtenerCantidadTotal(), obtenerCantidadPagadoCliente()).toString());
		// Si el pendiente es negativo o 0 se va a generar la operacion de pago
		if (obtenerCantidadTotal().compareTo(BigDecimal.ZERO) <= 0) {
			// Generamos una nueva operacion
			if (new OperacionBuilder().GenerarOperacion(interfaz.getPedido(), "cobro", "efectivo")) {
				// Desactivamos los botones y mostramos el boton de continuar
				cambiarEstadoElementos(false);
				interfaz.getBotonContinuar().setVisible(true);
				logger.info("Se creado la operacion de pedido correctamente");
			} else {
				logger.warn("No se ha podido almacenar la operacion del pedido ID {} correctamente",
						interfaz.getPedido().getId());
			}
		} else {
			logger.info("El importe pagado es inferior al importe total, no se ha realizado ninguna operacion");
		}
		// Reiniciamos el importe total a 0
		borrarCantidad();
	}

	protected void establecerDescuento() {
		// Solicitamos el descuento
		DescuentoInterfaz descuento = new DescuentoInterfaz();
		int cantidadDescuento = 0;

		descuento.setVisible(true);
		while (descuento.isVisible()) {
			// Si se ha aplicado un descuento (diferente de 1) ocultamos el panel
			if (descuento.getCantidadDescuento() != 1) {
				// Ocultamos el descuento
				descuento.setVisible(false);
				// Almacenamos la cantidad de descuento
				cantidadDescuento = descuento.getCantidadDescuento();
			}
		}
		// Hacemos dispose del descuento
		descuento.dispose();

		// Establecemos al pedido el nuevo descuento
		interfaz.getPedido().setDescuento(cantidadDescuento);
		// Actualizamos el importe del pedido
		new CalcularImporte(interfaz.getPedido()).obtenerImporteDescuento();

		logger.info("Se ha aplicado un descuento del {} al pedido", interfaz.getPedido().getDescuento());
	}

	/**
	 * Metodo que convierte el {@link String} del texto pendiente de pago pagar en
	 * un bigdecimal
	 * 
	 * @return {@link BigDecimal} del total a pagar
	 */
	private BigDecimal obtenerCantidadPagadoCliente() {
		logger.debug("Se ha consultado la cantidad de dinero pagada por el cliente");
		return new BigDecimal(interfaz.getTextoCantidadTotalPagar().getText());
	}

	/**
	 * Metodo que convierte el {@link String} del texto total pagar en un bigdecimal
	 * 
	 * @return {@link BigDecimal} del total a pagar
	 */
	private BigDecimal obtenerCantidadTotal() {
		logger.debug("Se ha consultado la cantidad de dinero pendiente de pagar");
		return new BigDecimal(interfaz.getTextoCantidadTotalPagar().getText());
	}

	/**
	 * Metodo que resta a la cantidad 1 la cantidad 2
	 * 
	 * @param cantidad1 es la cantidad a la que se va a restar
	 * @param cantidad2 cantidad que se resta
	 * @return {@link BigDecimal} con el resultado de la operacion
	 */
	private BigDecimal restarCantidades(BigDecimal cantidad1, BigDecimal cantidad2) {
		logger.debug("Se ha restado la cantidad de {} a {}", cantidad2, cantidad1);
		return cantidad1.subtract(cantidad2);
	}

	/**
	 * Metodo que establece el importe pagado con la misma cantidad de importe a
	 * pagar dando un resultado restante de 0 euros
	 */
	protected void importeExacto() {
		// Establecemos el importe pagado como el importe a pagar
		interfaz.getTextoCantidadPagado().setText(interfaz.getTextoCantidadTotalPagar().getText());
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

	void continuar() {
		// TODO
	}

}
