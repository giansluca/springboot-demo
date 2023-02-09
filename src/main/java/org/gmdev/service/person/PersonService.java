package org.gmdev.service.person;

import org.gmdev.api.person.model.CreatePersonApiReq;
import org.gmdev.api.person.model.GetPersonApiRes;
import org.gmdev.api.person.model.UpdatePersonApiReq;
import org.gmdev.dao.person.PersonDao;
import org.gmdev.dao.person.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class PersonService {

    private final PersonDao personRepository;

    @Autowired
    public PersonService(@Qualifier("personPostgresDao") PersonDao personDao) {
        this.personRepository = personDao;
    }

    public List<GetPersonApiRes> getAllPeople() {
        return personRepository.findAllPeople()
                .stream()
                .map(Person::toApiRes)
                .toList();
    }

    public GetPersonApiRes getOnePerson(UUID personId) {
        Person person = getPersonByIdOrThrow(personId);
        return person.toApiRes();
    }

    public UUID addPerson(CreatePersonApiReq bodyReq) {
        LocalDateTime now = LocalDateTime.now();

        return personRepository.insertPerson(new Person(UUID.randomUUID(), bodyReq.getName(), now, now));
    }

    public GetPersonApiRes updatePerson(UUID personId, UpdatePersonApiReq bodyReq) {
        Person person = getPersonByIdOrThrow(personId);

        person.setUpdatedAt(LocalDateTime.now());
        if (bodyReq.getName() != null)
            person.setName(bodyReq.getName());

        return personRepository.updatePerson(person).toApiRes();
    }

    public void deletePerson(UUID personId) {
        getPersonByIdOrThrow(personId);

        personRepository.deletePersonById(personId);
    }

    private Person getPersonByIdOrThrow(UUID personId) {
        return personRepository.findPersonById(personId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        String.format("Person with id: %s not found", personId)));
    }


}
