package br.com.apistarwars.starwars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.apistarwars.starwars.model.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String>{

}
