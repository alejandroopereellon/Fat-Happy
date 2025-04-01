package pedido.util;

import pedido.modelo.Pedido;
import pedido.modelo.OrdenPedido;
import caja.modelo.Caja;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Builder para crear pedidos de forma controlada y escalable.
 */
public class PedidoBuilder {

    // Datos obligatorios o configurables
    private int numeroPedido;
    private OrdenPedido orden;
    private int estadoPedido;
    private Caja caja;

    public PedidoBuilder withNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
        return this;
    }

    public PedidoBuilder withOrden(OrdenPedido orden) {
        this.orden = orden;
        return this;
    }

    public PedidoBuilder withEstado(int estadoPedido) {
        this.estadoPedido = estadoPedido;
        return this;
    }

    public PedidoBuilder withCaja(Caja caja) {
        this.caja = caja;
        return this;
    }

    public Pedido build() {
        Pedido pedido = new Pedido();

        pedido.setNumeroPedido(numeroPedido);
        pedido.setOrden(orden);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setCaja(caja);
        pedido.setFechaHora(LocalDateTime.now());

        String ruta = "/R" + caja.getRestaurante().getIdRestaurante() + "/" +
                LocalDate.now().toString() + "/" + numeroPedido;
        pedido.setRutaPedido(ruta);

        pedido.setDescuento(0);

        // Acciones adicionales tras creación
        new IniciarContadorPedido(pedido).start();
        new CalcularImporte(pedido).restaurarImporte();
        new AlmacenarOrdenPedidoJson(pedido).almacenarOrdenPedido();

        return pedido;
    }
}
