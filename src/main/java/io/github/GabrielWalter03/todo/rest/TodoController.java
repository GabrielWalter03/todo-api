package io.github.GabrielWalter03.todo.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.GabrielWalter03.todo.model.Todo;
import io.github.GabrielWalter03.todo.repository.TodoRepository;

@RestController // Essa notação faz com que o Spring mapeie ele como componente Rest e receba requisições e envie respsta
@RequestMapping("/api/todos") // Mapeia a url para que essa url receba requisições
@CrossOrigin("http://localhost:4200")// Isso Permite que esse controller receba requisições dessa URL
public class TodoController {

	@Autowired //injetar automaticamente a instância do TodoRepository nessa classe
	private TodoRepository repository;
	
	@PostMapping // Indica q o método abaixo é do tipo POST
	public Todo save(@RequestBody Todo todo) { // Diz ao Spring para pegar o corpo da requisição HTTP ...
		return repository.save(todo);          //...(JSON enviado) e convertê-lo em um objeto Java da classe Todo
	}
	
	@GetMapping
	public List<Todo> getAll(){
		return this.repository.findAll();
	}
	
	// url/api/todos/id(Qualquer número que represente o id)
	@GetMapping("{id}") // esse método será chamado quando houver  um requisição get para api/todos/id 
	public Todo getById(@PathVariable Long id) { // PathVariable indica que vai pegar o valor passado na url
		return repository
				.findById(id) // se o id n for acahado caí no orElseThrow 
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	@PatchMapping("{id}/done")// O Patch é usado quando vc fizer uma atualização parcial
	public Todo markAsDone(@PathVariable Long id) {
		return repository.findById(id).map(todo -> {
			todo.setDone(true);
			todo.setDoneDate(LocalDateTime.now());
			repository.save(todo);
			return todo;
		}).orElse(null);
	}
}
