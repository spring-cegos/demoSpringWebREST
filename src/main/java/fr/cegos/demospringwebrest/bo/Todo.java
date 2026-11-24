package fr.cegos.demospringwebrest.bo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    Long id;
    @NotEmpty // validation : le nom d'une tache doit être non vice
    @Size(min = 3, max = 20) // validation : le nom d'une tache doit contenir entre 3 et 10 caractères
    String name;
    @Future(message = "une tâche doit être dans le futur") // validation : la date limite d'une tache doit être dans le futur
    LocalDate dueDate;
}