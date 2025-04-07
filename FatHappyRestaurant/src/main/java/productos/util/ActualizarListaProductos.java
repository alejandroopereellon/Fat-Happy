package productos.util;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import productos.dao.ProductosDAO;
import productos.dao.ProductosDaoGlobal;

public class ActualizarListaProductos extends Thread {
	// Crear el logger
	static Logger logger = LogManager.getLogger(ActualizarListaProductos.class);
	// Establecemos el dao
	private ProductosDAO dao = ProductosDaoGlobal.get();

	// Almacenamos en memoria la hora de actualizacion
	private LocalDateTime tiempo = null;

	public void run() {
		logger.info("Se ha iniciado el proceso de actualizacion de lista de productos");
		// 1. Forzamos la carga de los datos de productos en memoria
		actualizarDatos();
		/**
		 * 2. Iniciamos el bucle que comprueba si los productos se han actualizado
		 */
		while (true) {
			/*
			 * Si la ultima actualizacion del dao es diferente a la ultima actualizacion de
			 * la base de datos se realiza una actualizacion de la base de datos
			 */
			LocalDateTime nueva = dao.obtenerUltimaActualizacionProductos();
			if (tiempo == null || !tiempo.equals(nueva)) {
				actualizarDatos();
			}
			
			// Dormimos el proceso durante 5 segundos para temas de consumo de recursos
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("Ha ocurrido un error al hacer sleep del hilo");
			}
		}

	}

	private void actualizarDatos() {
		// Se realiza la actualizacion de los elementos de la base de datos
		new ListaProductosBuilder().crearListaProductos();
		// Establecemos la hora de la ultima actualizacion de la base de datos
		tiempo = dao.obtenerUltimaActualizacionProductos();
		logger.info("Lista de productos actualizada a {}", tiempo);
	}
}
