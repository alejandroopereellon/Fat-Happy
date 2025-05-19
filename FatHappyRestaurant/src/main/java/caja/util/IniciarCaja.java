package caja.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.singleton.ClasesEstaticas;
import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Caja;
import empleados.modelo.Empleado;

/**
 * Clase encarga de la recuperacion de la ultima caja disponible en el
 * restaurante y en el numero de caja fisica
 * 
 * @author Alejandro Perellón López
 */
public class IniciarCaja {
	// Crear el logger
	static Logger logger = LogManager.getLogger(IniciarCaja.class);

	private CajasDao dao = new CajasDaoHibernateImpl();

	/**
	 * Metodo encargado de recuperar la {@link Caja}, se inicia automaticamente al
	 * inicio del programa , en caso de que la {@link Caja} exista en el medio de
	 * persistencia se va a cargar en memoria tambien el {@link Empleado}
	 * responsable de esa {@link Caja}
	 */
	public boolean recuperarCajaInicio() {
		Caja cajaRecuperada = recuperarCajaDAO();

		// Si la caja recuperada no es nula se va a
		if (cajaRecuperada != null && cajaRecuperada.getImporteFinal() == null) {
			// Establecemos el empleado de la caja en singleton
			ClasesEstaticas.setEmpleado(cajaRecuperada.getEmpleado());
			logger.info("Se ha establecido el empleado con ID {}", cajaRecuperada.getEmpleado().getIdEmpleado());
			// Establecemos la caja recuperada del dao en singleton
			establecerCaja(cajaRecuperada);
			logger.info("Se ha establecido la caja con ID {}", cajaRecuperada.getId());
			return true;
		}
		logger.info("La caja recuperada del metodo DAO es nula, no se inicia ninuna caja");
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
		Caja caja = dao.recuperarCaja();
		// Si la caja recuperada es nula se va a crear una nueva caja
		if (caja != null) {
			logger.info("Se ha recuperado la caja correctamente del DAO");
			return caja;
		}
		logger.info("No se ha obtenido la caja desde el metodo DAO");
		return null;
	}

	/**
	 * Metodo que establece en el singleton la {@link Caja} que se va a utilizar en
	 * el sistema
	 * 
	 * @param caja es la {@link Caja} que se va a meter en global {@link CajaDatos}
	 */
	public void establecerCaja(Caja caja) {
		ClasesEstaticas.setCaja(caja);
	}
}
