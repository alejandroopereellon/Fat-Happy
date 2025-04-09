/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliares.solicitarNumeroDecimal;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.solicitarNumero.DialogoSolicitudNumero;

/**
 * Clase de metodos que realiza todas las funciones necesarias para el
 * funcionamiento de la clase de {@link DialogoSolicitudNumeroDecimal}
 * 
 * @author Alejandro Perellón López
 */
public class SolicitarNumeroDecimalMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(SolicitarNumeroDecimalMetodos.class);

	private DialogoSolicitudNumeroDecimal interfaz;

	public SolicitarNumeroDecimalMetodos() {
		this.interfaz = new DialogoSolicitudNumeroDecimal(ConfiguracionInicial.get().getVentanaPrincipal(), true, this);
	}

	/**
	 * Metodo que inicia el {@link DialogoSolicitudNumero} para obtener el numero
	 * solicitado por el usuario
	 * 
	 * @param titulo es el motivo por el que se solicita el numero
	 * @return numero introducido en {@link DialogoSolicitudNumero}
	 */
	public BigDecimal solicitarNumero() {
		// Aqui se bloquea el hilo principal hasta que el dialogo se cierre
		interfaz.setVisible(true);
		// Una vez cerrado almacenamos el valor del numero
		BigDecimal numero = obtenerNumero();
		// Cerramos el cuadro de dialogo al obtener el numero
		interfaz.dispose();

		logger.debug("Se ha retornado el numero {}", numero);
		// Retornamos el numero
		return numero;
	}

	/**
	 * Metodo que añade el numero introducido por parametro al texto, lo procesa y
	 * lo pone mas visual y lo añade al cuadro de texto
	 * 
	 * @param numero es el digito que se va a añadir
	 */
	protected void anadirNumero(int numero) {
		// Generamos la nueva cifra
		String cifra = interfaz.getCuadroTexto().getText() + numero;
		// Procesamos la nueva cifra
		cifra = new GestionDecimales().procesarDecimales(cifra);
		// Devolvemos la cifra
		interfaz.getCuadroTexto().setText(cifra);
		logger.debug("Se ha anadido un numero a la cifra, cifra actual {}", interfaz.getCuadroTexto().getText());
	}

	/**
	 * Metodo que borra todos los digitos de la interfaz
	 */
	protected void borrarTexto() {
		interfaz.getCuadroTexto().setText("0,00");
		logger.debug("Se ha borrado el texto");
	}

	/**
	 * Metodo que recupera del {@link DialogoSolicitudNumeroDecimal} el numero
	 * introducido por el programa
	 * 
	 * @return Numero introducido en interfaz, en caso de error retorna 0
	 */
	private BigDecimal obtenerNumero() {
		try {
			logger.info("Se va a retornar el numero introducido");
			String cifra = interfaz.getCuadroTexto().getText().replace(",", ".");
			return new BigDecimal(cifra);
		} catch (NumberFormatException e) {
			logger.error("El numero introducido en la solicitud esta vacio");
		}
		return new BigDecimal("0.00");
	}
}
