import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TodoService {


  constructor(
    
    @Inject('apiUrl') private apiUrl,

    private http: HttpClient
  ) { }

  addTodo(obj) {
    return this.http.post(this.apiUrl+"/work", obj);
  }

  getAllTodos() {
    return this.http.get(this.apiUrl +"/work");
  }

  updateTodo(obj) {
    return this.http.put(this.apiUrl +"/work", obj);
  }

  removeTodo(id) {
    return this.http.delete(this.apiUrl +"/work/"+ id);
  }
}
