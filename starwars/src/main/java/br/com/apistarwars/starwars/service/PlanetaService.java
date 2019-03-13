package br.com.apistarwars.starwars.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.apistarwars.starwars.integrador.ConexaoStarwar;
import br.com.apistarwars.starwars.model.Planeta;
import br.com.apistarwars.starwars.repository.PlanetaRepository;

@Service
public class PlanetaService {

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

	public List<Planeta> obterTodosViaSwapi() throws Exception {
		ConexaoStarwar api = new ConexaoStarwar();

		return api.getAllPlanetForSwapi();
	}

	public void saveAll(List<Planeta> planetas) {
		repository.saveAll(planetas);
	}

	public List<Planeta> obterTodosViaBanco() {
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
		query.addCriteria(Criteria.where("_id").is(new ObjectId(planeta.getIdString())));
		
		mongo.updateFirst(query, update, Planeta.class);
	}

	public Planeta obterUmViaSwapi(int id) throws Exception {
		ConexaoStarwar api = new ConexaoStarwar();
		return api.getPlanetById(id);
	}

	public void removerPorNome(String nome) {

		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(nome, "i"));
		mongo.remove(query, Planeta.class);
	}

	public void removeAllPorTerreno(String terreno) {
		Query query = new Query();
		query.addCriteria(Criteria.where("terrain").regex(terreno, "i"));
		mongo.findAllAndRemove(query, Planeta.class);
	}

	public List<Planeta> findByTerrain(String terreno) {
		List<Planeta> planetas = new ArrayList<>();

		Query query = new Query();
		query.addCriteria(Criteria.where("terrain").regex(terreno, "i"));

		planetas = mongo.find(query, Planeta.class);

		if (planetas.isEmpty()) {
			throw new IllegalArgumentException("Não foi encontrado planetas com o terreno " + terreno);
		}

		return planetas;
	}

	public List<Planeta> findAllByName(String nome) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(nome, "i"));

		return mongo.find(query, Planeta.class);
	}

	// pesquisa por ID do banco de dados que é no formato ObjectId
	public Planeta findById(String id) throws Exception {
		ObjectId _id = new ObjectId(id);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(_id));
		
		Planeta planeta = mongo.findOne(query, Planeta.class); 

		if(planeta == null) {
			throw new IllegalArgumentException("Não foi localizado planeta com ID " + id);
		}
		return planeta;
	}

	// remove por ID do banco de dados que é no formato ObjectId
	public void removePorId(String id) {

		ObjectId _id = new ObjectId(id);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(_id));
		
		mongo.remove(query, Planeta.class);
	}

	// pesquisa no site swapi.co pelo id que é do tipo Integer
	public Planeta findByIdNoBanco(int id) throws Exception{
		Planeta planeta;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		
		planeta = mongo.findOne(query, Planeta.class);
		
		if(planeta == null) {
			throw new IllegalArgumentException(" Não foi encontrado um planeta com o id " + id);
		}
		return planeta;
	}

}
