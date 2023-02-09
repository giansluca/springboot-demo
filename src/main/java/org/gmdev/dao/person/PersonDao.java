package org.gmdev.dao.person;

import org.gmdev.dao.person.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    List<Person> findAllPeople();

    Optional<Person> findPersonById(UUID id);

    UUID insertPerson(Person person);

    Person updatePerson(Person person);

    void deletePersonById(UUID id);

    void deleteAll();

}
