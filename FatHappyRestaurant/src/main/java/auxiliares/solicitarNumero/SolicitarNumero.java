/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliares.solicitarNumero;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aleja
 */
public class SolicitarNumero {

	// Crear el logger
	static Logger logger = LogManager.getLogger(SolicitarNumero.class);

	/**
	 * Este metodo solicita un numero a traves de la interfaz grafica, para ello
	 * vamos a ejecutar la interfaz que solicita el numero, iniciamos el objeto y lo
	 * hacemos visible, vamos a hacer uso de la sincronizacion, wait y el bucle
	 * while para realizar de manera eficiente la recuperacion del numero
	 *
	 * DESCRIPCION WHILE: Mientras la ventana del objeto SolicitudEmpleado sea
	 * visible va a estar esperando al objeto para que retorne un empleado, una vez
	 * que se ha cerrado la ventan se va a recuperar el numero
	 * 
	 * @param titulo es el titulo de la ventana y el motivo por el que se solicita
	 *               el numero
	 * @return numero obtenido a traves de la interfaz grafica
	 */
	public int solicitarNumero(String titulo) {
		InterfazSolicitudNumero isn = new InterfazSolicitudNumero(titulo);
		isn.setVisible(true);

		synchronized (isn) {// Sincronización para esperar la respuesta del usuario
			try {
				while (isn.isVisible()) { // Mientras la ventana esté abierta
					isn.wait(); // Pausa el hilo actual hasta recibir notificación
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.debug("Se ha retornado el numero {}", isn.getCifra());

		try {
			logger.info("Se va a retornar el numero introducido");
			return Integer.parseInt(isn.getCifra());
		} catch (NumberFormatException e) {
			logger.error("El numero introducido en la solicitud esta vacio");
			return 0;
		}
	}
}
