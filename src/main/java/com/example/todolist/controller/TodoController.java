package com.example.todolist.controller;

import com.example.todolist.entity.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todoRequest) {
        return todoService.addTodo(todoRequest);
    }

    @PutMapping(path = "{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todoRequest) {
        return todoService.updateTodoById(id, todoRequest);
    }

    @DeleteMapping(path = "{id}")
    public Todo removeTodo(@PathVariable Integer id) {
        return todoService.removeTodo(id);
    }
}
