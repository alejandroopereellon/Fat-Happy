package productos.interfazProducto.listaProductosPedidos;

import javax.swing.*;

import pedido.interfazPedido.PanelPedido;

import java.awt.*;

/**
 * Clase utilizada en el {@link JList} del {@link PanelPedido} para poder
 * mostrar de manera correcta el contenido del tostring del los elementos
 */
public class ToStringRenderer extends JTextArea implements ListCellRenderer<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ToStringRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
		setFont(new Font("Segoe UI", Font.PLAIN, 14));
		setMargin(new Insets(4, 6, 4, 6)); // margen interno para que respire
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		// Establecer el texto tal cual el toString()
		setText(value.toString());

		// Forzar ajuste al ancho del JList
		setSize(list.getWidth(), Short.MAX_VALUE); // IMPORTANTE
		int alturaNecesaria = getPreferredSize().height;
		setPreferredSize(new Dimension(list.getWidth(), alturaNecesaria));

		// Colores según selección
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
}
