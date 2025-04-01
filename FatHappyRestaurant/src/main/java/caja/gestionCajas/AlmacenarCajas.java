package caja.gestionCajas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import auxiliares.Fechas;
import caja.modelo.Caja;

public class AlmacenarCajas {
	/**
	 * Este metodo almacena los datos de la caja en un medio no volatil para su
	 * posterior recuperacion en caso de cierre del sistema
	 * 
	 * @param cj es la caja que se va a almacenar en el medio no volatil
	 */
	protected void almacenarCajas(Caja cj) {
		// Obtenemos la ruta de la caja
		File rutaCaja = new File(RutaCajas.ArchivoCajas + new Fechas().obtenerFechaActualSinFormato(LocalDateTime.now())
				+ "\\CJ" + cj.getNumeroCaja() + "\\SE" + cj.getNumeroSesion() + "\\sesion.dat");
		if (!rutaCaja.exists()) {
			rutaCaja.getParentFile().mkdirs();
			try {
				rutaCaja.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Almacenamos el objeto caja en el fichero
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaCaja))) {
			oos.writeObject(cj); // Guardar objeto en archivo
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
