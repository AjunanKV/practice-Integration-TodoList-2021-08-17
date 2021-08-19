package com.example.todolist.todoIntegrationTest;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @Test
    void should_return_all_todo_when_getAllTodos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("first to do item"))
                .andExpect(jsonPath("$[1].text").value("second to do item"));

    }

    @Test
    void should_create_todo_when_addTodo_given_todo_information() throws Exception {
        String todo = "{\n" +
                "    \"text\": \"test todo\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todo))
                .andExpect(status().isCreated());
    }
    @Test
    void should_remove_todo_when_removeTodo_given_todo_id()throws Exception{
        final Todo todo = new Todo(99, "dummy test todo", false);
        final Todo savedTodo = todoService.addTodo(todo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_update_todo_when_updateTodo_given_todo_information() throws Exception {
        final Todo todo = new Todo(99, "test todo", false);
        final Todo savedTodo = todoService.addTodo(todo);

        String updateTodo = "{\n" +
                "    \"text\": \"dummy test 99\",\n" +
                "    \"done\": true\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateTodo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done").value(true))
                .andExpect(jsonPath("$.text").value("dummy test 99"));

        todoService.removeTodo(savedTodo.getId());
    }

}
