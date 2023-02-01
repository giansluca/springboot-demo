package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.school.model.GetCourseStudentApiRes;
import org.gmdev.api.school.model.GetStudentCourseApiRes;

import javax.persistence.*;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "student_course")
public class StudentCourse {

    public StudentCourse(Student student, Course course,
                         Integer rating, ZonedDateTime createdAt, ZonedDateTime updatedAt) {

        this.student = student;
        this.course = course;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @EmbeddedId
    private StudentCourseKey id = new StudentCourseKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public GetStudentCourseApiRes toStudentCourseApiRes() {
        return new GetStudentCourseApiRes(course.getId(), course.getTitle(), rating, createdAt);
    }

    public GetCourseStudentApiRes toCourseStudentApiRes() {
        return new GetCourseStudentApiRes(student.getId(), student.getName(), rating, createdAt);
    }


}
