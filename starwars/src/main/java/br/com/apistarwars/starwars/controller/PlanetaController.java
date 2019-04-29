package br.com.apistarwars.starwars.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.apistarwars.starwars.exception.PlanetaNotFoundException;
import br.com.apistarwars.starwars.model.Planeta;
import br.com.apistarwars.starwars.service.PlanetaService;

@Controller
@RequestMapping("/planets/")
public class PlanetaController {

	@Autowired
	private PlanetaService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getPlanets() throws Exception {

		List<Planeta> planetas = service.obterTodosViaBanco();

		return new ResponseEntity<>(planetas, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> savePlanet(@Valid @RequestBody Planeta planeta) throws Exception {

		service.save(planeta);

		return new ResponseEntity<>(planeta, HttpStatus.CREATED);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable int id) throws Exception {

		try {
			
			service.removePorId(id);
			
		} catch (NoSuchElementException e) {
			throw new PlanetaNotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable int id) throws Exception {
		Planeta planeta;

		try {
			
			planeta = service.findById(id);
			
		} catch (NoSuchElementException e) {
			throw new PlanetaNotFoundException();
		}
		
		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

	@RequestMapping(path = "name/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> findByName(@PathVariable String name) throws Exception {

		Planeta planeta;
		
		try {
			
			planeta = service.getPlanetSWApiByName(name);
			
		} catch (NoSuchElementException e) {
			throw new PlanetaNotFoundException();
		}
		
		service.save(planeta);

		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

}
