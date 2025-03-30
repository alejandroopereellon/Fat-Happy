package empleados.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Esta clase se encarga de analizar y almacenar los movimientos de los
 * empleados, por ejemplo los intentos de acceso al sistema
 * 
 * @author Alejandro Perellón López
 */
@Entity
@Table(name = "movimientos_empleados")
public class MovimientosEmpleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_log")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_empleado", nullable = true)
	private Empleado empleado;

	@Column(name = "accion", length = 100)
	private String accion;

	@Column(name = "observaciones", length = 100)
	private String observaciones;

	@Column(name = "resultado")
	private boolean resultado;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora;

	/**
	 * @param Empleado  es el empleado que ha intentado realizar la accion
	 * @param accion    es la descripcion de la accion que se ha intentando realizar
	 * @param resultado es si se ha permitido o no que se realice la accion
	 * @param fechaHora es el momento en el que se realizo la accion
	 */
	public MovimientosEmpleado(Empleado empleado, String accion, String observaciones, boolean resultado) {
		this.empleado = empleado;
		this.observaciones = observaciones;
		this.accion = accion;
		this.resultado = resultado;
		this.fechaHora = LocalDateTime.now();
	}

	public MovimientosEmpleado() {
	}

}
