package models.Dao;

import java.util.List;

import models.Bebida;
import models.Complemento;
import models.Extra;
import models.Hamburguesa;
import models.Ingrediente;
import models.Postre;
import models.Producto;
import models.Salsa;

public interface ProductosDAO {
	/**
	 * Metodo que retorna un listado completo de {@link Producto}
	 * 
	 * @return lista de todos lo {@link Producto} de la base de datos
	 */
	public List<Producto> listarProductos();

	/**
	 * Este metodo retorna la {@link Bebida} solicitada a traves de su id
	 * 
	 * @param id es el ID de la {@link Bebida}
	 * @return {@link Bebida} desde la base de datos
	 */
	public Bebida obtenerBebida(int id);

	/**
	 * Este metodo retorna el {@link Complemento} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Complemento}
	 * @return {@link Complemento} desde la base de datos
	 */
	public Complemento obtenerComplemento(int id);

	/**
	 * Este metodo retorna el {@link Extra} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Extra}
	 * @return {@link Extra} desde la base de datos
	 */
	public Extra obtenerExtra(int id);

	/**
	 * Este metodo retorna la {@link Hamburguesa} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Hamburguesa}
	 * @return {@link Hamburguesa} desde la base de datos
	 */
	public Hamburguesa obtenerHamburguesa(int id);

	/**
	 * Este metodo retorna el {@link Postre} solicitado a traves de su id
	 * 
	 * @param id es el ID del {@link Postre}
	 * @return {@link Postre} desde la base de datos
	 */
	public Postre obtenerPostre(int id);

	/**
	 * Este metodo retorna la {@link Producto} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Producto}
	 * @return {@link Producto} desde la base de datos
	 */
	public Producto obtenerProducto(int id);

	/**
	 * Este metodo retorna la {@link Salsa} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Salsa}
	 * @return {@link Salsa} desde la base de datos
	 */
	public Salsa obtenerSalsa(int id);

	/**
	 * Este metodo retorna la {@link Ingrediente} solicitada a traves de su id
	 * 
	 * @param id es el ID del {@link Ingrediente}
	 * @return {@link Ingrediente} desde la base de datos
	 */
	public Ingrediente obtenerIngrediente(int id);

}
