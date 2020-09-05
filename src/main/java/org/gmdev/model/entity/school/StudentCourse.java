package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter @Setter
@Entity
@Table(name = "student_course")
public class StudentCourse {

    @EmbeddedId
    private StudentCourseKey id;

    @ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "rating" )
    private Long rating;

    @Column(name = "insert_timestamp")
    private ZonedDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;
}
