package com.example.jakartaee.mapper;


import com.example.jakartaee.dto.PersonDto;
import com.example.jakartaee.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class Mapper {

    public List<PersonDto> map(List<Person> all) {
        return all.stream().map(PersonDto::new).toList();
    }

    public Person map(PersonDto person) {
        var f = new Person();
        f.setId( person.getId());
        f.setName(person.getName());
        f.setOld(person.getOld());
        f.setGender(person.getGender());
        return f;
    }

    public PersonDto map(Person person) {
        return new PersonDto(person);
    }
}
