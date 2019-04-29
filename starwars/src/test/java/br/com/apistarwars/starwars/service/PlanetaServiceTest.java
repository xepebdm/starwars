package br.com.apistarwars.starwars.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.apistarwars.starwars.model.Planeta;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetaServiceTest {

	@Autowired
	private PlanetaService service;
	
	@Test
	public void testSalvaUmPlaneta() throws Exception {
		Planeta planeta = new Planeta();
		planeta.setName("Yavin IV");
		planeta.setTerrain("jungle, rainforests");
		planeta.setClimate("temperate, tropical");
		
		service.save(planeta);
		
		Planeta terra = service.findByName("Yavin IV");
		
		assertEquals(terra.getName(), planeta.getName());
	}
	
	@Test
	public void testObterTodosPeloNome() throws Exception {
		Planeta planet = service.getPlanetSWApiById(13);
		
		service.save(planet);
		
		String nomeNovo = "Mustafar";
		
		Planeta mustafar = service.findByName(nomeNovo);
		
		assertEquals(planet.getClimate(), mustafar.getClimate());
	}
	
	@Test
	public void testObterPorId() throws Exception{
		Planeta planeta = new Planeta();
		planeta.setName("Endor");
		planeta.setClimate("temperate");
		planeta.setTerrain("forests, mountains, lakes");
		
		service.save(planeta);
		
		Planeta planetaId = service.findById(7);
		
		assertEquals("Endor", planetaId.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemovePorId() throws Exception{
		Planeta planeta = new Planeta();
		planeta.setName("Shili");
		planeta.setClimate("temperate");
		planeta.setTerrain("cities, savannahs, seas, plains");
		
		service.save(planeta);
		
		service.removePorId(58);
		
		service.findById(58);
		
		
	}
	
	@Test
	public void testeUpdate() throws Exception{
		
		Planeta planeta = new Planeta();
		planeta.setName("Shili");
		planeta.setClimate("temperate");
		planeta.setTerrain("cities, savannahs, seas, plains");
		
		service.save(planeta);
		
		Planeta planeta2 = service.findByName("Shili");
		
		planeta2.setName("Novo nome");
		
		service.update(planeta2);
		
		Planeta novo = service.findById(58);
		
		assertEquals("Novo nome", novo.getName());
	}
	
	@After
	public void rollback() {
		service.removeAll();
	}
	
	

}
