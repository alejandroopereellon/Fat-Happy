package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import socket.utilServidor.IniciarServidor;

public class MainServidor {
	public static void main(String[] args) {
		try {
			Files.createDirectories(Paths.get(
					System.getenv().getOrDefault("LOG_DIR", System.getProperty("user.home") + "/fathappyserver/logs")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Iniciamos el servidor
		new IniciarServidor().start();
	}
}
