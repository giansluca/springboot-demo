package org.gmdev.springbootdemo.dao;

import org.gmdev.springbootdemo.model.entity.Person;
import org.gmdev.springbootdemo.model.mapper.rowmapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public PersonPostgresDaoImp(
            @Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> findAllPeople() {
        String sql = "SELECT id, name FROM person";
        return jdbcTemplate.query(sql, new PersonMapper());
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        String sql = "SELECT id, name FROM person WHERE id = ?";
        Person person = null;
        try {
            person = jdbcTemplate.queryForObject(
                    sql, new Object[]{id}, new PersonMapper());

            return Optional.of(person);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(person);
        }
    }

    @Override
    public Person insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO person (id, name) values(?, ?)";
        jdbcTemplate.update(sql, id, person.getName());

        return findPersonById(id).get();
    }

    @Override
    public Person updatePersonById(UUID id, Person person) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), id);

        return findPersonById(id).get();
    }

    @Override
    public int deletePersonById(UUID id) {
        String sql = "DELETE FROM person WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

}
