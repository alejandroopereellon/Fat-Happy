package caja.gestionCajas;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import auxiliares.solicitarNumero.SolicitarNumero;
import auxiliares.solicitarNumeroDecimal.SolicitarNumeroDecimal;
import caja.modelo.Caja;
import usuarios.Empleados;
import usuarios.GestionEmpleados;

/**
 * La clase {@code IniciarCajas} se encarga de gestionar la inicialización y el
 * cambio de sesión de las cajas. Permite iniciar una nueva caja, ya sea
 * solicitando un número de caja si no hay ninguna abierta, o reusando el número
 * de una caja existente y creando una nueva sesión.
 */
public class IniciarCajas {

	/**
	 * Metodo basico que solicita el numero de caja en la que se va a trabajar y
	 * genera la caja mediante el metodo {@link IniciarCajas}
	 * 
	 * @return {@link Caja} con el numero de caja personalizada
	 */
	public Caja iniciarCajaPersonalizada() {
		int numeroCaja = new SolicitarNumero().solicitarNumero("Introduce el numero de la caja");
		return iniciarCaja(numeroCaja);
	}

	/**
	 * Este metodo inicia un nuevo objeto caja, para ello solicita los datos
	 * necesarios para su ejecucion:
	 * 
	 * Primero: comprueba si la caja tiene una sesion anterior abierta, en caso de
	 * que la sesion este abierta va a retornar esta sesion para su uso o cierre
	 * 
	 * Segundo: Creacion de los datos y solicitud de los datos, para ello se genera
	 * un numero de sesion, solicitamos el nombre del empleado responsable de la
	 * caja y a continuacion el importe necesario de la caja, y una confirmacion de
	 * que el empleado es un administrador
	 * 
	 * 
	 * @param numeroCaja el número de caja para la cual se inicia una nueva sesión.
	 * @return un objeto {@link Caja} con la información de la nueva sesión
	 *         iniciada, o {@code null} si el proceso no se completa correctamente.
	 */
	public Caja iniciarCaja(int numeroCaja) {
//		// Comprobamos que la caja no tenga ninguna sesion anterior
//		if (new RecuperarCajas().recuperarCajaUltimaSesion(numeroCaja).getEstadoCaja().equalsIgnoreCase("operativa")) {
//			JOptionPane.showMessageDialog(null, "Hay una sesion anterior abierta, cierra primero la caja",
//					"Error caja ya abierta", JOptionPane.ERROR_MESSAGE);
//			return new RecuperarCajas().recuperarCajaUltimaSesion(numeroCaja);
//		}

		// Creamos el numero de sesion
		int numeroSesion = new RutaCajas().extraerRutaCaja(numeroCaja).listFiles().length + 1;

		// Solicitamos la informacion del empleado responsable de la caja
		Empleados emp = new GestionEmpleados().solicitarEmpleado("Introduce numero empleado");
		// Si se retorna un empleado null cancelamos la operacion
		if (emp == null) {
			return null;
		}

		// Solicitamos el importe inicial de la caja
		BigDecimal importeInicial = new SolicitarNumeroDecimal().solicitarNumero("Introduce el importe inicial");
		// Si el importe iniciar de la caja es nulo cancelamos la operacion
		if (importeInicial == null) {
			return null;
		}

		// Verificamos que el empleado esté autorizado para iniciar la caja
		if (new GestionEmpleados().pedirAutorizacionEmpleado("Introduce empleado manager")) {
			Caja cj = new Caja(numeroCaja, numeroSesion, emp, importeInicial);
			// Almacenamos las cajas para su posterior recuperacion
			new AlmacenarCajas().almacenarCajas(cj);
			// Retornamos la caja
			return cj;
		}
		// Si no se completa la autorización del empleado, devolvemos null
		return null;
	}

}
