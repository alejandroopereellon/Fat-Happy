package auxiliares.metodosBigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pedido.util.ComprobarPedidoFinalizado;

/**
 * clase utilitaria para operaciones basicas con bigdecimal
 */
public class OperacionesBigDecimal {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ComprobarPedidoFinalizado.class);

	/**
	 * metodo que suma dos cantidades
	 * 
	 * @param cantidadUno cantidad 1 a la que se le va a añadir la cantidad 2
	 * @param cantidadDos cantidad 2 que se va a añadir a la cantidad 1
	 * @return {@link BigDecimal} con el resultado de la suma
	 */
	public BigDecimal sumar(BigDecimal cantidadUno, BigDecimal cantidadDos) {
		BigDecimal resultado = cantidadUno.add(cantidadDos);
		logger.debug("se ha sumado {} a {}, resultado: {}", cantidadDos, cantidadUno, resultado);
		return resultado;
	}

	/**
	 * metodo que resta una cantidad de otra
	 * 
	 * @param cantidadUno cantidad de la que se va a restar
	 * @param cantidadDos cantidad que se va a restar
	 * @return {@link BigDecimal} con el resultado de la resta
	 */
	public BigDecimal restar(BigDecimal cantidadUno, BigDecimal cantidadDos) {
		BigDecimal resultado = cantidadUno.subtract(cantidadDos);
		logger.debug("se ha restado {} de {}, resultado: {}", cantidadDos, cantidadUno, resultado);
		return resultado;
	}

	/**
	 * metodo que multiplica dos cantidades
	 * 
	 * @param cantidadUno primer valor a multiplicar
	 * @param cantidadDos segundo valor a multiplicar
	 * @return {@link BigDecimal} con el resultado de la multiplicacion
	 */
	public BigDecimal multiplicar(BigDecimal cantidadUno, BigDecimal cantidadDos) {
		BigDecimal resultado = cantidadUno.multiply(cantidadDos);
		logger.debug("se ha multiplicado {} por {}, resultado: {}", cantidadUno, cantidadDos, resultado);
		return resultado;
	}

	/**
	 * metodo que calcula un descuento sobre una cantidad
	 * 
	 * @param cantidad            cantidad sobre la que se aplica el descuento
	 * @param porcentajeDescuento porcentaje de descuento (por ejemplo, 10 para un
	 *                            10 por ciento)
	 * @return {@link BigDecimal} con el importe descontado, con 2 decimales
	 *         redondeados hacia arriba
	 */
	public BigDecimal aplicarDescuento(BigDecimal cantidad, int porcentajeDescuento) {
		BigDecimal porcentaje = BigDecimal.valueOf(porcentajeDescuento).divide(BigDecimal.valueOf(100));
		BigDecimal descuento = cantidad.multiply(porcentaje);
		BigDecimal resultado = cantidad.subtract(descuento);
		logger.debug("se ha aplicado un descuento de {}% sobre {}, resultado: {}", porcentajeDescuento, cantidad,
				resultado);
		return resultado.setScale(2, RoundingMode.UP);
	}

}
