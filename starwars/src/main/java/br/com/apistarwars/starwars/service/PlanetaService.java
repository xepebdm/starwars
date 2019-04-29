package br.com.apistarwars.starwars.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.apistarwars.starwars.dao.PlanetaDAO;
import br.com.apistarwars.starwars.integrador.ConexaoStarwar;
import br.com.apistarwars.starwars.model.Planeta;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaDAO dao;
	

	public void save(Planeta object) throws Exception {
		
		Planeta planeta = getPlanetSWApiByName(object);
				
		dao.save(planeta);
	}

	public Planeta findByName(String nome) {
		
		return dao.findByName(nome);
	}

	public List<Planeta> obterTodosViaBanco() {
		return dao.findAll();
	}

	public void removeAll() {
		dao.removeAll();
	}

	public void update(Planeta planeta) {
		dao.update(planeta);
	}

	public Planeta getPlanetSWApiById(int id) throws Exception {
		ConexaoStarwar api = new ConexaoStarwar();
		return api.getPlanetById(id);
	}
	
	public Planeta getPlanetSWApiByName(Planeta planeta) throws Exception{
		return getPlanetSWApiByName(planeta.getName());
	}
	
	public Planeta getPlanetSWApiByName(String name) throws Exception{
		ConexaoStarwar api = new ConexaoStarwar();
		return api.getPlanetByName(name);
	}


	public Planeta findById(int id) throws Exception {
		return dao.findById(id);
	}

	public void removePorId(int id) {
		dao.removePorId(id);
	}

}
