package caja.gestionCajas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import auxiliares.Escaner;
import auxiliares.Fechas;
import auxiliares.ModificarArchivo;
import caja.modelo.Caja;
import operaciones.Operaciones;
import usuarios.GestionEmpleados;

public class CerrarCajas {
	/**
	 * Este metodo realiza el cierre de caja de la sesion, para ello termina de
	 * modificar los parametros finales, recuperar la informacion de la caja crear
	 * un ticket con los datos, tambien da la opcion de mostrar los datos de la caja
	 * 
	 * @param cj caja de la que se extrae la informacion
	 * @return caja modificada y cerrada, lista para abrir otra
	 */
	public Caja cerrarCajas(Caja cj) {
		// Solicitamos la confirmacion del empleado responsable
		if (new GestionEmpleados().pedirAutorizacionEmpleado()) {
			// Establecemos el momento de cierre de la caja
			cj.setMomentoCierre(LocalDateTime.now());
			// Establencemos el estado de la caja en cerrado
			cj.setEstadoCaja("Cerrada");
			// Calculamos los importes de toda la caja
			calcularImportesCaja();
		}

//		// Mostramos la informacion de la caja
//		System.out.println("¿Deseas mostrar la informacion de la caja?\tS/N");
//		if (Escaner.pedirTexto().equalsIgnoreCase("S")) {
//			System.out.println(cj.toString());
//		}
//		System.out.println("¿Deseas mostrar las operaciones de la caja?\tS/N");
//		if (Escaner.pedirTexto().equalsIgnoreCase("S")) {
//			for (Operaciones ope : cj.operaciones) {
//				System.out.println(ope.toString());
//			}
//		}
//		// Creamos ticket con los datos de la caja
//		crearTicketCaja(cj); // TODO
		// Almacenamos las cajas para su posterior recuperacion
		new AlmacenarCajas().almacenarCajas(cj);
		return cj;
	}

	/**
	 * Este metodo genera un ticket con los datos de la caja incluye todos los datos
	 * de la caja junto con un listado de los pedidos cobrados, los importes y demas
	 * informacion
	 * 
	 * @param cj es la caja de la que se va a extraer la caja
	 */
	private static void crearTicketCaja(Caja cj) {
		// Introducimos los datos de la caja en un archivo de datos
		File generarTicketCaja = new File(cj.getRutaCaja() + "\\informeCaja.txt");
		if (!generarTicketCaja.exists()) {
			try {
				generarTicketCaja.createNewFile();
			} catch (IOException e) {
				System.out.println("No se ha podido generar el ticket de caja");
				e.printStackTrace();
			}
		}
		ModificarArchivo.anadirDatoAlArchivo(generarTicketCaja, cj.toString());
		ModificarArchivo.anadirDatoAlArchivo(generarTicketCaja,
				"\nListado de operaciones (" + cj.operaciones.size() + " Operaciones)");
		for (Operaciones ope : cj.operaciones) {
			ModificarArchivo.anadirDatoAlArchivo(generarTicketCaja, ope.toString() + "\n");
		}
	}

	// Este metodo va a calcular los importes de los pedidos realizados en las cajas
	private static void calcularImportesCaja() {

	}
}
