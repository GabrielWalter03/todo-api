package io.github.GabrielWalter03.todo.model;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

//a pasta model (às vezes chamada entity ou domain) guarda as entidades da aplicação — que são 
//as representações das tabelas do banco de dados em forma de classes Java.


@Entity // O spring/JPA vai mapear essa classe como uma tabela de banco de dados 
        //com o nome que vc indicar no table ou com o nome da própria classe
@Getter // O Lombok vai gerar os getters em tempo de compilação
@Setter // O Lombok vai gerar os setters em tempo de compilação
public class Todo {
	
	@Id //Spring/JPA vai indicar que esse campo é a chave primária
	@GeneratedValue(strategy = GenerationType.IDENTITY) // O GenerationType.IDENTITY indica que o banco de dados O GenerationType.IDENTITY indica que o banco de dados vai ser responsável por auto-incrementar o ID.
	private Long id;
	
	@Column
	private String description;
	
	@Column
	private Boolean done;
	
	@Column
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm") // formata a data
	private LocalDateTime Createddate;
	
	@Column
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm") // formata a data
	private LocalDateTime doneDate;
	
	@PrePersist                // Essa notação é do JPA e faz com que um método seja chamada antes de um
	public void beforeSave() { // repository.save() - antes de uma entidade ser persistida e preenche um campo
		setCreateddate(LocalDateTime.now());
	}
}
