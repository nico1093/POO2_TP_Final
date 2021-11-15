

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InspectorTestCase {
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
		
		inspector.setZonaEncargada(zona);
		
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
		app = new App(1145648612, "AB 123 CD");
	}
	

	@Test
	public void inspectorIncial() {
		sem = SEM.getInstance();
		sem.reset();	
		inspector = new Inspector();
		Assertions.assertEquals(inspector.getZonaEncargada(),zona);
		Assertions.assertEquals(inspector.getSem(), sem );
	}
	
	@Test
	public void inspectorInspeccionaUnaPatente() {
		sem = SEM.getInstance();
		sem.reset();	
		
		inspector = new Inspector();
		
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		
		inspector.setZonaEncargada(zona);
		
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		
		app = new App(1145648612, "AB 123 CD");
		app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1145648612, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		inspector.inspeccionarEstacionamiento("AB 123 CD");
		Assertions.assertFalse(sem.getInfracciones().isEmpty());
	}
}
