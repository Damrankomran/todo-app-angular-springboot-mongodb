import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { TodoService } from '../../services/todo.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  data = {
    pendingList: [],
    inProgressList: [],
    doneList: [],
  };

  obj = {};

  constructor(
    private todoService: TodoService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getAllTodos();
  }

  drop(event: CdkDragDrop<string[]>, listName: String) {
    console.log("listName --> "+listName);
    if (event.previousContainer === event.container) { //Move in the same list.
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    }
    else { //Move to different list.
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
    if (listName == "pending") {
      this.data["pendingList"][event.currentIndex].status = "pending";
    }
    else if (listName == "inProgress") {
      this.data["inProgressList"][event.currentIndex].status = "inProgress";
    }
    else {
      this.data["doneList"][event.currentIndex].status = "done";
    }
    console.log("this.data -->"+JSON.stringify(this.data));
    this.updateTodo();
  }

  addTodo(todo) {
    
    var index = this.data.pendingList.length;

    const obj = {
      name: todo.value,
      index: index
    };

    console.log("obj -->" + obj.name);

    this.todoService.addTodo(obj).subscribe((res) => {
      this.openSnackBar("New item added!");
      todo.value = "";
      this.getAllTodos();
    }, (err) => {
      console.log(err);
    });
  }

  getAllTodos() {
    this.todoService.getAllTodos().subscribe((res) => {
      Object.keys(res).forEach((key) => {
        this.data[key] = res[key];
      });
    }, (err) => {
      console.log(err);
    });
  }

  updateTodo() {
    this.todoService.updateTodo(this.data).subscribe((res) => {
      console.log(res);
    }, (err) => {
      console.log(err);
    });
  }

  removeTodo(id){
    if(confirm('Do you want to delete this item?')){
      this.todoService.removeTodo(id).subscribe((res) =>{
        console.log(res);
        this.getAllTodos();
      },(err) =>{
        console.log(err);
      });
    }
  }

  openSnackBar(message: string) {
    this._snackBar.open(message, "OK", {
      duration: 2000,
    });
  }

}
