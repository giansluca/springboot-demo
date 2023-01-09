package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gmdev.model.entity.school.Course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter @NoArgsConstructor
public class CreateCourseApiReq {

    @NotBlank
    @Size(max = 256)
    private String title;

    public Course toEntity() {
        return new Course(
                title,
                ZonedDateTime.now(ZoneId.of("Z")),
                ZonedDateTime.now(ZoneId.of("Z")));
    }

}
