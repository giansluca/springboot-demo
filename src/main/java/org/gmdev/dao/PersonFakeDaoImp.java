package org.gmdev.dao;

import org.gmdev.model.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personFakeDao")
public class PersonFakeDaoImp implements PersonDao {

    // in memory DB simulation
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
    public Person insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));

        return findPersonById(id).get();
    }

    @Override
    public Person updatePersonById(UUID id, Person update) {
        return findPersonById(id)
                .map(person -> {
                    int indexPersonToUpdate = DB.indexOf(person);
                    if (indexPersonToUpdate >= 0) {
                        DB.set(indexPersonToUpdate, new Person(id, update.getName()));
                        return DB.get(indexPersonToUpdate);
                    }
                    return null;
                })
                .get();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = findPersonById(id);
        if (personMaybe.isEmpty())
            return 0;

        DB.remove(personMaybe.get());
        return 1;
    }


}
