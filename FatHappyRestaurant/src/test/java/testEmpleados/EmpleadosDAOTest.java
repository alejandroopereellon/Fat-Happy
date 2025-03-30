package testEmpleados;

import static org.junit.jupiter.api.Assertions.*;

import empleados.dao.EmpleadoDaoHibernateImpl;
import empleados.dao.EmpleadosDao;
import empleados.modelo.Empleado;

import org.junit.jupiter.api.Test;

class EmpleadosDAOTest {

	@Test
	void testObtenerEmpleadoExistente() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		Empleado emp = dao.obtenerEmpleado(198); // Este debe existir
		assertNotNull(emp);
		assertEquals("Antonio", emp.getNombre());
	}

	@Test
	void testObtenerEmpleadoInexistente() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		Empleado emp = dao.obtenerEmpleado(99999); // No debería existir
		assertNull(emp);
	}

	@Test
	void testEmpleadoExiste() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		assertTrue(dao.comprobarEmpleadoExiste(198)); // Debe de existir
	}

	@Test
	void testEmpleadoNoExiste() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		assertFalse(dao.comprobarEmpleadoExiste(-1)); // no debe de existir
	}

	@Test
	void testEmpleadoAutorizado() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		boolean autorizado = dao.pedirAutorizacionEmpleado(198, 5, "Acceso a configuración");
		assertTrue(autorizado);
	}

	@Test
	void testEmpleadoNoAutorizadoPorPermisos() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		boolean autorizado = dao.pedirAutorizacionEmpleado(103, 3, "Intento de acceso a caja");
		assertFalse(autorizado);
	}

	@Test
	void testEmpleadoInexistenteEnAutorizacion() {
		EmpleadosDao dao = new EmpleadoDaoHibernateImpl(0);
		boolean autorizado = dao.pedirAutorizacionEmpleado(99999, 3, "Intento fallido");
		assertFalse(autorizado);
	}

}
