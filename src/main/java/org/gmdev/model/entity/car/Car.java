package org.gmdev.model.entity.car;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@Document(collection = "Car")
public class Car {

    @Id
    private String id;

    private String name;
}
