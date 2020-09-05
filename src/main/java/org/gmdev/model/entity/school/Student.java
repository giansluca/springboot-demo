package org.gmdev.model.entity.school;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "insert_timestamp")
    private ZonedDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;

    @OneToMany(mappedBy = "student")
    private Set<StudentCourse> studentCourse;
}
