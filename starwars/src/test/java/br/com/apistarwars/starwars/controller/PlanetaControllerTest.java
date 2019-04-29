package br.com.apistarwars.starwars.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.apistarwars.starwars.model.Planeta;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaControllerTest {

	@Autowired
	private MockMvc mock;

	private Planeta planeta;
	@Before
	public void setUp() {
		planeta = new Planeta();
		planeta.setName("Alderaan");
		planeta.setClimate("temperate");
		planeta.setTerrain("grasslands, mountains");
		planeta.setUrl( "https://swapi.co/api/planets/2/");
	}

	@Test
	public void testUrlPrincipal() throws Exception {
		mock.perform(get("/planets/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testAdicionaPlanetaViaJson() throws Exception {
		mock.perform(post("/planets/").content(asJsonString(planeta)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Alderaan"));
	}

	@Test
	public void testRemovePorId() throws Exception {

		mock.perform(post("/planets/").content(asJsonString(planeta)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Alderaan")).andReturn();

		mock.perform(delete("/planets/{id}", "2")).andExpect(status().isOk());
	}

	@Test
	public void testeBuscarEAdicionarPorNome() throws Exception {

		mock.perform(get("/planets/name/{name}", "Endor").param("name", "Endor"))
				.andExpect(jsonPath("$.name").value("Endor")).andExpect(status().isOk());
	}

	@Test
	public void testPlanetaNotFound() throws Exception {
		mock.perform(get("/{id}", "1")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testeNotFoundBuscarPorNome() throws Exception {

		mock.perform(get("/planets/name/{name}", "Terra")).andExpect(status().is4xxClientError());
	}
	
	private String asJsonString(final Object obj) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonContent = mapper.writeValueAsString(obj);

			return jsonContent;

		} catch (Exception e) {

			throw new RuntimeException(e);

		}
	}

}
