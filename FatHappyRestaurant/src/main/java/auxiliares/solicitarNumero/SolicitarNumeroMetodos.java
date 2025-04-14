package auxiliares.solicitarNumero;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import auxiliares.inicioAplicacion.ConfiguracionInicial;

/**
 * Clase de metodos que realiza todas las funciones necesarias para el
 * funcionamiento de la clase de {@link DialogoSolicitudNumero}
 * 
 * @author Alejandro Perellón López
 */
public class SolicitarNumeroMetodos {
	// Crear el logger
	static Logger logger = LogManager.getLogger(SolicitarNumeroMetodos.class);

	private DialogoSolicitudNumero interfaz;

	public SolicitarNumeroMetodos(String titulo) {
		if (ConfiguracionInicial.get()==null) {
			this.interfaz = new DialogoSolicitudNumero(null, true, titulo,this);
		}else {
			this.interfaz = new DialogoSolicitudNumero(ConfiguracionInicial.get().getVentanaPrincipal(), true, titulo,this);
		}
		
	}

	/**
	 * Metodo que inicia el {@link DialogoSolicitudNumero} para obtener el numero
	 * solicitado por el usuario
	 * 
	 * @param titulo es el motivo por el que se solicita el numero
	 * @return numero introducido en {@link DialogoSolicitudNumero}
	 */
	public int solicitarNumero() {
		// Aqui se bloquea el hilo principal hasta que el dialogo se cierre
		interfaz.setVisible(true);
		// Una vez cerrado almacenamos el valor del numero
		int numero = obtenerNumero();
		// Cerramos el cuadro de dialogo al obtener el numero
		interfaz.dispose();

		logger.debug("Se ha retornado el numero {}", numero);
		// Retornamos el numero
		return numero;
	}

	/**
	 * Metodo que añade un numero a la cadena de numeros
	 *
	 * @param numero numero que se va a añadir a la cadena
	 */
	protected void anadirNumero(int numero) {
		String cifra = interfaz.getCuadroTexto().getText() + numero;
		interfaz.getCuadroTexto().setText(cifra);
	}

	/**
	 * Metodo que borra todo el contenido del texto
	 */
	protected void borrarTexto() {
		interfaz.getCuadroTexto().setText("");
	}

	/**
	 * Metodo que recupera del {@link DialogoSolicitudNumero} el numero introducido
	 * por el programa
	 * 
	 * @return Numero introducido en interfaz, en caso de error retorna 0
	 */
	private int obtenerNumero() {
		try {
			logger.info("Se va a retornar el numero introducido");
			return Integer.parseInt(interfaz.getCuadroTexto().getText());
		} catch (NumberFormatException e) {
			logger.error("El numero introducido en la solicitud esta vacio");
		}
		return 0;
	}
}
