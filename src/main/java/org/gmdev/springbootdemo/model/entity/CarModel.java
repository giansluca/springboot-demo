package org.gmdev.springbootdemo.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@Document(collection = "Car")
public class CarModel {

    @Id
    private String id;

    private String name;
}
