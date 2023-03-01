package com.example.jakartaee.dto;

import com.example.jakartaee.entity.Person;

import java.math.BigDecimal;

public class PersonDto {
    private int id;
    String name;

    int old;

    String gender;

    public PersonDto() {
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.old = person.getOld();
        this.gender = person.getGender();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
