package org.gmdev.model.entity.car;

import lombok.*;
import org.gmdev.api.car.model.GetCarApiRes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@Document(collection = "Car")
public class Car {

    @Id
    private String id;

    private String name;

    public GetCarApiRes toApiRes() {
        return new GetCarApiRes(id, name);
    }

}
