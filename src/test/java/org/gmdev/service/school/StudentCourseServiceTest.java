package org.gmdev.service.school;

import org.gmdev.api.model.school.CourseStudentApiRes;
import org.gmdev.api.model.school.StudentCourseApiRes;
import org.gmdev.api.model.school.UpdateStudentCourseApiReq;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentCourseServiceTest {

    @Autowired
    SchoolTestHelper schoolTestHelper;

    @Autowired
    StudentCourseService underTest;

    @AfterEach
    void cleanUp() {
        schoolTestHelper.cleanDb();
    }

    @Test
    void itShouldFindStudentCourses() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Course course1 = courses.get(0);
        Course course2 = courses.get(1);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        StudentCourse mark1 = new StudentCourse(student1, course1, 7, now, now);      // Mark <--> Kitchen course
        StudentCourse mark2 = new StudentCourse(student1, course2, 5, now, now);      // Mark <--> Fishing course
        StudentCourse steven1 = new StudentCourse(student2, course2, 9, now, now);    // Steven <--> Fishing course
        schoolTestHelper.saveStudentCourseList(List.of(mark1, mark2, steven1));

        // When
        List<StudentCourseApiRes> studentCoursesS1 = underTest.getStudentCourses(student1.getId());
        List<StudentCourseApiRes> studentCoursesS2 = underTest.getStudentCourses(student2.getId());
        List<StudentCourseApiRes> studentCoursesS3 = underTest.getStudentCourses(student3.getId());

        // Then
        assertThat(studentCoursesS1).isNotEmpty().hasSize(2);
        assertThat(studentCoursesS1.get(0).getCourseId()).isEqualTo(course1.getId());
        assertThat(studentCoursesS1.get(0).getTitle()).isEqualTo("Kitchen course");
        assertThat(studentCoursesS1.get(1).getCourseId()).isEqualTo(course2.getId());
        assertThat(studentCoursesS1.get(1).getTitle()).isEqualTo("Fishing course");

        assertThat(studentCoursesS2).isNotEmpty().hasSize(1);
        assertThat(studentCoursesS2.get(0).getCourseId()).isEqualTo(course2.getId());
        assertThat(studentCoursesS2.get(0).getTitle()).isEqualTo("Fishing course");

        assertThat(studentCoursesS3).isEmpty();
    }

    @Test
    void itShouldFindCourseStudents() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Course course1 = courses.get(0);
        Course course2 = courses.get(1);
        Course course3 = courses.get(2);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        StudentCourse sc1 = new StudentCourse(student1, course1, 7, now, now);      // Mark <--> Kitchen course
        StudentCourse sc2 = new StudentCourse(student1, course2, 5, now, now);      // Mark <--> Fishing course
        StudentCourse sc3 = new StudentCourse(student2, course2, 9, now, now);    // Steven <--> Fishing course
        schoolTestHelper.saveStudentCourseList(List.of(sc1, sc2, sc3));

        // When
        List<CourseStudentApiRes> courseStudentsC1 = underTest.getCourseStudents(course1.getId());
        List<CourseStudentApiRes> courseStudentsC2 = underTest.getCourseStudents(course2.getId());
        List<CourseStudentApiRes> courseStudentsC3 = underTest.getCourseStudents(course3.getId());

        // Then
        assertThat(courseStudentsC1).isNotEmpty().hasSize(1);
        assertThat(courseStudentsC1.get(0).getStudentId()).isEqualTo(student1.getId());
        assertThat(courseStudentsC1.get(0).getName()).isEqualTo("Mark");

        assertThat(courseStudentsC2).isNotEmpty().hasSize(2);
        assertThat(courseStudentsC2.get(0).getStudentId()).isEqualTo(student1.getId());
        assertThat(courseStudentsC2.get(0).getName()).isEqualTo("Mark");
        assertThat(courseStudentsC2.get(1).getStudentId()).isEqualTo(student2.getId());
        assertThat(courseStudentsC2.get(1).getName()).isEqualTo("Steven");

        assertThat(courseStudentsC3).isEmpty();
    }

    @Test
    void itShouldAddNewStudentCourse() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Course course2 = courses.get(1);

        UpdateStudentCourseApiReq newStudentCourse =
                new UpdateStudentCourseApiReq(student1.getId(), course2.getId());

        // When
        underTest.addStudentCourse(newStudentCourse);

        // Then
    }

    @Test
    void itShouldThrowAddingNewNewStudentCourseIfStudentOrCourseNotExits() {
        // Given

        // When

        // Then
    }

    @Test
    void itShouldThrowIfStudentCourseAlreadyExists() {
        // Given

        // When

        // Then
    }

    private List<Student> getFakeStudentEntities() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return List.of(
                new Student("Mark", now, now),
                new Student("Steven", now, now),
                new Student("Tom", now, now)
        );
    }

    private List<Course> getFakeCourseEntities() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        return List.of(
                new Course("Kitchen course", now, now),
                new Course("Fishing course", now, now),
                new Course("Programming course", now, now)
        );
    }


}