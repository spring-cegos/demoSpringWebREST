package fr.cegos.demospringwebrest.controller;

import fr.cegos.demospringwebrest.bo.Todo;
import fr.cegos.demospringwebrest.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin // @CrossOrigin : permet d'accéder à l'application depuis un Front situé à une origine différente (port ou adresse différente)
class TodoRestController {
    @Autowired //possible également de créer un constructeur avec argument pour le controller
    private TodoService  todoService;

    /**
     * GetMapping
     * Affiche la liste des taches
     *
     * On a ajouté un terme de recherche :
     * @RequestParam(required = false) String termeRecherche
     * afin de permettre les requêtes de type : GET http://localhost:8080/todo?termeRecherche=faire
     */
    @GetMapping
    public List<Todo> getTodo(@RequestParam(required = false) String termeRecherche){
        // si jamais j'ai un terme de recherche dans ma requête GET
        if (termeRecherche != null){
            // je retourne la liste des taches filtrée selon ce terme
            return todoService.searchTodos(termeRecherche);
        }
        return todoService.listTodos();
    }

    /**
     * GetMapping
     * Affiche une tâche spécifique
     */
    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Long id){
        return todoService.getTodo(id);
    }

    /**
     * PostMapping
     * Crée une nouvelle tâche
     */
    @PostMapping
    public void postTodo(@RequestBody Todo todo){
        todoService.addTodo(todo);
    }

    /**
     * PutMapping
     * Modifie une  tâche
     *
     * note : l'utilisation de /{id} dans la route et de @PathVariable Long id en argument
     * me permet de récupérer la valeur de l'id de tache dans l'url de la requête
     * exemple : http://localhost:8080/todo/3 => id=3
     */
    @PutMapping("/{id}")
    public void putTodo(@PathVariable Long id, @RequestBody Todo todo){
        todo.setId(id);
        todoService.saveTodo(todo);
    }


    /**
     * DeleteMapping
     * Supprime une tâche
     *
     * note : l'utilisation de /{id} dans la route et de @PathVariable Long id en argument
     * me permet de récupérer la valeur de l'id de tache dans l'url de la requête
     * exemple : http://localhost:8080/todo/3 => id=3
     */
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }

}
