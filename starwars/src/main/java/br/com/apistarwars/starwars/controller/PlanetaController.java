package br.com.apistarwars.starwars.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.apistarwars.starwars.model.Planeta;
import br.com.apistarwars.starwars.service.PlanetaService;

@Controller
public class PlanetaController {

	@Autowired
	private PlanetaService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> inicio() throws Exception {

		List<Planeta> planetas = service.obterTodosViaBanco();

		return new ResponseEntity<>(planetas, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAll", method = RequestMethod.POST)
	public ResponseEntity<?> obterTodosViaApi() throws Exception {
		
		List<Planeta> planetas = service.obterTodosViaSwapi();
		service.saveAll(planetas);

		return inicio();
	}

	@RequestMapping(value = "/removeAll", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerTodos() throws Exception {
		
		service.removeAll();
		
		return inicio();
	}

	@RequestMapping(value = "/editar", method = RequestMethod.PUT)
	public ResponseEntity<?> editar(String planetaJson) throws Exception {
		
		Planeta planeta = Planeta.toJson(planetaJson);
		service.save(planeta);
		
		return new ResponseEntity<>(planeta, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	private ResponseEntity<?> form(String planetaJson) throws Exception {
		
		Planeta planeta = Planeta.toJson(planetaJson);
		
		if(planeta.getUrl() == null) {
			planeta.setUrl("https://swapi.co/api/planets/0/");
		}
		
		service.save(planeta);

		return new ResponseEntity<>(planeta, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/deletar", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletar(@RequestParam("id") String id) throws Exception {

		service.removePorId(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/pesquisarPorId", method=RequestMethod.GET)
	public ResponseEntity<?> pesquisarPorId(@RequestParam("id") int id) throws Exception {
		
		Planeta planeta = service.findByIdNoBanco(id);
		
		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

	@RequestMapping(value = "/pesquisarPorNome", method = RequestMethod.GET)
	public ResponseEntity<?> pesquisarPorNome(@RequestParam("nome") String nome) throws Exception {
		
		Planeta planeta = service.findByName(nome);
		
		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}
	
	@RequestMapping(value="/pesquisarPorIdViaSwapi", method=RequestMethod.GET)
	public ResponseEntity<?> pesquisarPorIdViaSwapi(@RequestParam("id") int id) throws Exception{
		
		Planeta planeta = service.obterUmViaSwapi(id);
		
		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}
}
