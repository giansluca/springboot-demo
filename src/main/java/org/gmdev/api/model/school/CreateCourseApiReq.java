package org.gmdev.api.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gmdev.model.entity.school.Course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class CreateCourseApiReq {

    @NotBlank
    @Size(max = 256)
    private final String title;

    public Course toEntity() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return new Course(title, now, now);
    }

}
