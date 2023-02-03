package org.gmdev.model.entity.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.gmdev.api.car.model.GetCarApiRes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@Document(collection = "Car")
public class Car {

    @Id
    private String id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetCarApiRes toApiRes() {
        return new GetCarApiRes(id, name, createdAt, updatedAt);
    }

}
