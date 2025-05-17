package socket.modelo;

import java.io.Serializable;

import auxiliares.singleton.ClasesEstaticas;

public class Confirmacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numeroRestaurante = ClasesEstaticas.getNumerorestaurante();

	private int rol = ClasesEstaticas.getRolcliente();

	private int numeroPedido;

	public Confirmacion(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public int getNumeroRestaurante() {
		return numeroRestaurante;
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public int getRol() {
		return rol;
	}

}
