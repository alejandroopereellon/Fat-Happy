package main;

import socket.utilServidor.IniciarServidor;

public class MainServidor {
	public static void main(String[] args) {
		// Iniciamos el servidor
		new IniciarServidor().start();
	}
}
