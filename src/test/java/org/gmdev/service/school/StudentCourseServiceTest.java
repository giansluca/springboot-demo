package org.gmdev.service.school;

import org.gmdev.api.model.school.StudentCourseApiRes;
import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.gmdev.model.entity.school.StudentCourseKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudentCourseServiceTest {

    @Autowired
    StudentCourseRepository studentCourseRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    StudentCourseService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentCourseService(studentCourseRepository);
    }

    @Test
    void itShouldFindStudentCourses() {
        // Given
        List<Student> students = getFakeStudentEntities();
        List<Course> courses = getFakeCourseEntities();
        studentRepository.saveAll(students);
        courseRepository.saveAll(courses);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        StudentCourse mark1 = new StudentCourse(students.get(0), courses.get(0), 7, now, now);      // Mark <--> Kitchen course
        StudentCourse mark2 = new StudentCourse(students.get(0), courses.get(1), 5, now, now);      // Mark <--> Fishing course
        StudentCourse steven1 = new StudentCourse(students.get(2), courses.get(1), 9, now, now);    // Steven <--> Fishing course
        studentCourseRepository.saveAll(List.of(mark1, mark2, steven1));

        // When
        List<StudentCourseApiRes> studentCourses = underTest.getStudentCourses(1L);

        // Then
        assertThat(studentCourses).isNotEmpty();
        assertThat(studentCourses).hasSize(2);
        assertThat(studentCourses.get(0).getTitle()).isEqualTo("Kitchen course");
        assertThat(studentCourses.get(1).getTitle()).isEqualTo("Fishing course");
    }

    private List<Student> getFakeStudentEntities() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return List.of(
                new Student("Mark", now, now),        // id generated is 1
                new Student("Steven", now, now),      // id generated is 2
                new Student("Tom", now, now)          // id generated is 3
        );
    }

    private List<Course> getFakeCourseEntities() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return List.of(
                new Course("Kitchen course", now, now),         // id generated is 1
                new Course("Fishing course", now, now),         // id generated is 2
                new Course("Programming course", now, now)      // id generated is 3
        );
    }


}