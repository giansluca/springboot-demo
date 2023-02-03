package org.gmdev.api.car.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class GetCarApiRes {

    private final String id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
