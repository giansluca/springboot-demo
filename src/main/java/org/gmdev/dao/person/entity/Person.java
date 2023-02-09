package org.gmdev.dao.person.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gmdev.api.person.model.GetPersonApiRes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class Person {

    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetPersonApiRes toApiRes() {
        return new GetPersonApiRes(id, name, createdAt, updatedAt);
    }

}
