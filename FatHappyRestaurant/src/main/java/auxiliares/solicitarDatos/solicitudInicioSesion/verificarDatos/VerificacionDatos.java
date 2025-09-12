package auxiliares.solicitarDatos.solicitudInicioSesion.verificarDatos;

public interface VerificacionDatos {

	/**
	 * Metodo encargado de la verificacion de que el puerto introducido por el
	 * usuario es candidato. Debe comprobar que esta en los rangos 0 - 65,536
	 * 
	 * @param numeroPuerto es el numero de puerto que se va a verificar
	 * @return TRUE en caso de que el puerto sea apto && FALSE en caso de que el
	 *         puerto no sea apto u erroneo
	 */
	public boolean verificarPuerto(String numeroPuerto);

	/**
	 * Metodo encargado de la verificacion de que la direccion ip introducida por el
	 * usuario tenga el formato de direccion ip correcto, este no verifica que la
	 * conexion sea la correcta. Debe comprobar que el rango sea 0.0.0.0 -
	 * 255.255.255.255 y realizar una adecuada notificacion al usuario de si es apta
	 * o no
	 * 
	 * @param direccionIP es la direccion ip que se va a verificar
	 * @return TRUE en caso de que la direccion ip sea correcta || FALSE en caso de
	 *         que la direccion ip sea imposible
	 */
	public boolean verificarDireccionIP(String direccionIP);
}
