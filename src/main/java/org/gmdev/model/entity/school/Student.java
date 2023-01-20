package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.model.school.GetStudentApiRes;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "student")
public class Student {

    public Student(String name,
                   ZonedDateTime createdAt,
                   ZonedDateTime updatedAt) {

        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourses;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public GetStudentApiRes toApiRes() {
        return new GetStudentApiRes(
                id,
                name,
                studentCourses != null
                        ? studentCourses.stream().map(StudentCourse::toStudentCourseApiRes).toList()
                        : List.of(),
                createdAt,
                updatedAt
        );
    }

    public GetStudentApiRes toListApiRes() {
        return new GetStudentApiRes(
                id,
                name,
                null,
                createdAt,
                updatedAt
        );
    }

}
