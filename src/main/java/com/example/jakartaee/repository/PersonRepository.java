package com.example.jakartaee.repository;


import com.example.jakartaee.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PersonRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Person> findAll(){
        var query = entityManager.createQuery("select f from Person f");
        return (List<Person>) query.getResultList();
    }

    public Optional<Person> findOne(int id){
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }

    public void insertPerson(Person person){
        entityManager.persist(person);
    }

    public void deletePerson(int id){
        var person = findOne(id);
        person.ifPresent((f)-> entityManager.remove(f));
    }

    public Person update(int id, Person person){
        var entity = entityManager.find(Person.class, id);
        entity.setName(person.getName());
        entity.setOld(person.getOld());
        entity.setGender(person.getGender());
        entityManager.persist(entity);
        return entity;
    }


    public List<Person> findAllByName(String name) {
        var query = entityManager.createQuery("select f from Person f where f.name like :name");
        query.setParameter("name", name);
        return (List<Person>) query.getResultList();
    }
}
