package org.gmdev.api.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gmdev.dao.school.entity.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
