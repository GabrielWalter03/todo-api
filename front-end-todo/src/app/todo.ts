import { Injectable } from '@angular/core';
import {TodoObject} from './todoObject'
import {Observable} from 'rxjs'
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Todo {

  apiURL: string = 'http://localhost:8080/api/todos';

  constructor(private http: HttpClient) {} //injetando o serviço HTTP do Angular para usar o método dele de fazer POST


  salvar(todoObject: TodoObject) : Observable<TodoObject> { //Como o componenent transforma os valores do form como TodoObject esse método recebe um TodoObject
                                                            //Nesse método vc passa um objeto TodoObject e e no final retorna um observable
                                                            // Observable do tipo TodoObjetct que dizer q ele vai esperar um resposta do servidor
    return this.http.post<TodoObject>(this.apiURL, todoObject) // 
  }

  listar() : Observable<TodoObject[]>{ //retorna um Observable que vai, em algum momento futuro, emitir um valor do tipo TodoObject[].
    ///////////////////////////////////o Angular não sabe exatamente quando o servidor vai responder. Então, ele usa Observable para:
    ///////////////////////////////////esperar os dados e permitir que você reaja quando a resposta chegar (via .subscribe() ou outro).
    return this.http.get<TodoObject[]>(this.apiURL) 
  }

  deletar(id: number) : Observable<void> {
    const url = `${this.apiURL}/${id}`
    return this.http.delete<void>(url)
  }

  marcarComoConcluido(id : number) : Observable<TodoObject>{
    const url = `${this.apiURL}/${id}/done`
    return this.http.patch<TodoObject>(url, {})
  }
}
