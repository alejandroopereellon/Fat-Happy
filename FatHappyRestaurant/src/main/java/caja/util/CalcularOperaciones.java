package caja.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.metodosBigDecimal.OperacionesBigDecimal;
import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Caja;
import caja.modelo.Operacion;

/**
 * Clase que calcula todas las operaciones realizadas por la {@link Caja}
 * 
 * @author Alejandro Perellón López
 */
public class CalcularOperaciones {

	// Creamos el logger
	static Logger logger = LogManager.getLogger(CalcularOperaciones.class);

	// Establecemos el dao
	private CajasDao dao = new CajasDaoHibernateImpl();

	/**
	 * Metodo que lista todas las operaciones realizadas en la {@link Caja} y
	 * calcula la suma y resta de todas las operaciones realizadas
	 * 
	 * @return {@link BigDecimal} con el importe total
	 */
	public BigDecimal calcularTotalOperaciones() {
		BigDecimal totalOperaciones = BigDecimal.ZERO;

		List<Operacion> listaOperacionesRealizadas = dao.listarOperaciones();
		logger.info("Se ha obtenido una lista de operaciones con {} operaciones", listaOperacionesRealizadas.size());

		for (Operacion operacion : listaOperacionesRealizadas) {
			// Si el tipo de cobro es cobro, se aumenta la cantidad
			if (operacion.getTipoOperacion().toLowerCase().equalsIgnoreCase("cobro")) {
				logger.debug("Se va a sumar el importe de {} al total de {} por cobro", operacion.getImporte(),
						totalOperaciones);
				totalOperaciones = new OperacionesBigDecimal().sumar(totalOperaciones, operacion.getImporte());
			} else {
				logger.debug("Se va a restar el importe de {} al total de {} por devolucion", operacion.getImporte(),
						totalOperaciones);
				totalOperaciones = new OperacionesBigDecimal().restar(totalOperaciones, operacion.getImporte());
			}
		}

		// Retornamos el total de operaciones
		logger.info("Se ha retornado un total de operaciones de {} Eur", totalOperaciones);
		return totalOperaciones.setScale(2, RoundingMode.HALF_UP);
	}

}
