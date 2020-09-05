package org.gmdev.dao;

import org.gmdev.model.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    // insert Person with 'id randomly' generated
    default Person insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> findAllPeople();

    Optional<Person> findPersonById(UUID id);

    Person insertPerson(UUID id, Person person);

    Person updatePersonById(UUID id, Person person);

    int deletePersonById(UUID id);

}
