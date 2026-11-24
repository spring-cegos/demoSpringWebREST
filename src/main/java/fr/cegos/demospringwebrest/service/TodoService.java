package fr.cegos.demospringwebrest.service;

import fr.cegos.demospringwebrest.bo.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> listTodos();
    Todo addTodo(Todo todo);
    void saveTodo(Todo todo);
    void deleteTodo(Long id);
    Todo getTodo(Long id);
    List<Todo> searchTodos(String termeRecherche);
}