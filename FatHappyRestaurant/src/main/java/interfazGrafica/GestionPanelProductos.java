package interfazGrafica;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;

import models.Producto;

/**
 * Este metodo geestiona el panel de productos, en este caso la principal
 * funcion de esta clase es la insercion de los productos en el
 * {@link panelProductos}
 *
 * @author Alejandro Perellón López
 */
public class GestionPanelProductos {

    /**
     * Este metodo añade los {@link Producto} en el {@link JPanel} introducido
     * en los parametros del metodo, para ello primero establece la disposicion
     * de los elementos, a continuacion elimina todos los elementos del panel en
     * caso de que haya algun elemento residuo
     *
     * A continuacion hace un bucle para introducir los {@link Producto} en el
     * panel
     *
     * @param panel es el panel en el que se van a introducir los productos y
     * los cuales van a mostrar toda la informacion
     * @param listaProductos lista de productos que se van a mostrar en la
     * interfaz
     * @param panelPedido es el panel de pedido que se utilizar para
     * realizarmodificaciones en el
     */
    protected void mostrarProductosPanel(JPanel panel, List<Producto> listaProductos, PanelPedido panelPedido) {
        //Vaciamos el panel principal
        panelPedido.getPanelProductos().removeAll();
        panelPedido.getPanelProductos().revalidate();
        panelPedido.getPanelProductos().repaint();

        // Establecemos 5 productos por fila
        panel.setLayout(new GridLayout(0, 3));

        //panel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas con espaciado
        // Vaciamos el panel de productos
        panel.removeAll();

        // Anadimos cada elemento en el panel
        for (Producto producto : listaProductos) {
            panel.add(new CasillaProducto(producto, panelPedido));
        }
    }

}
