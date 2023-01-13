package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.model.school.StudentCourseApiRes;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "student_course")
public class StudentCourse {

    public StudentCourse(Long rating,
                         ZonedDateTime insertTimestamp,
                         ZonedDateTime updateTimestamp) {

        this.rating = rating;
        this.insertTimestamp = insertTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

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

    public StudentCourseApiRes toApiRes() {
        return new StudentCourseApiRes(id.getStudentId(), id.getCourseId(), rating, insertTimestamp, updateTimestamp);
    }

    public StudentCourseApiRes toListApiRes() {
        return new StudentCourseApiRes(id.getStudentId(), id.getCourseId(), rating, null, null);
    }

}
