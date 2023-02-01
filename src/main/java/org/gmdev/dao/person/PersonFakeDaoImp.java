package org.gmdev.dao.person;

import org.gmdev.model.entity.person.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personFakeDao")
public class PersonFakeDaoImp implements PersonDao {

    private static final List<Person> DB = new ArrayList<>();

    @Override
    public List<Person> findAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public UUID insertPerson(Person person) {
        DB.add(new Person(person.getId(), person.getName()));

        return person.getId();
    }

    @Override
    public Person updatePerson(Person update) {
        return findPersonById(update.getId())
                .map(person -> {
                    int indexPersonToUpdate = DB.indexOf(person);
                    if (indexPersonToUpdate >= 0) {
                        DB.set(indexPersonToUpdate, new Person(update.getId(), update.getName()));
                        return DB.get(indexPersonToUpdate);
                    }
                    return null;
                })
                .orElseThrow();
    }

    @Override
    public void deletePersonById(UUID id) {
        Optional<Person> personMaybe = findPersonById(id);
        if (personMaybe.isEmpty()) return;

        DB.remove(personMaybe.get());
    }

    @Override
    public void deleteAll() {
        DB.clear();
    }

}
