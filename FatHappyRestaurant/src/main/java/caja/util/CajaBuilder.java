package caja.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Caja;
import caja.modelo.CajaDatos;
import empleados.modelo.Empleado;
import restaurante.modelo.RestauranteDatos;

/**
 * Clase que crea un nuevo objeto caja
 */
public class CajaBuilder {

	// Crear el logger
	static Logger logger = LogManager.getLogger(CajaBuilder.class);

	/**
	 * Metodo que crea el nuevo objeto caja y lo establece en la clase singleton de
	 * {@link CajaDatos} para usarla en el sistema
	 * 
	 * @return TRUE si la caja se ha creado correctamente || FALSE si la caja no se
	 *         ha podido crear
	 */
	public boolean crearNuevaCaja() {

		// Creamos el objeto caja
		Caja caja = new Caja();
		logger.debug("Se ha generado el objeto caja vacio");

		// Anadimos los parametros a la caja
		caja.setRestaurante(RestauranteDatos.get());
		logger.debug("Se ha establecido el restaurante ID {}", caja.getRestaurante().getIdRestaurante());

		caja.setEmpleado(new em);// TODO);
				logger.debug("Se ha establecido el restaurante ID {}", caja.getEmpleado().getIdRestauranteEmpleado()));

//		public Caja(Empleado empleado, BigDecimal importeInicial) {
//			this.restaurante = 
//			this.empleado = empleado;
//			this.numeroCaja = ConfiguracionInicial.get().getNumeroCaja();
//			this.numeroSesion = new CajasDaoHibernateImpl().obtenerSiguienteNumeroSesion();
//			this.momentoApertura = LocalDateTime.now();
//			this.momentoCierre = null;
//			this.importeInicial = importeInicial;
//			this.importeFinal = null;
//		}
	}
}
