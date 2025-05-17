package socket.modelo;

import java.io.Serializable;

public class RolSocket implements Serializable {
	private static final long serialVersionUID = 1L;

	private int numeroRestaurante;

	/*
	 * 0. Caja 1. Expeditor 2. Cocina 3. Bebidas
	 */
	private int rolCliente;

	public RolSocket(int numeroRestaurante, int rolCliente) {
		this.numeroRestaurante = numeroRestaurante;

		this.rolCliente = rolCliente;
		if (rolCliente < 0 || rolCliente > 3)
			throw new IllegalArgumentException("Rol inválido: " + rolCliente);
	}

	public RolSocket() {
	}

	public int getRolCliente() {
		return rolCliente;
	}

	public int getNumeroRestaurante() {
		return numeroRestaurante;
	}

	@Override
	public String toString() {
		return "RolSocket [numeroRestaurante=" + numeroRestaurante + ", rolCliente=" + rolCliente + "]";
	}

}
