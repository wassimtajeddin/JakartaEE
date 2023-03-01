package com.example.jakartaee.validate;

import com.example.jakartaee.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonValidator {

    public boolean validate(Person person){
        return person.getName() != null && !person.getName().isEmpty();
    }
}
