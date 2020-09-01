package org.gmdev.springbootdemo.model.entity.school;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "insert_timestamp")
    private ZonedDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    private ZonedDateTime updateTimestamp;

    @OneToMany(mappedBy = "course")
    private Set<StudentCourse> studentCourse;
}
