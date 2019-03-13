package br.com.apistarwars.starwars.integrador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.apistarwars.starwars.model.Planeta;

public class ConexaoStarwar {

	private String URL = "https://swapi.co/api/planets/";

	private List<Planeta> planetas = new ArrayList<>();

	// busca todos os planetas do site swapi.co
	public List<Planeta> getAllPlanetForSwapi() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = criarEntity();

		ResponseEntity<PlanetaIntegrador> response = restTemplate.exchange(URL, HttpMethod.GET, entity,
				PlanetaIntegrador.class);

		// se ainda existir url seguinte ao endereco atual o metodo fara recursividade
		// para continuar populando a lista
		if (response.getBody().getNext() != null) {

			URL = response.getBody().getNext();
			adicionarPlanetaNaLista(response.getBody().getResults());

			return getAllPlanetForSwapi();

		} else {
			adicionarPlanetaNaLista(response.getBody().getResults());
		}
		return planetas;

	}

	// obter o planeta do site swapi.co de acordo com o id
	public Planeta getPlanetById(int id) throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = criarEntity();

		ResponseEntity<Planeta> response = restTemplate.exchange("https://swapi.co/api/planets/" + id, HttpMethod.GET,
				entity, Planeta.class);

		return response.getBody();
	}

	private void adicionarPlanetaNaLista(ArrayList<Planeta> pList) {

		for (Planeta planeta : pList) {
			planetas.add(planeta);
		}
	}

	private HttpEntity<String> criarEntity() {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
}
