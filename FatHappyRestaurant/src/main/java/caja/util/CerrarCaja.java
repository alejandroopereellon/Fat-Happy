package caja.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import auxiliares.crearTicket.TicketBuilder;
import auxiliares.inicioAplicacion.ConfiguracionInicial;
import auxiliares.mostrarMensaje.DialogoMostrarMensajeMetodos;
import auxiliares.singleton.ClasesEstaticas;
import caja.dao.CajasDao;
import caja.dao.CajasDaoHibernateImpl;
import caja.modelo.Caja;
import empleados.util.ActividadEmpleados;
import ventanaPrincipal.InterfazVentanaPrincipalMetodos;

/**
 * Clase que realiza las operaciones necesarias para el cierre correcto de la
 * caja
 *
 * @author Alejandro Perellón López
 */
public class CerrarCaja {
    // Crear el logger

    static Logger logger = LogManager.getLogger(CerrarCaja.class);

    private CajasDao dao = new CajasDaoHibernateImpl();
    private Caja caja = ClasesEstaticas.getCaja();

    File fichero = new File(ConfiguracionInicial.get().getDirectorioLocal() + File.separator + "cierreCaja"
            + File.separator + LocalDate.now() + File.separator + "caja" + caja.getNumeroCaja() + File.separator
            + "sesion" + caja.getNumeroSesion() + File.separator);

    public boolean cerrarCaja() {
        // Pedimos autorizacion del empleado
        // Si no se ha autorizado retornamos false y no se modifica la caja
        if (!new ActividadEmpleados().solicitarPermisos(ClasesEstaticas.getProveedorMensaje().findMessage("CERRARCAJA_MENSAJE_CIERRE") + LocalDateTime.now(), 3)) {
            return false;
        }

        /*
		 * Comprobamos si la caja esta activa actualmente y si no esta cerrada (momento
		 * cierre = null)
         */
        if (caja != null && caja.getMomentoCierre() == null) {
            // Iniciamos el proceso de cierre

            // Establecemos el importe final de la caja
            caja.setImporteFinal(new CalcularOperaciones().calcularTotalOperaciones().add(caja.getImporteInicial()));
            // Establecemos el momento de cierre
            caja.setMomentoCierre(LocalDateTime.now());
            // Establecemos la lista de operaciones
            caja.setListaOperaciones(dao.listarOperaciones());

            // Cerramos la caja en el metodo DAO
            if (dao.cerrarCaja()) {
                logger.info("Se ha cerrado la caja correctamente");
                // Notificamos que se ha cerrado la caja
                new DialogoMostrarMensajeMetodos().mostrarMensaje(ClasesEstaticas.getProveedorMensaje().findMessage("CERRARCAJA_NOTIFICACION_CIERRE_EXITO"));
                // Crear las carpetas si no existen
                fichero.getParentFile().mkdirs();
                // Creamos los documentos de cierre de caja
                almacenarDatosCajaJson();
                crearTicketCaja();

                // Volvemos el objeto caja de singleton en null
                ClasesEstaticas.setCaja(null);
                // Establecemos los datos de la caja cerrada en la ventana principal
                new InterfazVentanaPrincipalMetodos(ConfiguracionInicial.get().getVentanaPrincipal())
                        .configurarPanelCaja();

                return true;
            }
            logger.error("No se ha podido cerrar la caja en el DAO");
        } else {
            logger.error("La caja esta cerrada");
        }
        // Notificamos que se ha cerrado la caja
         new DialogoMostrarMensajeMetodos().mostrarMensaje(ClasesEstaticas.getProveedorMensaje().findMessage("CERRARCAJA_NOTIFICACION_CIERRE_ERROR"));
        return false;
    }

    /**
     * Metodo que genera un ticket de cierre de caja y lo guarda en un archivo
     * de texto. El ticket incluye los datos principales del cierre, empleado,
     * importe y resumen de operaciones.
     */
    private void crearTicketCaja() {
        try {
            File archivo = new File(fichero, "ticket_cierre.txt");

            // Generamos un objeto tiketBuilder
            TicketBuilder ticket = new TicketBuilder();

            ticket.centrar("CIERRE DE CAJA");
            ticket.linea();

            // Mostramos los datos del restaurante, caja y empleado
            ticket.izquierda("Restaurante ID: " + caja.getRestaurante().getIdRestaurante());
            ticket.izquierda("Caja #: " + caja.getNumeroCaja());
            ticket.izquierda("Sesion #: " + caja.getNumeroSesion());
            ticket.izquierda("Empleado: " + caja.getEmpleado().getIdRestauranteEmpleado());

            ticket.linea();

            // Mostramos los tiempos de la apertura y cierre de caja
            ticket.izquierda("Fecha apertura: " + caja.getMomentoApertura());
            ticket.izquierda("Fecha cierre: " + caja.getMomentoCierre());

            ticket.linea();

            // Mostramos los importe de apertura y cierre de caja
            ticket.izquierda("Importe inicial: " + caja.getImporteInicial() + " EUR");
            ticket.izquierda("Importe final:   " + caja.getImporteFinal() + " EUR");

            // Mostramos el numero de operaciones realizadas
            ticket.izquierda("Operaciones realizadas: " + caja.getListaOperaciones().size());

            ticket.linea();
            ticket.centrar("Gracias por su trabajo");

            // Almacenar en archivo
            ticket.almacenarEnArchivo(archivo);

            logger.info("Ticket de cierre de caja generado correctamente en {}", archivo.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Error al generar el ticket de cierre de caja", e);
        }
    }

    /**
     * Metodo que almacena los datos de la {@link Caja} en el fichero local del
     * sistema
     */
    private void almacenarDatosCajaJson() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            mapper.writeValue(new File(fichero + "caja.json"), caja);
        } catch (IOException e) {
            logger.error("Ha ocurrido un error al persistir los datos de la caja en json", e);
        }
    }
}
