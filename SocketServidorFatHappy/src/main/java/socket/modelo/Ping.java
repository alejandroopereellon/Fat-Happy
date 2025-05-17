package socket.modelo;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Ping implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int numeroComprobacion = ThreadLocalRandom.current().nextInt(100000, 1000000);;

	@Override
	public String toString() {
		return "[Ping " + numeroComprobacion + "]";
	}
	
	public int getNumeroComprobacion() {
		return numeroComprobacion;
	}

}
