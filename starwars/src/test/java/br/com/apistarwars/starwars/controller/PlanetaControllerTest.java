package br.com.apistarwars.starwars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaControllerTest {

	@Autowired
	private MockMvc mock;

	private String planetaJson = "{ \"name\": \"Alderaan\"}";

	@Test
	public void testUrlPrincipal() throws Exception {
		mock.perform(get("/planets/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testAdicionaPlanetaViaJson() throws Exception {
		mock.perform(post("/planets/").contentType(MediaType.APPLICATION_JSON).content(planetaJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Alderaan"));
	}

	@Test
	public void testRemovePorId() throws Exception {
		
		mock.perform(post("/planets/").contentType(MediaType.APPLICATION_JSON).content(planetaJson))
				.andExpect(jsonPath("$.name").value("Alderaan")).andReturn();

		mock.perform(delete("/planets/{id}", "2").param("id", "2")).andExpect(status().isOk());
	}

	@Test
	public void testeBuscarEAdicionarPorNome() throws Exception {

		mock.perform(get("/planets/name/{name}", "Endor")
				.param("name", "Endor"))
			.andExpect(jsonPath("$.name").value("Endor"))
				.andExpect(status().isOk());
	}
	
}
