package restaurante.dao;

import restaurante.modelo.Restaurante;

public interface RestauranteDao {

	/**
	 * Metodo que recupera de la base de datos el restaurante solicitado mediante el
	 * ID
	 * 
	 * @param id es el id del restaurante que va a manejar el sistema
	 * @return objeto {@link Restaurante} con los datos del restaurante
	 */
	public Restaurante obtenerRestaurante(int id);
}
