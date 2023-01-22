package org.gmdev.model.mapper;

import org.gmdev.model.entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID personId = UUID.fromString(rs.getString("id"));
        String personName = rs.getString("name");

        return new Person(personId, personName);
    }
}
