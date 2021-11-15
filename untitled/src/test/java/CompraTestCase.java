import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompraTestCase {
	
	private CompraPorHoraPuntual compraPorHora;
	private ZonaDeEstacionamiento zona; 
	private Inspector inspector; 
	private Comercio comercio;
	private CompraPorRecarga compraPorRecarga;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		inspector = new Inspector();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		compraPorHora = new CompraPorHoraPuntual(2, 0, comercio);
		compraPorRecarga = new CompraPorRecarga(0, comercio, 1153276406, 200);
	}
	@Test
	public void testCuandoUnaCompraPorRecarga() {
		Assertions.assertEquals(compraPorRecarga.getComercio(), comercio);
		Assertions.assertEquals(compraPorRecarga.getNroControl(), 0);
		Assertions.assertEquals(compraPorRecarga.getMonto(), 200);
		Assertions.assertEquals(compraPorRecarga.getNumero() , 1153276406 );
	}

	@Test
	public void testCuandoUnaCompraPorHorasFijasSeInicia() {
		Assertions.assertEquals(2, compraPorHora.getHorasCompradas());
		Assertions.assertEquals(compraPorHora.getNroControl(),0);
		Assertions.assertEquals(compraPorHora.getComercio(),comercio);
		Assertions.assertEquals(compraPorHora.getFecha(), new Date() );
		
	}
	
}
