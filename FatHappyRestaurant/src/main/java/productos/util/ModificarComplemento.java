package productos.util;

import productos.modelo.Complemento;
import productos.modelo.Producto;
import productos.modelo.Salsa;

/**
 * Clase que contiene los metodos necesarios para modificar un complemento
 * 
 * @author Alejandro Perellón López
 */
public class ModificarComplemento {
	private Complemento complemento;

	public ModificarComplemento(Complemento complemento) {
		this.complemento = complemento;
	}

	public boolean anadirSalsaComplemento(Producto pro) {
		if (complemento.getSalsas().size() < complemento.getNumeroSalsas()) {
			complemento.getSalsas().add((Salsa) pro);
			return true;
		}

		return false;
	}
}
