package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.model.school.CourseApiRes;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    public Course(String title, ZonedDateTime insertTimestamp, ZonedDateTime updateTimestamp) {
        this.title = title;
        this.insertTimestamp = insertTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourse;

    @Column(name = "insert_timestamp")
    private ZonedDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;

    public CourseApiRes toApiRes() {
        return new CourseApiRes(
                id,
                title,
                studentCourse != null ? studentCourse.stream().map(StudentCourse::toApiRes).toList() : null,
                insertTimestamp,
                updateTimestamp
        );
    }

}
