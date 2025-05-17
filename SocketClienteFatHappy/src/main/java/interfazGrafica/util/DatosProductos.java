package interfazGrafica.util;

import productos.modelo.Bebida;
import productos.modelo.Complemento;
import productos.modelo.Extra;
import productos.modelo.Hamburguesa;
import productos.modelo.Ingrediente;
import productos.modelo.Postre;
import productos.modelo.Salsa;

public class DatosProductos {

	private static final String LINEA = System.lineSeparator();
	private static final String ESPACIO = "  "; // Dos espacios

	public StringBuilder informacionPostre(Postre postre) {
		StringBuilder texto = new StringBuilder();

		texto.append(postre.getNombreProducto()).append(LINEA);

		for (Ingrediente ing : postre.getListaIngredientes()) {
			if (!ing.isActivo()) {
				texto.append(ESPACIO).append(ing.toString().trim()).append(LINEA);
			}
		}

		for (Extra ext : postre.getListaExtras()) {
			if (ext.getCantidadExtra() > 0) {
				texto.append(ESPACIO).append(ext.toString().trim()).append(LINEA);
			}
		}

		return texto;
	}

	public StringBuilder informacionHamburguesa(Hamburguesa hamburguesa) {
		StringBuilder texto = new StringBuilder();

		texto.append(hamburguesa.getNombreProducto()).append(LINEA);

		for (Ingrediente ing : hamburguesa.getListaIngredientes()) {
			if (!ing.isActivo()) {
				texto.append(ESPACIO).append(ing.toString().trim()).append(LINEA);
			}
		}

		for (Extra ext : hamburguesa.getExtras()) {
			if (ext.getCantidadExtra() > 0) {
				texto.append(ESPACIO).append(ext.toString().trim()).append(LINEA);
			}
		}

		return texto;
	}

	public StringBuilder informacionComplemento(Complemento complemento) {
		StringBuilder stb = new StringBuilder();

		stb.append(complemento.getNombreProducto()).append(LINEA);

		for (Salsa salsa : complemento.getSalsas()) {
			stb.append(ESPACIO).append(salsa.toString().trim()).append(LINEA);
		}

		return stb;
	}

	public StringBuilder informacionBebida(Bebida bebida) {
		StringBuilder stb = new StringBuilder();

		stb.append(bebida.getNombreProducto()).append(LINEA);

		if (bebida.getNombreExtra() != null) {
			if (bebida.isExtraActivo() && bebida.getNombreExtra().equalsIgnoreCase("hielo")) {
				stb.append(ESPACIO).append("Sin hielo").append(LINEA);
			} else if (bebida.isExtraActivo() && bebida.getNombreExtra().equalsIgnoreCase("del tiempo")) {
				stb.append(ESPACIO).append("Extra del tiempo").append(LINEA);
			}
		}

		return stb;
	}
}
