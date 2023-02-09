package org.gmdev.service.person;


import org.gmdev.dao.person.PersonDao;
import org.gmdev.dao.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class PersonTestHelper {

    private final PersonDao personRepository;

    @Autowired
    public PersonTestHelper(@Qualifier("personPostgresDao") PersonDao personRepository) {
        this.personRepository = personRepository;
    }

    public void cleanDb() {
        personRepository.deleteAll();
    }

    public void savePersonList(List<Person> people) {
        for (Person person : people)
            personRepository.insertPerson(person);
    }

    public List<Person> findAllPeople() {
        return personRepository.findAllPeople();
    }

    public Optional<Person> findOnePerson(UUID personId) {
        return personRepository.findPersonById(personId);
    }


}
