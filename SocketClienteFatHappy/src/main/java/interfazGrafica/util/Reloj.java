package interfazGrafica.util;

import java.time.LocalDateTime;

import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Metodo que inicia un hilo que muestra la hora actual en la interfaz
 * 
 * @author Alejandro Perellón López
 */
public class Reloj extends Thread {
	// Crear el logger
	static Logger logger = LogManager.getLogger(Reloj.class);

	JLabel casilla;

	public Reloj(JLabel casilla) {
		this.casilla = casilla;
	}

	public void run() {
		logger.info("Se ha iniciado el hilo que maneja el reloj");
		String fecha = obtenerFecha();
		while (true) {
			obtenerHora();
			// Si la hora es igual a 0 y minutos es menor de 10 actualizamos la fecha;
			if (LocalDateTime.now().getHour() == 0 && LocalDateTime.now().getMinute() < 1) {
				fecha = obtenerFecha();
			}
			casilla.setText(fecha + "  " + obtenerHora());

			try {
				sleep(1000);
			} catch (InterruptedException ex) {
				logger.warn("Ha ocurrido un error en el hilo de reloj",ex);
			}
		}

	}

	private String obtenerFecha() {
		String dia = String.valueOf(LocalDateTime.now().getDayOfMonth());
		if (Integer.parseInt(dia) < 10) {
			dia = "0" + dia;
		}
		String mes = String.valueOf(LocalDateTime.now().getMonthValue());
		if (Integer.parseInt(mes) < 10) {
			mes = "0" + mes;
		}
		return dia + "/" + mes + "/" + LocalDateTime.now().getYear();
	}

	private String obtenerHora() {
		String hora = String.valueOf(LocalDateTime.now().getHour());
		if (Integer.parseInt(hora) < 10) {
			hora = "0" + hora;
		}
		String minuto = String.valueOf(LocalDateTime.now().getMinute());
		if (Integer.parseInt(minuto) < 10) {
			minuto = "0" + minuto;
		}
		String segundos = String.valueOf(LocalDateTime.now().getSecond());
		if (Integer.parseInt(segundos) < 10) {
			segundos = "0" + segundos;
		}
		return hora + ":" + minuto + ":" + segundos;

	}

}
