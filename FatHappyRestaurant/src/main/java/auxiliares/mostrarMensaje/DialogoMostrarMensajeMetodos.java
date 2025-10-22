package auxiliares.mostrarMensaje;

import auxiliares.singleton.ClasesEstaticas;

/**
 * Clase encargada de iniciar el dialogo que muestra la advertencia al usuario
 * 
 * @author Alejandro Perellón López
 */
public class DialogoMostrarMensajeMetodos {

	public void mostrarMensaje(String titulo) {
		new DialogoMostrarMensaje(titulo).setVisible(true);
	}

	public void buscarMensajes(String codigoMensaje, Object... args) {
		new DialogoMostrarMensaje(ClasesEstaticas.getProveedorMensaje().findMessage(codigoMensaje, args))
				.setVisible(true);
	}

	public void buscarMensajes(String codigoMensaje) {
		new DialogoMostrarMensaje(ClasesEstaticas.getProveedorMensaje().findMessage(codigoMensaje)).setVisible(true);
	}
}
