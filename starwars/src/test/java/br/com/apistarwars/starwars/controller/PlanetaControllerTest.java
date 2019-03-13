package br.com.apistarwars.starwars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaControllerTest {

	@Autowired
	private MockMvc mock;
	
	private String planetaJson = "{" + 
			"        \"idString\": \"5c8672886d69773d980d340e\"," + 
			"        \"id\": 2,\n" + 
			"        \"qtdFilmes\": 2," + 
			"        \"name\": \"Alderaan\"," + 
			"        \"climate\": \"temperate\"," + 
			"        \"terrain\": \"grasslands, mountains\"," + 
			"        \"films\": [\n" + 
			"            \"https://swapi.co/api/films/6/\"," + 
			"            \"https://swapi.co/api/films/1/\"" + 
			"        ]" + 
			"    }";
	
	private String planetaJsonNovo = "{" + 
			"        \"idString\": \"5c8672886d69773d980d340e\"," + 
			"        \"id\": 2,\n" + 
			"        \"qtdFilmes\": 2," + 
			"        \"name\": \"Alderaan Novo\"," + 
			"        \"climate\": \"temperate\"," + 
			"        \"terrain\": \"grasslands, mountains\"," + 
			"        \"films\": [\n" + 
			"            \"https://swapi.co/api/films/6/\"," + 
			"            \"https://swapi.co/api/films/1/\"" + 
			"        ]" + 
			"    }";
	
	
	@Test
	public void testUrlHome() throws Exception {
		this.mock.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void testAdicionaPlaneta() throws Exception {
		mock.perform(post("/adicionar").param("planetaJson", planetaJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Alderaan"));
	}
	
	@Test
	public void testRemovePorId() throws Exception{
		mock.perform(post("/adicionar").param("planetaJson", planetaJson))
		.andExpect(jsonPath("$.name").value("Alderaan"))
		.andReturn();
		
		mock.perform(delete("/deletar").param("id", "5c8672886d69773d980d340e"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testEditarPlaneta() throws Exception{
		mock.perform(post("/adicionar").param("planetaJson", planetaJson))
		.andExpect(jsonPath("$.name").value("Alderaan"))
		.andReturn();
		
		mock.perform(put("/editar").param("planetaJson", planetaJsonNovo))
		.andExpect(jsonPath("$.name").value("Alderaan Novo"))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testAddAll() throws Exception{
		mock.perform(post("/addAll")).andExpect(status().isOk());
	}
	
	@Test
	public void testPesquisarPorIdNoBanco() throws Exception{
		mock.perform(post("/addAll")).andReturn();
		
		mock.perform(get("/pesquisarPorId").param("id", "5"))
		.andExpect(jsonPath("$.name").value("Dagobah"))
		.andExpect(jsonPath("$.climate").value("murky"))
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void testePesquisarPorNomeNoBanco() throws Exception{
		mock.perform(post("/addAll")).andReturn();
		
		String nome = "Alderaan";
		
		mock.perform(get("/pesquisarPorNome").param("nome", nome))
		.andExpect(jsonPath("$.terrain").value("grasslands, mountains"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testeObterViaIdPorSwapi() throws Exception{
		mock.perform(get("/pesquisarPorIdViaSwapi").param("id", "10"))
		.andExpect(jsonPath("$.name").value("Kamino"))
		.andExpect(jsonPath("$.terrain").value("ocean"))
		.andExpect(status().isOk());
	}
	
	
	@After
	public void removeAll() throws Exception {
		mock.perform(delete("/removeAll"))
		.andExpect(status().is2xxSuccessful());
	}

}
