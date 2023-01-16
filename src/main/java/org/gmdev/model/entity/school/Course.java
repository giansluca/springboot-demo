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

    public Course(String title,
                  ZonedDateTime createdAt,
                  ZonedDateTime updatedAt) {

        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourses;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public CourseApiRes toApiRes() {
        return new CourseApiRes(
                id,
                title,
                studentCourses != null
                        ? studentCourses.stream().map(StudentCourse::toCourseStudentApiRes).toList()
                        : List.of(),
                createdAt,
                updatedAt
        );
    }

    public CourseApiRes toListApiRes() {
        return new CourseApiRes(
                id,
                title,
                null,
                createdAt,
                updatedAt
        );
    }

}
