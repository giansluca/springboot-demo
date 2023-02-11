package org.gmdev.api.school.model;

import lombok.*;
import org.gmdev.dao.school.entity.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class CreateStudentApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

    public Student toEntity() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return new Student(name, now, now);
    }

}
