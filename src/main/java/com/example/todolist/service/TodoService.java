package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.todoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TodoService {
    @Resource
    private final todoRepository todoRepository;

    public TodoService(com.example.todolist.repository.todoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

}
