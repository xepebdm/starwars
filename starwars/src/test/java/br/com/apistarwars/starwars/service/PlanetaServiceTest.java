package br.com.apistarwars.starwars.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.apistarwars.starwars.StarwarsApplication;
import br.com.apistarwars.starwars.model.Planeta;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {StarwarsApplication.class})
public class PlanetaServiceTest {

	@Autowired
	private PlanetaService service;
	
	@Test
	public void testSalvaUmPlaneta() {
		Planeta planeta = new Planeta();
		planeta.setName("Terra");
		planeta.setTerrain("calcario, arenoso");
		
		service.save(planeta);
		
		Planeta terra = service.findByName("terra");
		
		assertEquals(terra.getName(), planeta.getName());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveUmPorNome() throws Exception{
		Planeta planeta = service.obterUmViaSwapi(12);
		
		String nome = "Utapau";
		
		service.removerPorNome(nome);
		service.findByName(planeta.getName());
	}
	
	@Test
	public void testSalvaTodosViaApi() throws Exception{
		List<Planeta> planetas = service.obterTodosViaSwapi();
		
		service.saveAll(planetas);
		
		List<Planeta> planetas2 = service.obterTodosViaBanco();
		
		assertEquals(planetas.size(), planetas2.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveTodosPorTerreno() throws Exception{
		
		service.obterTodosViaSwapi();
		service.removeAllPorTerreno("savanna");
		
		service.findByTerrain("savanna");
	}
	
	@Test
	public void testObterTodosPeloNome() throws Exception {
		Planeta terra = service.obterUmViaSwapi(13);
		
		service.save(terra);
		
		String nomeNovo = "Mustafar";
		
		Planeta mustafar = service.findByName(nomeNovo);
		
		assertEquals(terra.getClimate(), mustafar.getClimate());
	}
	
	@Test
	public void testObterPorId() throws Exception{
		Planeta planeta = new Planeta();
		planeta.setName("Planeta teste por Id");
		planeta.setClimate("hot");
		planeta.setTerrain("gasoso");
		
		service.save(planeta);
		
		Planeta planetaId = service.findById(planeta.getIdString());
		
		assertEquals("Planeta teste por Id", planetaId.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemovePorId() throws Exception{
		Planeta planeta = new Planeta();
		planeta.setName("Planeta teste por Id");
		planeta.setClimate("hot");
		planeta.setTerrain("gasoso");
		
		service.save(planeta);
		
		service.removePorId(planeta.getIdString());
		
		service.findById(planeta.getIdString());
		
		
	}
	
	@Test
	public void testeUpdate() throws Exception{
		Planeta planeta = service.findByName("Eriadu");
		
		planeta.setName("Novo nome");
		
		service.update(planeta);
		
		Planeta novo = service.findById("5c6a016e8e0cd81348cf0a32");
		
		assertEquals("Novo nome", novo.getName());
	}
	
//	@After
//	public void rollback() {
//		service.removeAll();
//	}
	
	

}
