package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gmdev.api.model.school.StudentApiRes;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "student")
public class Student {

    public Student(String name,
                   ZonedDateTime insertTimestamp,
                   ZonedDateTime updateTimestamp) {

        this.name = name;
        this.insertTimestamp = insertTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourse;

    @Column(name = "insert_timestamp")
    private ZonedDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;

    public StudentApiRes toApiRes() {
        StudentApiRes s = new StudentApiRes(
                id,
                name,
                studentCourse != null ? studentCourse.stream().map(StudentCourse::toApiRes).toList() : List.of(),
                insertTimestamp,
                updateTimestamp
        );

        return s;
    }

    public StudentApiRes toListApiRes() {
        return new StudentApiRes(
                id,
                name,
                studentCourse != null ? studentCourse.stream().map(StudentCourse::toListApiRes).toList() : List.of(),
                insertTimestamp,
                updateTimestamp
        );
    }

}
