package br.com.apistarwars.starwars.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.apistarwars.starwars.model.Planeta;
import br.com.apistarwars.starwars.repository.PlanetaRepository;

@Repository
public class PlanetaDAO {

	@Autowired
	private PlanetaRepository repository;

	@Autowired
	private MongoTemplate mongo;

	public void save(Planeta planeta) {
		repository.save(planeta);
	}

	public Planeta findByName(String nome) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(nome, "i"));

		Planeta planeta = mongo.findOne(query, Planeta.class);

		if (planeta == null) {
			throw new IllegalArgumentException("Não existe um planeta com o nome " + nome);
		}
		return planeta;
	}

	public List<Planeta> findAll() {
		return repository.findAll();
	}

	public void removeAll() {
		repository.deleteAll();
	}

	public void update(Planeta planeta) {
		Update update = new Update();

		update.set("name", planeta.getName());
		update.set("climate", planeta.getClimate());
		update.set("terrain", planeta.getTerrain());

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(planeta.getId()));

		mongo.updateFirst(query, update, Planeta.class);
	}

	public Planeta findById(int id) throws Exception {

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));

		Planeta planeta = mongo.findOne(query, Planeta.class);

		if (planeta == null) {
			throw new IllegalArgumentException("Não foi localizado planeta com ID " + id);
		}
		return planeta;
	}

	public void removePorId(int id) {

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));

		mongo.remove(query, Planeta.class);
	}
}
