package auxiliares.utilidadesGraficas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.net.URL;

public class ImageInserter {

	public void insertImage(String imagePath, JPanel panel) {
		URL imageUrl = getClass().getResource(imagePath);
		if (imageUrl != null) {
			ImageIcon icon = new ImageIcon(imageUrl);
			JLabel label = new JLabel(icon);
			panel.removeAll();
			panel.setLayout(new java.awt.BorderLayout());
			panel.add(label, java.awt.BorderLayout.CENTER);
			panel.revalidate();
			panel.repaint();
		} else {
			System.err.println("No se encontró la imagen en: " + imagePath);
		}
	}
}
