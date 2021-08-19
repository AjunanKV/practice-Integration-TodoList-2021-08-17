package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Resource
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo updateTodoById(Integer todoId, Todo todoRequest) {
        Todo updateTodo = (Todo) todoRepository.findById(todoId)
                .map(todo -> updateTodoInformation(todo, todoRequest))
                .get();
        return todoRepository.save(updateTodo);
    }

    private Object updateTodoInformation(Todo todo, Todo todoRequest) {
        todo.setDone(todoRequest.isDone());
        return todo;
    }

    public Todo addTodo(Todo todoRequest) {
        return todoRepository.save(todoRequest);
    }

    public Todo removeTodo(Integer id) {
        Optional<Todo> removeTodo = todoRepository.findById(id);
        todoRepository.deleteById(id);
        return removeTodo.orElse(null);
    }
}
