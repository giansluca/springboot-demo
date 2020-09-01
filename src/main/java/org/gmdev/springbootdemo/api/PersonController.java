package org.gmdev.springbootdemo.api;

import org.gmdev.springbootdemo.model.dto.PersonDto;
import org.gmdev.springbootdemo.model.entity.Person;
import org.gmdev.springbootdemo.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDto> getAllPeople() {
        List<Person> people = personService.getAllPeople();
        return people.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    public PersonDto getOnePerson(@PathVariable("id") UUID id) {
        Person person = personService.getOnePerson(id);
        return toDto(person);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PersonDto addPerson(@Valid @NotNull @RequestBody PersonDto personDto) {
        Person newPerson = personService.addPerson(tooEntity(personDto));
        return toDto(newPerson);
    }

    @PutMapping(path = "{id}")
    public PersonDto updatePerson(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody PersonDto personDto) {
        Person updatedPerson = personService.updatePerson(id, tooEntity(personDto));
        return toDto(updatedPerson);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable("id") UUID id) {
        personService.deletePerson(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("x-op-status", "Deleted");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(String.format("person with id: %s deleted", id));
    }

    private Person tooEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDto toDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }
}

