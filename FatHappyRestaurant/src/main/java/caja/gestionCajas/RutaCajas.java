package caja.gestionCajas;

import java.io.File;
import java.time.LocalDateTime;

import auxiliares.Fechas;
import caja.modelo.Caja;

public class RutaCajas {
	/** Ruta base donde se almacenan los datos de las cajas. */
	protected final static String ArchivoCajas = "src\\caja\\SistemaCajas\\";

	/**
	 * Este metodo solicita el numero de caja y genera la ruta donde se va a
	 * almacenar los datos de la caja, esta omite el numero de sesion.
	 * 
	 * Se comprueba si existe la carpeta, en caso de que no existe se va a generar
	 * el directorio en el equipo
	 * 
	 * @param numeroCaja es el numero de caja con la que se va a trabajar
	 * @return File con la ruta de la caja, para trabajar posteriormente con ella
	 */
	protected File extraerRutaCaja(int numeroCaja) {
		// Generamos el directorio donde se van a crear la sesion de la caja
		File archivoCajas = new File(
				ArchivoCajas + new Fechas().obtenerFechaActualSinFormato(LocalDateTime.now()) + "\\CJ" + numeroCaja);
		// En caso de que no exista la ruta de las cajas se va a generar el directorio
		if (!archivoCajas.exists()) {
			archivoCajas.mkdirs();
		}
		return archivoCajas;
	}

	/**
	 * Este metodo retorna la ruta de las cajas
	 * 
	 * @param cj es la {@link Caja} que se va a introducir para poder obtener la
	 *           ruta de las cajas
	 * @return {@link File} con la ruta donde se almacena la informacion de las
	 *         cajas
	 */
	protected File extraerRutaCajaSesion(Caja cj) {
		// Obtenemos la ruta de la caja
		File rutaCaja = extraerRutaCaja(cj.getNumeroCaja());
		// Generamos y retornamos la ruta de la sesion y de la caja
		return new File(rutaCaja + "\\SE" + cj.getNumeroSesion());
	}
}
