package org.gmdev.service;

import org.gmdev.dao.PersonDao;
import org.gmdev.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("personPostgresDao") PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> getAllPeople() {
        return personDao.findAllPeople();
    }

    public Person getOnePerson(UUID id) {
        return personDao.findPersonById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id: %s not found", id)));
    }

    public Person addPerson(Person person) {
        return personDao.insertPerson(person);
    }

    public Person updatePerson(UUID id, Person person) {
        personDao.findPersonById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Person with id: %s not found", id)));

        return personDao.updatePersonById(id, person);
    }

    public void deletePerson(UUID id) {
        if (personDao.deletePersonById(id) != 1)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Person with id: %s not found", id));
    }
}
