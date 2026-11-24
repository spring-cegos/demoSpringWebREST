package fr.cegos.demospringwebrest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Cette classe va intercepter les exceptions lancées par mes controlleurs ou services lors de la phase de validation
 * afin de formater la réponse JSON avec un format plus lisible
 */
@ControllerAdvice
public class GlobalExceptionHandler extends
        ResponseEntityExceptionHandler {

    /**
     * Cette méthode est appelée lorsqu'une exception de validation est levée
     *
     * Elle retourne un format personalisé selon nos besoin de réponse HTTP
     */
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException
                                         ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        /**
         * Dans notre cas, on a envie de retourner un JSON avec un attribut "errors"
         * qui contient la liste des erreurs à afficher
         */
        // 1 - on crée une HasMap qui va contenir le format de notre réponse
        Map<String, List<String>> body = new HashMap<>();

        // 2 - on crée la liste des erreurs
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(Collectors.toList());

        // 3 - on affecte cette liste à l'attribut "errors" de notre HasMap de réponse
        body.put("errors", errors);

        // la réponse HTTP traduite en JSON sera de la forme {"errors" : ["erreur 1", "erreur 2"]}
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Traitement des exceptions "Métier" (business exception)
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            // + autres exceptions qu'on souhaite renvoyer en tant que BAD_REQUEST
            BusinessException.class
    })
    public ResponseEntity<Object> handleHelloException(Exception e){
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors", List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}