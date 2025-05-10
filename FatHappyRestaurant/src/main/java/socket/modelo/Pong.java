package socket.modelo;

import java.io.Serializable;

public class Pong implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int numeroComprobacion;

	public Pong(int numeroComprobacion) {
		this.numeroComprobacion = numeroComprobacion;
	}

	@Override
	public String toString() {
		return "[Pong " + numeroComprobacion + "]";
	}

}
