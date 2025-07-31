import { Component, signal, OnInit } from '@angular/core';
import {Todo} from './todo'
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { TodoObject } from './todoObject';
//FormGroup e FormControl são classes de modelo. Você usa elas na lógica TypeScript
//para construir formulários reativos.

//ReactiveFormsModule é o módulo Angular que ativa o suporte para esses formulários.Ele vai no imports
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  todosObjects: TodoObject[] = [] //array chamado todosObjects   composto de TodoObject


  form: FormGroup = new FormGroup({ // FormGroup Agrupa vários campos em um conjunto.
                                    // Permite tratar o formulário como uma "unidade"
                                    // validar o conjunto, ou enviar todos os dados de uma vez.

    description: new FormControl('',[Validators.required, Validators.minLength(4)]) 
    // FormControl Representa um único campo do formulário
    // Ex: um input de texto, um checkbox, etc.
  })

  constructor(
    private service: Todo // Aqui estou injetando uma instância  do service pois é nele que tem os métodos de comunicação com o Back-end
  ){}

  ngOnInit(){ // Método que roda automáticamente quando o componente e inicializado
    this.listarTodos()
  }  

  listarTodos() {
    this.service.listar().subscribe(todosList => this.todosObjects = todosList )
  }
  

  submit(){
                                                        // this.form.value = pega o valor colocado no form e trnasforma em objeto
    const todoObject: TodoObject = {...this.form.value} // cria um TodoObject a partir de dados recebidos no form
    this.service.salvar(todoObject) //this.service.salvar = envia os dados pro servidor com POST conforme está no método do service
    .subscribe(SavedTodoObject => { //.subscribe espera a resposta do servidor
      this.todosObjects.push(SavedTodoObject)
      this.form.reset()
    })
    // O this se refere ao service que foi instanciado no construtor
  }

  delete(todoObject : TodoObject){
    this.service.deletar(todoObject.id!).subscribe({
      next: (response) => this.listarTodos()
    })
  }

  done(todoObject : TodoObject){
    this.service.marcarComoConcluido(todoObject.id!).subscribe({
      next: (todoAtualizado) => {
        todoObject.done = todoAtualizado.done
        todoObject.doneDate = todoAtualizado.doneDate
      }
    })
  }
}
