package org.gmdev.service.person;

import org.gmdev.api.person.model.CreatePersonApiReq;
import org.gmdev.api.person.model.GetPersonApiRes;
import org.gmdev.api.person.model.UpdatePersonApiReq;
import org.gmdev.dao.person.entity.Person;
import org.gmdev.setup.PostgresTestcontainersSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PersonServiceTest extends PostgresTestcontainersSetup {

    @Autowired
    PersonTestHelper personTestHelper;

    @Autowired
    PersonService underTest;

    @AfterEach
    void setUp() {
        personTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOnePerson() {
        // Given
        List<Person> people = getFakePeople();
        personTestHelper.savePersonList(people);

        Person person = people.get(0);

        // When
        GetPersonApiRes foundPerson = underTest.getOnePerson(person.getId());

        // Then
        assertThat(foundPerson).isNotNull();
        assertThat(foundPerson.getCreatedAt()).isNotNull();
        assertThat(foundPerson.getName()).isEqualTo("Tobia Guru");
    }

    @Test
    void itShouldThrowIfPersonNotFound() {
        // Given
        List<Person> people = getFakePeople();
        personTestHelper.savePersonList(people);

        // When
        UUID id = UUID.randomUUID();

        // When Then
        assertThatThrownBy(() -> underTest.getOnePerson(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Person with id: %s not found", id));
    }

    @Test
    void itShouldFindAllPeople() {
        // Given
        List<Person> people = getFakePeople();
        personTestHelper.savePersonList(people);

        // When
        List<GetPersonApiRes> allPeople = underTest.getAllPeople();

        // Then
        assertThat(allPeople).hasSize(2);
    }

    @Test
    void itShouldAddPerson() {
        // Given
        CreatePersonApiReq bodyReq = new CreatePersonApiReq("Bomb Cha");

        // When
        UUID personId = underTest.addPerson(bodyReq);
        Person foundPerson = personTestHelper.findOnePerson(personId).orElseThrow();

        // Then
        assertThat(foundPerson.getName()).isEqualTo("Bomb Cha");
    }

    @Test
    void itShouldUpdatePerson() {
        // Given
        List<Person> people = getFakePeople();
        personTestHelper.savePersonList(people);
        UUID personId = people.get(0).getId();

        UpdatePersonApiReq bodyReq = new UpdatePersonApiReq("Bob Cha updated");

        // When
        GetPersonApiRes personRes = underTest.updatePerson(personId, bodyReq);
        Person updatedPerson = personTestHelper.findOnePerson(personId).orElseThrow();

        // Then
        assertThat(personRes).isNotNull();
        assertThat(updatedPerson.getName()).isEqualTo("Bob Cha updated");
    }

    @Test
    void itShouldDeletePerson() {
        // Given
        List<Person> people = getFakePeople();
        personTestHelper.savePersonList(people);
        UUID personId = people.get(0).getId();

        // When
        underTest.deletePerson(personId);
        List<Person> allPeople = personTestHelper.findAllPeople();
        Optional<Person> personMaybe = personTestHelper.findOnePerson(personId);

        // Then
        assertThat(allPeople).hasSize(people.size() - 1);
        assertThat(personMaybe).isEmpty();
    }

    public List<Person> getFakePeople() {
        LocalDateTime now = LocalDateTime.now();

        Person person1 = new Person(UUID.randomUUID(), "Tobia Guru", now, now);
        Person person2 = new Person(UUID.randomUUID(), "Sante the Wise", now, now);

        return List.of(person1, person2);
    }


}