package br.com.apistarwars.starwars.integrador;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.apistarwars.starwars.model.Planeta;

public class ConexaoStarwar {

	private String URL = "https://swapi.co/api/planets/";

	public Planeta getPlanetById(int id) throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entity = criarEntity();

		ResponseEntity<Planeta> response = restTemplate.exchange( URL + id, HttpMethod.GET,
				entity, Planeta.class);

		return response.getBody();
	}


	public Planeta getPlanetByName(String name) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = criarEntity();
		
		name = name.replaceAll(" ", "+");
		

		ResponseEntity<PlanetaIntegrador> response = restTemplate.exchange( URL + "?search=" + name , HttpMethod.GET, entity,
				PlanetaIntegrador.class);
		
		if(response.getBody().getResults().size() < 0) {
			throw new IllegalArgumentException("Não existe planeta com o nome informado!");
		}

		return response.getBody().getResults().get(0);
	}
	
	private HttpEntity<String> criarEntity() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
}
