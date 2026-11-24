package fr.cegos.demospringwebrest.service;

import fr.cegos.demospringwebrest.bo.Todo;
import fr.cegos.demospringwebrest.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        listeTodo.add(new Todo(1L, "faire les courses", LocalDate.of(2026,12,12)));
        listeTodo.add(new Todo(2L, "suivre cours ", LocalDate.of(2026,10,12)));
        listeTodo.add(new Todo(3L, "faire devoirs ", LocalDate.of(2027,1,2)));
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
        // validation métier
        if (listeTodo.size() == 5){
            throw new BusinessException("vous avez déjà 5 tâches en cours veuillez les traiter avant d'en ajouter une autre");
        }

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