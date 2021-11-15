
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;


public class SemTestCase {
	private ZonaDeEstacionamiento zona; 
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private EntidadObservadora entidad;
	private App app;
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();	
		inspector = new Inspector();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		entidad = new EntidadObservadora();
		sem.suscribirEntidad(entidad);
		app = new App(1153276406, "ABC123");
		
	}
	
	@Test
	public void seInicioUnEstacionamientoVirutal(){
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		Estacionamiento estacionamietno = zona.estacionamientoDe(app.getPatente());
		Assertions.assertTrue(sem.getEstacionamientos().containsKey(app.getPatente()));
		Assertions.assertEquals(estacionamietno.getPatente(), app.getPatente() );
	}
	
	@Test
	public void seFinalizoUnEstacionamientoVirtual() {
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Assertions.assertFalse( zona.estacionamientoDe(app.getPatente()).esVigente());
	}
	
	@Test
	public void agregarEstacionamientoPorCompra() {
		Assertions.assertTrue( sem.getCompras().isEmpty());
		comercio.generarEstacionamiento("ABC123", 2);
		Assertions.assertFalse( sem.getCompras().isEmpty() );
		Assertions.assertFalse( zona.getEstacionados().isEmpty() );
	}
	
	@Test
	public void agregarCompraPorRecarga() {
		Assertions.assertTrue( sem.getCompras().isEmpty());
		comercio.recargarAplicativo(1564068646, 200);
		Assertions.assertFalse( sem.getCompras().isEmpty());
	}
	
	
}
