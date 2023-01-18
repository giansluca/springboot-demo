package org.gmdev.api.model.school;

import lombok.*;
import org.gmdev.model.entity.school.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class CreateStudentApiReq {

    @NotBlank
    @Size(max = 64)
    private final String name;

    public Student toEntity() {
        return new Student(
                name,
                ZonedDateTime.now(ZoneId.of("Z")),
                ZonedDateTime.now(ZoneId.of("Z")));
    }

}
