package auxiliares.incioAplicacion;

import auxiliares.singleton.ClasesEstaticas;
import auxiliares.solicitarNumero.SolicitarNumeroMetodos;
import auxiliares.solicitarRol.solicitarRol;
import interfazGrafica.ventanaPrincipal.VentanaPrincipalMetodos;
import socket.util.ConectarAlServidor;

public class IniciarAplicacion {
	public void inicio() {

		// Establecemos el escalado de las imagenes al 100%
		System.setProperty("sun.java2d.uiScale", "1.0");

		// Establecemos el look and feel
		new VentanaPrincipalMetodos();
		VentanaPrincipalMetodos.aplicarLookAndFeel();

		// Creamos la ventana principal
		new VentanaPrincipalMetodos().inciarVentana();

		// Seleccionamos el numero de restaurante
		ClasesEstaticas.setNumeroRestaurante(
				new SolicitarNumeroMetodos("Introduce el numero de restaurante").solicitarNumero());

		// Selecionamos el rol del cliente
		new solicitarRol().setVisible(true);

		// Establecemos conexion al servidor
		new ConectarAlServidor().start();
	}

}
