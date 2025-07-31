package io.github.GabrielWalter03.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.GabrielWalter03.todo.model.Todo;

//O SpringData/Jpa é framework dentro do spring que permite trabalhar com dados(Banco de dados)
//Aqui ele está recebendo a entidade que irá trabalhar - Todo -  e o tipo do id dela - Long.
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
