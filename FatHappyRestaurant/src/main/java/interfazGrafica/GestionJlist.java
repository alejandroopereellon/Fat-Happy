/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfazGrafica;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 *
 * @author Alejandro Perellón López
 */
public class GestionJlist {
    
    public static void setMultilineListRenderer(JList<String> jList) {
        jList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea textArea = new JTextArea((String) value);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
                textArea.setBackground(Color.decode("#666666"));
                textArea.setForeground(Color.WHITE);
                textArea.setFont(new java.awt.Font("Segoe UI", 1, 14));

                // Configura el color de fondo y el texto dependiendo de la selección
                if (isSelected) {
                    textArea.setBackground(Color.decode("#58d68d")); // Fondo verde personalizado
                    textArea.setForeground(Color.BLACK);
                }
                
                return textArea;
            }
        });
    }
}
