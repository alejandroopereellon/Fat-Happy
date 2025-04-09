package caja.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.solicitarNumeroDecimal.SolicitarNumeroDecimal;
import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.interfazCaja.PanelCaja;
import caja.interfazCaja.PanelCajaMetodos;
import caja.modelo.Caja;
import caja.modelo.CajaDatos;
import empleados.modelo.Empleado;
import empleados.modelo.EmpleadoDatos;
import empleados.util.ActividadEmpleados;
import restaurante.modelo.RestauranteDatos;

/**
 * Clase que crea un nuevo objeto caja
 * 
 * @author Alejandro Perellón López
 */
public class CajaBuilder {

	// Crear el logger
	static Logger logger = LogManager.getLogger(CajaBuilder.class);

	// Establecemos el metodo dao de las cajas
	private CajasDao dao = new CajasDaoHibernateImpl();
	// Establecemos el objeto caja de la clase
	private Caja caja = new Caja();
	// Establecemos el panel de caja
	private PanelCaja panel;

	/**
	 * Metodo que crea el nuevo objeto caja y lo establece en la clase singleton de
	 * {@link CajaDatos} para usarla en el sistema
	 * 
	 * Esta va ejecutando todos los pasos a los que tiene asignado un metodo, segun
	 * se vayan cumpliendo los requistos se continua con la siguiente ejecucion y al
	 * finalizar la ejecucion realiza el veredicto de si iniciar la caja o no
	 * 
	 * @return TRUE si la caja se ha creado correctamente || FALSE si la caja no se
	 *         ha podido crear
	 */
	public boolean crearNuevaCaja() {
		// Solicitamos permisos de administrador
		if (new ActividadEmpleados().solicitarPermisos("Iniciar una nueva caja", 3)) {
			logger.error("No existen permisos suficientes para iniciar una nueva caja");
			return false;
		}
		Boolean bandera = true;
		logger.debug("Se ha generado el objeto caja vacio");

		// 1. Establecemos el restaurante
		if (!anadirRestaurante()) {
			logger.error("No se ha podido obtener el restaurante, no se activará la caja");
			bandera = false;
		}
		// 2. Establecemos el numero de caja del hardware del restaurante
		if (!establecerNumeroCaja()) {
			logger.error("No se ha podido obtener el numero de caja, no se activará la caja");
			bandera = false;
		}

		// 3. Establecemos el empleado responsable de la caja
		if (!anadirEmpleado()) {
			logger.error("No se ha podido obtener el empleado, no se activará la caja");
			bandera = false;
		}
		// 3. Establecemos el importe inicial de la caja
		if (!anadirImporte()) {
			logger.error("No se ha podido obtener el importe, no se activará la caja");
			bandera = false;
		}

		// 4. Establecemos el numero de sesion de la caja
		caja.setNumeroSesion(new CajasDaoHibernateImpl().obtenerSiguienteNumeroSesion());

		// 5. Establecemos el momento de inicio de la caja
		caja.setMomentoApertura(LocalDateTime.now());

		// Si toda la configuracion ha funcionado correctamente se añade la caja al
		// singleton
		if (bandera) {
			logger.info("La caja ha sido iniciada correctamente");
			// Establecemos la caja en el singleton
			CajaDatos.set(caja);
			logger.info("Se ha establecido la caja en el singleton");
			// Anadimos la caja en el DAO
			dao.insertarCaja(caja);
			logger.info("Se se ha añadido la caja en la base de datos");

			// Establecemos los datos de la caja
			new PanelCajaMetodos(panel).rellenarDatosCaja();

			return true;
		}

		logger.error("Ha ocurrido un error durante el proceso de inicio de la caja");
		JOptionPane.showConfirmDialog(null, "La caja no ha podido iniciarse correctamente, vuelve a intentarlo",
				"Error inicio de caja", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	/**
	 * Metodo que establece el importe inicial de la caja, para ello hace un
	 * {@link SolicitarNumeroDecimal} y lo añade a la caja
	 * 
	 * @return TRUE si se ha añadido el importe en la caja || FALSE si no se ha
	 *         añadido el importe en la caja
	 */
	private boolean anadirImporte() {
		BigDecimal numeroDecimal = new SolicitarNumeroDecimal().solicitarNumero("Introduce el importe inicial");
		if (numeroDecimal != null) {
			caja.setImporteInicial(numeroDecimal);
			logger.info("Se ha establecido el importe inicial de la caja en {}", numeroDecimal);
			return true;
		}
		logger.error("Se ha establecido un importe incorrecto o se ha cancelado la operacion");
		return false;
	}

	/**
	 * Metodo que establece el numero de caja en {@link CajaBuilder}, obtiene de la
	 * configuracion inicial el numero de caja y la establece en
	 * 
	 * @return TRUE si el numero de caja se ha establecido correctamente || FALSE si
	 *         no se ha podido obtener
	 */
	private boolean establecerNumeroCaja() {
		if (ConfiguracionInicial.get().getNumeroCaja() != 0) {
			caja.setNumeroCaja(ConfiguracionInicial.get().getNumeroCaja());
			logger.info("Se ha establecido el numero de caja en {}", ConfiguracionInicial.get().getNumeroCaja());
			return true;
		}
		logger.warn("El numero de caja esta mal configurado");
		return false;
	}

	/**
	 * Metodo que comprueba desde el singleton si el restaurante existe, si el
	 * restaurante existe se va a añadir a la caja y se confirma
	 * 
	 * @return TRUE si la caja existe || FALSE si la caja no existe
	 */
	private boolean anadirRestaurante() {
		// Si la caja es nula se retorna un error
		if (RestauranteDatos.get() == null) {
			logger.error("El restaurante en singleton no existe");
			return false;
		}
		caja.setRestaurante(RestauranteDatos.get());
		logger.debug("Se ha establecido el restaurante ID {}", caja.getRestaurante().getIdRestaurante());
		return true;
	}

	/**
	 * Metodo que genera un nuevo {@link Empleado} y lo añade a la caja y a
	 * singleton de empleado para sus posteriores consultas
	 * 
	 * @return TRUE en caso de que se recupere el empleado y se almacene en
	 *         singleton || FALSE en caso de que no se recupere nada
	 */
	private boolean anadirEmpleado() {
		// Solicitamos un nuevo empleado
		Empleado emp = new ActividadEmpleados().obtenerEmpleado();
		// Si el empleado no es nulo lo añadimos a singleton y en caja
		if (emp != null) {
			caja.setEmpleado(emp);
			EmpleadoDatos.set(emp);
			logger.info("Se ha recuperado el empleado con id {}, se añade en singleton y en caja", emp.getIdEmpleado());
			return true;
		}
		logger.error("No se ha añadido el empleado en el singleton ni en caja");
		return false;
	}
}
