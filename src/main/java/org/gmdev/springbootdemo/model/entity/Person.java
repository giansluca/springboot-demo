package org.gmdev.springbootdemo.model.entity;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Person {

    private UUID id;
    private String name;
}
