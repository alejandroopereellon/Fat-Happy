package interfazGrafica;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import gestionBaseDatos.CrearConexion;
import models.Producto;

public class GestionProductos {

    /**
     * Este metodo separa por categorias los {@link Producto} para ello solicita
     * el listado de productos completo y la categoria, realiza un bucle por
     * todo el listado completo y busca los productos que conntengan la
     * categoria introducida, si el producto coincide con la categoria va
     *
     * @param listaProductos lista de hamburguesas que se van a utilizar para
     * dividir
     * @param categoria es la categoria que nos interesa dividir
     * @return listado de hamburguesas que pertenecen a la categoria introducida
     */
    public List<Producto> listarProductosPorCategoria(List<Producto> listaProductos, String categoria) {
        List<Producto> listaProductoCategorizada = new ArrayList<>();
        for (Producto pro : listaProductos) {
            if (pro.getTipo_producto().equalsIgnoreCase(categoria)) {
                listaProductoCategorizada.add(pro);
            }
        }
        return listaProductoCategorizada;
    }

    /**
     * Este metodo realiza una consulta a la base de datos con todos los
     * productos, con el while generamos nuevos objetos de producto y lo
     * introducimos en el listado de productos
     *
     * @param nombreCategoria es el nombre de la categoria que se va a mostrar
     * @return listado de productos
     */
    public List<Producto> consultaListadoProductosBBDD(String nombreCategoria) {
        List<Producto> listaProductos = new ArrayList<>();

        String consulta = "SELECT p.codigo, p.nombre, p.categoria, p.tipo, p.precio, p.costeEmpresa, p.productoActivo, p.opcionDescuento, p.imagenProducto FROM productos p where categoria = ?";

        try (Connection conexion = new CrearConexion().iniciar(); PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, nombreCategoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Almacenamos los valores de las columnas en variables
                int codigo = rs.getInt("codigo");
                String nombreProducto = rs.getString("nombre");
                String categoria = rs.getString("categoria");
                String tipoProducto = rs.getString("tipo");
                BigDecimal precioVenta = rs.getBigDecimal("precio");
                BigDecimal costeEmpresa = rs.getBigDecimal("costeEmpresa");
                boolean productoActivo = rs.getBoolean("productoActivo");
                boolean opcionDescuento = rs.getBoolean("opcionDescuento");
                File imagenProducto = new File(rs.getString("imagenProducto"));

                //Creamos el objeto producto con toda la informacion
                listaProductos.add(new Producto(codigo, nombreProducto, categoria, tipoProducto, precioVenta, costeEmpresa, productoActivo, opcionDescuento, imagenProducto));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    //TODO TENEMOS QUE SEPARARLO Y OPTIMIZARLO
    /**
     *
     * String consulta = "SELECT p.codigo, p.nombre, p.categoria, p.tipo,
     * p.precio, p.costeEmpresa, p.productoActivo, p.opcionDescuento,
     * p.imagenProducto, h.opcionMenu, h.ingrediente1, h.ingrediente2,
     * h.ingrediente3, h.ingrediente4, h.ingrediente5, h.ingrediente6,
     * h.ingrediente7, h.extra1, h.extra2, h.extra3 FROM productos p LEFT JOIN
     * hamburguesas h ON p.codigo = h.codigo where categoria = ?";
     *
     *
     *
     * List<Ingrediente> listaIngredientes =
     * obtenerIngredientesHamburguesas(rs);
     *
     * List<Extra> listaExtras = obtenerExtrasHamburguesas(rs);
     *
     * // Creamos el objeto hamburguesa con toda la informacion
     * listaProductos.add(new Hamburguesa(codigo, nombreProducto, categoria,
     * tipoProducto, precioVenta, costeEmpresa, productoActivo, opcionDescuento,
     * imagenProducto, opcionMenu, listaIngredientes, listaExtras));
     */
}
