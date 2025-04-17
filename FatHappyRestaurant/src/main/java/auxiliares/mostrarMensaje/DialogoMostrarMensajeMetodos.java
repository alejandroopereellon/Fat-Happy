package auxiliares.mostrarMensaje;

/**
 * Clase encargada de iniciar el dialogo que muestra la advertencia al usuario
 * 
 * @author Alejandro Perellón López
 */
public class DialogoMostrarMensajeMetodos {
	
	
	public void mostrarMensaje(String titulo) {
		new DialogoMostrarMensaje(titulo).setVisible(true);
	}
}
