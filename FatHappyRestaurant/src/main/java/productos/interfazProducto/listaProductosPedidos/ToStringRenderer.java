package productos.interfazProducto.listaProductosPedidos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import auxiliares.utilidadesGraficas.coloresInterfaz.ColoresInterfaz;

public class ToStringRenderer<T> implements ListCellRenderer<T> {
	private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	private static final Color GRIS_SEPARADOR = new Color(0xD1D1D1);

	@Override
	public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// 1) Creamos la etiqueta base
		JLabel etiqueta = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);

		// 2) Multilínea y texto
		String texto = value.toString().replace("\n", "<br>");
		etiqueta.setText("<html>" + texto + "</html>");

		// 3) Ya no pinta fondo ella misma…
		etiqueta.setOpaque(false);
		etiqueta.setForeground(isSelected ? Color.WHITE : Color.BLACK);

		// 4) Borde + padding
		Border padding = new EmptyBorder(8, 12, 8, 12);
		Border separator = BorderFactory.createMatteBorder(0, 0, 1, 0, GRIS_SEPARADOR);
		etiqueta.setBorder(new CompoundBorder(separator, padding));

		// 5) Panel que pinta el fondo dorado o zebra
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(etiqueta, BorderLayout.CENTER);
		panel.setOpaque(true);
		if (isSelected) {
			panel.setBackground(ColoresInterfaz.PRIMARIO_DORADO);
		} else {
			panel.setBackground((index & 1) == 0 ? Color.WHITE : ColoresInterfaz.SECUNDARIO_CHAMPAGNE);
		}

		return panel;
	}
}
