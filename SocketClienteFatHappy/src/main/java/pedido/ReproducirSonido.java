package pedido;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class ReproducirSonido {

	public ReproducirSonido(String nombreRecursoWav) {
		try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream(nombreRecursoWav)) {
			if (audioSrc == null) {
				System.out.println("No se encontró el recurso: " + nombreRecursoWav);
				return;
			}

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido: " + ex.getMessage());
		}
	}
}
