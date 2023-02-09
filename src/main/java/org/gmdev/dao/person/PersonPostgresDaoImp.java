package org.gmdev.dao.person;

import org.gmdev.dao.person.entity.Person;
import org.gmdev.dao.person.entity.PersonMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personPostgresDao")
public class PersonPostgresDaoImp implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonPostgresDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> findAllPeople() {
        String sql = "SELECT id, name, created_at, updated_at FROM person";
        return jdbcTemplate.query(sql, new PersonMapper());
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        String sql = "SELECT id, name, created_at, updated_at FROM person WHERE id = ?";
        Person person;
        try {
            person = jdbcTemplate.queryForObject(sql, new PersonMapper(), id);

            return Optional.ofNullable(person);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UUID insertPerson(Person person) {
        String sql = "INSERT INTO person (id, name, created_at, updated_at) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getId(), person.getName(), person.getCreatedAt(), person.getUpdatedAt());

        return person.getId();
    }

    @Override
    public Person updatePerson(Person person) {
        String sql = "UPDATE person SET name = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), person.getUpdatedAt(), person.getId());

        return findPersonById(person.getId()).orElseThrow();
    }

    @Override
    public void deletePersonById(UUID id) {
        String sql = "DELETE FROM person WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM person";

        jdbcTemplate.update(sql);
    }


}
