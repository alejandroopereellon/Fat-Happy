package socket.modelo;

import java.io.Serializable;

public class Confirmacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numeroRestaurante;

	private int rol;

	private int numeroPedido;

	public Confirmacion() {
	}

	public int getNumeroRestaurante() {
		return numeroRestaurante;
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	/*
	 * 0. Caja 1. Expeditor 2. Cocina 3. Bebidas
	 */
	public int getRol() {
		return rol;
	}

	@Override
	public String toString() {
		return "Confirmacion [numeroRestaurante=" + numeroRestaurante + ", rol=" + rol + ", numeroPedido="
				+ numeroPedido + "]";
	}
	
	

}
