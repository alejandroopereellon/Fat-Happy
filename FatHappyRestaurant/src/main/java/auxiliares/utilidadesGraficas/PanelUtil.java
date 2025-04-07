package auxiliares.utilidadesGraficas;

import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase utilitaria para realizar operaciones comunes sobre los jPanel. Incluye
 * metodos para actualizar, limpiar y añadir objetos en el jPanel
 */
public class PanelUtil {

	// Iniciamos el logger
	private static final Logger logger = LogManager.getLogger(PanelUtil.class);

	/**
	 * Fuerza la actualización visual de un panel. Se utiliza para aplicar cambios
	 * tras modificar componentes.
	 *
	 * @param panel Panel que se desea actualizar
	 */
	public void actualizarPanel(JPanel panel) {
		if (panel == null) {
			logger.warn("El panel a actualizar es nulo");
			return;
		}

		panel.revalidate();
		panel.repaint();
		logger.info("Panel actualizado correctamente");
	}

	/**
	 * Elimina todos los componentes de un panel y lo actualiza.
	 *
	 * @param panel Panel que se desea limpiar
	 */
	public static void limpiarPanel(JPanel panel) {
		if (panel == null) {
			logger.warn("El panel a limpiar es nulo");
			return;
		}

		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		logger.info("Panel limpiado correctamente");
	}
}
