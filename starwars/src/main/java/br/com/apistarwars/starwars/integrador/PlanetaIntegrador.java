package br.com.apistarwars.starwars.integrador;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.apistarwars.starwars.model.Planeta;

public class PlanetaIntegrador {
	
	// anotação para ignorar atributos nao mapeados
	@JsonIgnoreProperties(ignoreUnknown = true)

	private ArrayList<Planeta> results = new ArrayList<Planeta>();
	
	// URL seguinte, caso exista, é necessário para poder vasculhar todo o site swapi.co
	private String next;


	public ArrayList<Planeta> getResults() {
		return results;
	}

	public void setResults(ArrayList<Planeta> results) {
		this.results = results;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

}
