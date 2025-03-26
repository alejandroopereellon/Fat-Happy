package interfazGrafica;

import models.Complemento;
import models.Hamburguesa;
import models.Producto;
import models.hamburguesas.GestionHamburguesas_old;

/**
 * Este metodo se encarga de gestionar el panel de pedido, entre las opciones
 * permite:
 *
 * La modificacion y actualizacion del jList
 *
 * Obtencion de las herencias de los objetos
 *
 * Comprobar si un producto se puede editar o no
 *
 * @author Alejandro Perellón López
 */
public class GestionPanelPedido {

    /**
     * Este metodo permite añadir un elemento en el {@link  JList} y en el
     * listado de los productos
     *
     * @param pro es el producto que se va a añadir
     * @param panelPedido es el {@link PanelPedido} en el que se van a realizar
     * las modificaciones
     */
    protected void actualizarListaPedido(Producto pro, PanelPedido panelPedido) {
        //Obtenemos el producto final desde la base de datos
        panelPedido.getListaPedido().add(new GestionPanelPedido().obtenerProductoHerencia(pro));
        // Añadimos el producto en el modelo de lista
        panelPedido.getModelo().addElement(pro.toString());
        //Seleccionamos el ultimo elemento de lista
        panelPedido.getjList1().setSelectedIndex(panelPedido.getModelo().getSize() - 1);
        //Comprobamos si el ultimo elemento tiene edicion disponible
        comprobarEdicionProductos(panelPedido);
    }

    /**
     * Este metodo busca en el producto si en la herencia coincide con algun
     * tipo de producto a traves de la categoria del producto, en caso de que la
     * categoria coincida con alguna categoria vamos a retornar el objeto de
     * herencia
     *
     * @param pro es el producto que se va a retornar
     * @return herencia de {@link Producto} (hamburguesa, complemento, bebidas,
     * postres)
     */
    protected Producto obtenerProductoHerencia(Producto pro) {

        switch (pro.getCategoria().toLowerCase()) {
            case "hamburguesa":
                return new GestionHamburguesas_old().obtenerHamburguesaBBDD(pro);
            case "complemento":
            //Introducimos el complemento
            default:
                return pro;
        }
    }

    /**
     * Este metodo comprueba si es posible la edicion del producto dando la
     * posibilidad de la activacion del producto de edicion.
     *
     * Para ello comprueba si el objeto seleccionado contiene ingredientes o
     * extras, en caso de contener una de las dos opciones se va a activar el
     * boton de edicion y se permitira la edicion del produco
     *
     * @param panelPedido es el {@link PanelPedido} en el que se van a realizar
     * las modificaciones
     */
    protected void comprobarEdicionProductos(PanelPedido panelPedido) {
        //Previamente deshabilitamos el boton para evitar que se active sin ser posible
        panelPedido.getBotonEditar().setEnabled(false);
        //Obtenemos el objeto producto seleccionado del jlist del panelpedido
        Producto pro = panelPedido.getListaPedido().get(panelPedido.getjList1().getSelectedIndex());
        //Comprobamos producto es una hamburguesa
        if (pro instanceof Hamburguesa hamburguesa) {
            //Si el producto es una hamburguesa y tiene ingredientes o extras se activa la edicion del produco
            if (!hamburguesa.getIngredientes().isEmpty() || !hamburguesa.getExtras().isEmpty()) {
                panelPedido.getBotonEditar().setEnabled(true);
            }
        } //Comprobamos si producto es un complemento
        else if (pro instanceof Complemento complemento) {
            //Si el producto es un complemento etc...
            //TODO hacer el resto de los ejercicios
        }
    }
}
