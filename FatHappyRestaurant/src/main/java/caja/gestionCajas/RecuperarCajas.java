package caja.gestionCajas;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import auxiliares.solicitarNumero.SolicitarNumero;
import caja.modelo.Caja;

public class RecuperarCajas {

	/**
	 * Este metodo permite seleccionar la caja que deseas recuperar, para ello hace
	 * uso del metodo SolicitarNumero para que de manera grafica podamos seleccionar
	 * el numero de caja para abrir o recuperar
	 * 
	 * A continuacion con el numero procede a recuperar la ultima sesion de la caja
	 * con el metodo recuperarCajaUltimaSesion
	 * 
	 * @return {@link Caja} recuperada o iniciada con el numero de caja introducida
	 */
	public Caja recuperarUltimaSesionCajaSeleccionada() {
		// Solicitamos el numero de la caja
		int numeroCaja = new SolicitarNumero().solicitarNumero("Introduce el numero de la caja");

		// Restauramos la ultima sesion de la caja seleccionada
		return recuperarCajaUltimaSesion(numeroCaja);
	}

	/**
	 * Este metodo se encarga de la recuperacion de las cajas, para ello realiza los
	 * siguientes pasos:
	 * 
	 * Primero extrae la ruta de la caja con {@link extraerRutaCaja}
	 * 
	 * Segundo buscamos el numero de sesiones creadas, para ello almacenamos en
	 * numeroSesionesCreadas el numero de ficheros que se han creado en total, si
	 * hay creados 3 fichero existen 3 sesiones creadas
	 * 
	 * Tercero: comprobamos si no existen ningun fichero en la carpeta de la caja,
	 * en este caso se procede a crear una nueva caja automaticamente
	 * 
	 * En caso de que existan varios archivos en el fichero se va a procesar la
	 * recuperacion del objeto con {@link procesarCajas}
	 * 
	 * @param numeroCaja es el numero de caja que se va a generar o recuperar
	 * @return {@link Caja} recuperadas o generadas
	 */
	public Caja recuperarCajaUltimaSesion(int numeroCaja) {
		// Extraemos la ruta de la caja
		File archivoCajas = new RutaCajas().extraerRutaCaja(numeroCaja);

		// Buscamos el numero de sesiones
		int numeroSesionesCreadas = archivoCajas.listFiles().length;

		// Si no hay ficheros generamos una caja
		if (numeroSesionesCreadas == 0) {
			return new IniciarCajas().iniciarCaja(numeroCaja);
		}
		// Recuperamos la informacion de la ultima caja y la procesamos
		else {
			return procesarCajas(archivoCajas, numeroSesionesCreadas);
		}
	}

	/**
	 * En caso de que exista mas de una sesion, vamos a recuperar la ultima sesion
	 * de las cajas y comprobar si esta cerrada o no, en caso de estar cerrada se va
	 * a generar una nueva, en caso de que exista se va a recuperar esa sesion
	 * 
	 * Con el metodo {@link recuperarObjetoCaja} asumimos que en caso de error se
	 * nos retornara un valor nulo, vamos procesar de manera de que en caso de ser
	 * nulo no se nos bloquee el programa generando, para ello se va a generar una
	 * nueva caja
	 * 
	 * Tambien se va a procesar en caso de que la caja recuperada estuviera cerrada,
	 * se habra otra caja nueva automaticamente
	 * 
	 * @param archivoCajas          es la ruta donde se almacena la caja
	 * @param numeroSesionesCreadas es el numero de sesiones que contiene la caja
	 * @return caja recuperada o generada en caso de estar cerrada o ser nula
	 */
	private Caja procesarCajas(File archivoCajas, int numeroSesionesCreadas) {
		// Creamos el directorio de la ultima sesion
		File archivoSesion = new File(archivoCajas + "\\SE" + numeroSesionesCreadas + "\\sesion.dat");
		// Recuperamos el archivo de la sesion
		Caja cajaRecuperada = recuperarObjetoCaja(archivoSesion);
		// Si la caja no es nula ni esta cerrada se procede a iniciar una nueva caa
		if (cajaRecuperada == null || cajaRecuperada.getEstadoCaja().equalsIgnoreCase("Cerrada")) {
			return new IniciarCajas().iniciarCaja(1);
		} /**
			 * En caso de que la caja esta activa y no sea nula vamos a recuperar este
			 * objeto
			 */
		else {
			return cajaRecuperada;
		}
	}

	/**
	 * Este metodo recupera de la memoria no volatil el objeto y no retorna.
	 * 
	 * En caso de que el objeto retornado produzca un error se ejecuta un mensaje
	 * explicando la situacion y procede a ejecutar una nueva caja
	 * 
	 * Si el objeto se ha recuperado de manera exitosa este se va a retornar
	 * 
	 * @param archivoSesion es la ruta donde se almacena la sesion actual de la caja
	 * @return Objeto {@link Caja} en caso de recuperarse correctamente, en caso de
	 *         que ocurra un error retorna un null
	 */
	private Caja recuperarObjetoCaja(File archivoSesion) {
		// Creamos el archivo caja en formato nulo
		Caja caja = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoSesion))) {
			caja = (Caja) ois.readObject(); // Leer objeto del archivo
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido recuperar la ultima caja", "Error recuperacion caja",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return caja;
	}

}
