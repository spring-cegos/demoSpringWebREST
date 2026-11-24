package fr.cegos.demospringwebrest.controller;

import fr.cegos.demospringwebrest.bo.Todo;
import fr.cegos.demospringwebrest.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation(summary = "récupérer la liste des tâches") // sert à la doc d'API
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
    @Operation(summary = "récupérer une tâche") // sert à la doc d'API
    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Long id){
        return todoService.getTodo(id);
    }

    /**
     * PostMapping
     * Crée une nouvelle tâche
     */
    @Operation(summary = "créer une tâche") // sert à la doc d'API
    @PostMapping
    public void postTodo(@Valid @RequestBody Todo todo){ // @Valid = l'objet de type Todo doit répondre aux cntraintes de validations, sinon, une erreur sera retournée
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
    @Operation(summary = "modifier une tâche") // sert à la doc d'API
    @PutMapping("/{id}")
    public void putTodo(@PathVariable Long id, @Valid @RequestBody Todo todo){
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
    @Operation(summary = "supprimer une tâche") // sert à la doc d'API
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }

}
