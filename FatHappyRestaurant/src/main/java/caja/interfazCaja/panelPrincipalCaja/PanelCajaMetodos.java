package caja.interfazCaja.panelPrincipalCaja;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.singleton.ClasesEstaticas;
import caja.interfazCaja.Reloj;
import caja.modelo.Caja;

/**
 * Clase que gestiona la interfaz del panel de caja
 * 
 * @author Alejandro Perellón López
 */
public class PanelCajaMetodos {

	// Crear el logger
	static Logger logger = LogManager.getLogger(PanelCajaMetodos.class);
	// Interfaz de la ventana principal
	private PanelCaja interfaz;

	// Constructor
	public PanelCajaMetodos(PanelCaja interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Metodo que se utiliza para iniciar la caja por primera vez
	 */
	public void iniciarPanelCaja() {
		// Iniciamos el reloj
		new Reloj(interfaz.getFechaHora()).start();
		logger.info("Se ha configurado el reloj");

		rellenarDatosCaja();
		interfaz.setVisible(true);
	}

	/**
	 * Metodo que rellena los datos de la caja
	 */
	public void rellenarDatosCaja() {
		// Obtenemos la caja de singleton
		Caja caja = ClasesEstaticas.getCaja();

		if (caja != null && caja.getEmpleado() != null) {
			// Establecemos el nombre y apellidos del empleado
			interfaz.getDatosEmpleado().setText(caja.getEmpleado().getNombre() + " " + caja.getEmpleado().getApellido()
					+ " (" + caja.getEmpleado().getIdRestauranteEmpleado() + ")");
			logger.debug("Se ha establecido los datos del empleado");

			// Establecemos el puesto y sesion
			interfaz.getPuestoSesion()
					.setText("Puesto " + caja.getNumeroCaja() + " | Sesion " + caja.getNumeroSesion());
			logger.debug("Se ha establecido el numero de sesion y el puesto");

			actualizarEstadoCaja(caja);

			// Almacenamos la caja en el singleton
			ClasesEstaticas.setPanelCaja(interfaz);
		} else {
			establecerInterfazNula();
			logger.info("Se ha establecido la interfaz panelCaja como nula");
		}
	}

	/**
	 * Metodo que establece el estado de la {@link Caja} en activa o cerrada
	 * dependiendo de si la caja es nula, o existe y no tiene fecha de momento
	 * cierre
	 * 
	 * @param caja es la caja de la que se extraen los datos
	 */
	private void actualizarEstadoCaja(Caja caja) {
		// Establecemos la caja en abierta o cerrada
		if (caja != null && caja.getMomentoCierre() == null) {
			interfaz.getEstadoCaja().setText("Activa");
			logger.info("Se ha establecido el estado de la caja en abierta");
		} else {
			interfaz.getEstadoCaja().setText("Cerrada");
			logger.info("Se ha establecido el estado de la caja en cerrada");
		}
	}

	/**
	 * Metodo que establece los datos de la interfaz en nula
	 */
	private void establecerInterfazNula() {
		// Establecemos el nombre y apellidos del empleado
		interfaz.getDatosEmpleado().setText("No hay usuario activo");
		logger.debug("Se han establecido los datos del empleado en nulo");
		// Establecemos el puesto y sesion
		interfaz.getPuestoSesion().setText("Puesto " + ConfiguracionInicial.get().getNumeroCaja() + " | Sesion 0");
		logger.debug("Se ha establecido el numero de sesion y el puesto en nula");
		actualizarEstadoCaja(null);
		logger.info("Se ha establecido el estado de la caja en cerrada");
	}

}
