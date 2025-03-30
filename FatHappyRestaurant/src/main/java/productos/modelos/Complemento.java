package productos.modelos;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un complemento que puede ser parte de un producto. Un
 * complemento tiene un tamaño (puede ser nulo), número de salsas y una lista de
 * salsas asociadas.
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "complementos")
@PrimaryKeyJoinColumn(name = "codigo")
public class Complemento extends Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3556051122005475200L;

	@Column(name = "tamano", nullable = true)
	private int tamano;

	@Column(name = "numeroSalsas", nullable = false)
	private int numeroSalsas;

	@Transient
	private List<Salsa> salsas = new ArrayList<>();

	// Constructor vacío para Hibernate
	public Complemento() {
	}

	/**
	 * Constructor para crear un objeto complemento.
	 * 
	 * este constructor crea un objeto de la clase complemento con todos los
	 * parametros proporcionados.
	 * 
	 * Un complemento es un tipo especial de producto que tiene un tamano, un numero
	 * de salsas y una lista de salsas que se pueden asociar durante la creacion del
	 * producto
	 *
	 * @param codigo                 el codigo unico del complemento
	 * @param nombreProducto         el nombre del complemento
	 * @param categoria              la categoria a la que pertenece el complemento
	 * @param tipoProducto           el tipo de producto
	 * @param precioVenta            el precio de venta del complemento.
	 * @param costeEmpresa           el coste de produccion del complemento
	 * @param productoActivo         indica si el producto esta activo o inactivo.
	 * @param productoPromocionado   indica si el complemento esta promocionado.
	 * @param opcionDescuento        indica si el complemento puede tener un
	 *                               descuento aplicado.
	 * @param imagenProducto64       la imagen del complemento en tamano reducido
	 *                               (64px), en formato base64.
	 * @param imagenProducto128      la imagen del complemento en tamano mediano
	 *                               (128px), en formato base64.
	 * @param imagenProductoOriginal la imagen del complemento en tamano original.
	 * @param tamano                 el tamano del complemento (por ejemplo,
	 *                               "pequeno", "mediano", "grande"). puede ser null
	 *                               si no tiene un tamano especifico.
	 * @param numeroSalsas           el numero maximo de salsas permitidas para el
	 *                               complemento. este campo ayuda a definir la
	 *                               capacidad del complemento para acompañar
	 *                               salsas.
	 * @param salsas                 una lista de objetos salsa asociadas al
	 *                               complemento.
	 */

	public Complemento(int codigo, String nombreProducto, String categoria, String tipoProducto, BigDecimal precioVenta,
			BigDecimal costeEmpresa, boolean productoActivo, boolean productoPromocionado, boolean opcionDescuento,
			String imagenProducto64, String imagenProducto128, String imagenProductoOriginal, int tamano,
			int numeroSalsas, List<Salsa> salsas) {
		super(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo,
				productoPromocionado, opcionDescuento, imagenProducto64, imagenProducto128, imagenProductoOriginal);
		this.tamano = tamano;
		this.numeroSalsas = numeroSalsas;
		this.salsas = salsas;
	}

	// Getters y Setters

	public List<Salsa> getSalsas() {
		return salsas;
	}

	public void setSalsas(List<Salsa> salsas) {
		this.salsas = salsas;
	}

	public int getTamano() {
		return tamano;
	}

	public int getNumeroSalsas() {
		return numeroSalsas;
	}

	// toString
	@Override
	public String toString() {
		boolean noMasSalsas = false;
		String datos = getNombreProducto();
		// Mientras salsas no sea empty

		// Recorremos todas las salsas que existan
		for (Salsa sal : salsas) {
			if (sal.getCodigo() == 20050509) {
				noMasSalsas = true;
				break;
			}
			datos = datos + System.lineSeparator() + sal.toString();
		}
		/**
		 * Si la cantidad de salsas seleccionadas es menor que el numero de salsas
		 * disponibles y quiere mas salsas se mostrará la opcion de seleccionar salsa el
		 * numero de veces que no se hayan seleccionado salsas
		 */
		if ((salsas.size() < numeroSalsas) && !noMasSalsas) {
			for (int i = 0; i < (numeroSalsas - salsas.size()); i++) {
				datos = datos + System.lineSeparator() + "Escoger salsa";
			}
		}
		return datos;
	}

}
