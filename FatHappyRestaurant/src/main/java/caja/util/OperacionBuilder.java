package caja.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Operacion;
import pedido.modelo.Pedido;
import pedido.util.CalcularImporte;

public class OperacionBuilder {
	static Logger logger = LogManager.getLogger(OperacionBuilder.class);

	// Establecemos el metodo dao de las cajas
	private CajasDao dao = new CajasDaoHibernateImpl();
	// Establecemos el objeto operacion de la clase
	private Operacion operacion = new Operacion();

	/**
	 * Metodo que genera una operacion con los datos introcudido y lo persiste a
	 * traves del modelo DAO
	 * 
	 * @param pedido        es el pedido que se va a persistir
	 * @param tipoOperacion es el tipo de operacion (cobro o devolucion)
	 * @param metodoCobro   es el metodo de cobro (tarjeta, efectivo, otros...)
	 * @return TRUE si se ha podido persistir la operacion || FALSE si no se ha
	 *         podido persistir
	 */
	public boolean GenerarOperacion(Pedido pedido, String tipoOperacion, String metodoCobro) {

		// Establecemos la caja para la operacion
		operacion.setCaja(ClasesEstaticas.getCaja());

		// Establecemos el pedido
		operacion.setPedido(pedido);

		// Establecemos el tipo de operacion
		operacion.setTipoOperacion(tipoOperacion);

		// Establecemos el importe total
		operacion.setImporte(new CalcularImporte(pedido).obtenerImporteDescuento());

		// Establecemos el metodo de cobro
		operacion.setMetodoCobro(metodoCobro);

		// Añadimos la operacion al DAO
		if (dao.insertarOperacion(operacion)) {
			logger.info("Se ha añadido la operacion en el DAO");
			return true;
		}
		logger.warn("No se ha podido añadir la operacion en la base de datos");
		return false;
	}

	public Operacion getOperacion() {
		return operacion;
	}
}
