package caja.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Caja;
import caja.modelo.CajaDatos;
import empleados.dao.EmpleadoDaoHibernateImpl;
import empleados.dao.EmpleadosDao;
import empleados.modelo.EmpleadoDatos;
import restaurante.modelo.RestauranteDatos;

/**
 * Clase encarga de la recuperacion de la ultima caja disponible en el
 * restaurante y en el numero de caja fisica
 */
public class IniciarCaja {
	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarCaja.class);

	
	
	/**
	 * Metodo encargado de recuperar la caja, este metodo se inicia automaticamente
	 * al inicio del programa y recupera la caja, en caso de que la caja exista se
	 * va a cargar en memoria tambien el empleado que era responsable de esa caja
	 */
	public boolean recuperarCajaInicio() {
		Caja cajaRecuperada = null;
		cajaRecuperada = recuperarCajaDAO();
		// Si la caja recuperada no es nula se va a
		if (cajaRecuperada != null) {
			// Establecemos el empleado de la caja en singleton
			EmpleadoDatos.set(cajaRecuperada.getEmpleado());
			logger.info("Se ha establecido el empleado con ID {}", cajaRecuperada.getEmpleado().getIdEmpleado());
			// Establecemos la caja recuperada del dao en singleton
			establecerCaja(cajaRecuperada);
			logger.info("Se ha establecido la caja con ID {}", cajaRecuperada.getId());
			return true;
		}
		logger.error("No se ha podido obtener la informacion de la caja");
		return false;
	}

	/**
	 * Metodo que busca la caja desde el DAO y en caso de existir se va a retornar
	 * la caja
	 * 
	 * @return {@link Caja} recuperada de la clase {@link CajasDao}
	 */
	public Caja recuperarCajaDAO() {
		// Intentamos recuperar la ultima caja de la base de datos
		Caja caja = new CajasDaoHibernateImpl().recuperarCaja();
		// Si la caja recuperada es nula se va a crear una nueva caja
		if (caja != null) {
			logger.info("Se ha recuperado la caja correctamente");
			return caja;
		}
		logger.info("No se ha obtenido la caja desde el metodo DAO");
		return null;
	}

	/**
	 * Metodo que establece en el singleton la caja que se va a utilizar en el
	 * sistema
	 * 
	 * @param caja es la caja que se va a meter en global
	 */
	public void establecerCaja(Caja caja) {
		CajaDatos.set(caja);
	}
}
