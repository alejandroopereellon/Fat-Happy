package auxiliares;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import auxiliares.incioAplicacion.IniciarAplicacion;

public class MainCliente {

	public static void main(String[] args) {
		try {
			Files.createDirectories(Paths.get(
					System.getenv().getOrDefault("LOG_DIR", System.getProperty("user.home") + "/fathappyclient/logs")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Iniciamos la aplicacion
		new IniciarAplicacion().inicio();
	}

}
