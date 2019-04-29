package br.com.apistarwars.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que representa a exception caso um Planeta não seja encontrado na base de dados ou na API
 * 
 * @author Alexsandre Rodrigues
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Não foi encontrado Planeta com os dados informados!")
public class PlanetaNotFoundException extends RuntimeException{

	/**
	 * Serialização Default
	 */
	private static final long serialVersionUID = 1L;

}
