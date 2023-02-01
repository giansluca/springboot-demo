package org.gmdev.model.entity.person;

import lombok.*;
import org.gmdev.api.person.model.GetPersonApiRes;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class Person {

    private UUID id;
    private String name;

    public GetPersonApiRes toApiRes() {
        return new GetPersonApiRes(id, name);
    }

}
