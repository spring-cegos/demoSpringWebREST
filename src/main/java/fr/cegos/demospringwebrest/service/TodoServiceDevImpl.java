package fr.cegos.demospringwebrest.service;

import fr.cegos.demospringwebrest.bo.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette implémentation ajoute et recupère des tâches en mémoire Java (pas d'écriture en base de donnée)
 */
@Service
public class TodoServiceDevImpl implements TodoService {
    // les tâches sont stockées dans une liste Java
    List<Todo> listeTodo = new ArrayList<>();

    /**
     * On crée 2 taches au démarrage du service
     */
    public TodoServiceDevImpl() {
        listeTodo.add(new Todo(1L, "faire les courses"));
        listeTodo.add(new Todo(2L, "suivre cours "));
        listeTodo.add(new Todo(3L, "faire devoirs "));
    }

    /**
     * pour les différentes méthodes des tâches, on accède/modifie la liste Java
     */
    @Override
    public List<Todo> listTodos() {
        return listeTodo;
    }

    @Override
    public Todo addTodo(Todo todo) {
        todo.setId(listeTodo.getLast().getId() + 1);
        listeTodo.add(todo);
        return todo;
    }

    @Override
    public void saveTodo(Todo todo) {
        for (int i = 0; i < listeTodo.size(); i++) {
            if (listeTodo.get(i).getId().equals(todo.getId())) {
                listeTodo.set(i, todo);
            }
        }
    }

    @Override
    public void deleteTodo(Long id) {
        for (int i = 0; i < listeTodo.size(); i++) {
            if (listeTodo.get(i).getId().equals(id)) {
                listeTodo.remove(i);
            }
        }
    }

    @Override
    public Todo getTodo(Long id) {
        for (int i = 0; i < listeTodo.size(); i++) {
            if (listeTodo.get(i).getId().equals(id)) {
                return listeTodo.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Todo> searchTodos(String termeRecherche) {
        List<Todo>  listeTodoFiltree = new ArrayList<>();
        for (int i = 0; i < listeTodo.size(); i++) {
            if (listeTodo.get(i).getName().contains(termeRecherche)) {
                listeTodoFiltree.add(listeTodo.get(i));
            }
        }
        return listeTodoFiltree;
    }
}