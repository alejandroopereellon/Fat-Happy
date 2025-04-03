/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliares.solicitarNumeroDecimal;

import java.math.BigDecimal;

/**
 *
 * @author aleja
 */
public class SolicitarNumeroDecimal {

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
	public BigDecimal solicitarNumero(String titulo) {
		InterfazSolicitudNumeroDecimales isn = new InterfazSolicitudNumeroDecimales(titulo);
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
		return new BigDecimal(isn.getCifra().replace(",", "."));
	}
}
