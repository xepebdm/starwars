package br.com.apistarwars.starwars.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(method = RequestMethod.POST)
	private ResponseEntity<?> savePlanet(@Valid @RequestBody String planetaJson) throws Exception {

		Planeta planeta = Planeta.toJson(planetaJson);

		service.save(planeta);

		return new ResponseEntity<>(planeta, HttpStatus.CREATED);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable int id) throws Exception {

		service.removePorId(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable int id) throws Exception {

		Planeta planeta = service.findById(id);

		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

	@RequestMapping(path = "name/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> findByName(@PathVariable String name) throws Exception {

//		Planeta planeta = service.findByName(name);
		Planeta planeta = service.getPlanetSWApiByName(name);

		service.save(planeta);

		return new ResponseEntity<>(planeta, HttpStatus.OK);
	}

}
