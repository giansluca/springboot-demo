package org.gmdev.api.person;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.person.model.CreatePersonApiReq;
import org.gmdev.api.person.model.GetPersonApiRes;
import org.gmdev.api.person.model.UpdatePersonApiReq;
import org.gmdev.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("api/v1/person")
@Validated
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetPersonApiRes> getAllPeople() {
        log.info("Incoming call to [PersonController - getAllPeople]");
        return personService.getAllPeople();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{personId}")
    public GetPersonApiRes getOnePerson(@PathVariable("personId") UUID personId) {
        log.info("Incoming call to [PersonController - getOnePerson]");
        return personService.getOnePerson(personId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID addPerson(@Valid @NotNull @RequestBody CreatePersonApiReq bodyReq) {
        log.info("Incoming call to [PersonController - addPerson]");
        return personService.addPerson(bodyReq);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{personId}")
    public GetPersonApiRes updatePerson(
            @PathVariable("personId") UUID personId,
            @Valid @NotNull @RequestBody UpdatePersonApiReq bodyReq) {

        log.info("Incoming call to [PersonController - addPerson]");
        return personService.updatePerson(personId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{personId}")
    public ResponseEntity<Object> deletePerson(@PathVariable("personId") UUID personId) {
        personService.deletePerson(personId);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("X-Op-Status", "Deleted");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(String.format("person with id: %s deleted", personId));
    }

}

