package br.com.apistarwars.starwars.model;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/*
 * @author Alexsandre Rodrigues
 * 
 * Classe criada para representar a entidade no banco de dados e tbm o objeto Json
 */

@Document(collection="planeta")
public class Planeta {

	// ID do banco de dados
	@JsonIgnoreProperties(ignoreUnknown = true)
	@Id
	private String idString;
	
	// ID do site swapi.co
	private int id;
	
	// a variavel URL precisou ser criada para poder definir o atributo id (int), pois somente na URL é possível obter o id
	private String url;

	private int qtdFilmes;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String climate;
	
	@NotBlank
	private String terrain;
	
	private ArrayList<String> films = new ArrayList<>();
	
	
	
	public Planeta(String nome, String clima, String terreno, int qtdFilmes) {
		this.name = nome;
		this.climate = clima;
		this.terrain = terreno;
		this.qtdFilmes = qtdFilmes;
	}
	
	public Planeta() {}
	
	
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String clima) {
		this.climate = clima;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terreno) {
		this.terrain = terreno;
	}
	public int getQtdFilmes() {
		return qtdFilmes;
	}
	public void setQtdFilmes(int qtdFilmes) {
		this.qtdFilmes = qtdFilmes;
	}

	public ArrayList<String> getFilms() {
		return films;
	}

	public void setFilms(ArrayList<String> films) {
		
		this.qtdFilmes = films.size();
		this.films = films;
	}
	
	public String getIdString() {
		return idString;
	}

	public void setIdString(String id) {
		this.idString = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		
		// variaveis para o substring
		int inicio = 29;
		int fim = 30;
		
		// a url com 32 digitos tem o id decimal, então é preciso alterar o indice do substring
		if(url.length() == 32) {
			fim = 31;
		}
		
		String idString = url.substring(inicio, fim);		
		
		this.id = Integer.valueOf(idString);
				
	}
	
}
