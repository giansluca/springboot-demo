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
        return new StudentApiRes(
                id,
                name,
                studentCourse.stream().map(StudentCourse::toApiRes).toList(),
                insertTimestamp,
                updateTimestamp
        );
    }

}
