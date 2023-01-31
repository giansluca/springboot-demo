package org.gmdev.service.school;

import org.gmdev.api.model.school.*;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        List<GetStudentCourseApiRes> studentCoursesS1 = underTest.getStudentCourses(student1.getId());
        List<GetStudentCourseApiRes> studentCoursesS2 = underTest.getStudentCourses(student2.getId());
        List<GetStudentCourseApiRes> studentCoursesS3 = underTest.getStudentCourses(student3.getId());

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
        StudentCourse sc3 = new StudentCourse(student2, course2, 9, now, now);      // Steven <--> Fishing course
        schoolTestHelper.saveStudentCourseList(List.of(sc1, sc2, sc3));

        // When
        List<GetCourseStudentApiRes> courseStudentsC1 = underTest.getCourseStudents(course1.getId());
        List<GetCourseStudentApiRes> courseStudentsC2 = underTest.getCourseStudents(course2.getId());
        List<GetCourseStudentApiRes> courseStudentsC3 = underTest.getCourseStudents(course3.getId());

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

        Long student1Id = students.get(0).getId();
        Long course2Id = courses.get(1).getId();

        CreateStudentCourseApiReq bodyReq =
                new CreateStudentCourseApiReq(student1Id, course2Id, 7);

        // When
        underTest.addStudentCourse(bodyReq);
        StudentCourse savedsStudentCourse =
                schoolTestHelper.findStudentCourseById(student1Id, course2Id).orElseThrow();

        // Then
        assertThat(savedsStudentCourse.getId().getStudentId()).isEqualTo(student1Id);
        assertThat(savedsStudentCourse.getId().getCourseId()).isEqualTo(course2Id);
    }

    @Test
    void itShouldThrowAddingNewStudentCourseIfStudentNotExits() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Long student1Id = 999L;
        Long course2Id = courses.get(1).getId();

        CreateStudentCourseApiReq bodyReq =
                new CreateStudentCourseApiReq(student1Id, course2Id, 7);

        // When Then
        assertThatThrownBy(() -> underTest.addStudentCourse(bodyReq))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Student with id: %d not found", student1Id));
    }

    @Test
    void itShouldThrowAddingNewStudentCourseIfCourseNotExits() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Long student1Id = students.get(0).getId();
        Long course2Id = 999L;

        CreateStudentCourseApiReq bodyReq =
                new CreateStudentCourseApiReq(student1Id, course2Id, 7);

        // When Then
        assertThatThrownBy(() -> underTest.addStudentCourse(bodyReq))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Course with id: %d not found", course2Id));
    }

    @Test
    void itShouldThrowIfStudentCourseAlreadyExists() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Course course2 = courses.get(1);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        schoolTestHelper.saveStudentCourse(new StudentCourse(student1, course2, 6, now, now));

        CreateStudentCourseApiReq bodyReq =
                new CreateStudentCourseApiReq(student1.getId(), course2.getId(), 7);

        // When Then
        assertThatThrownBy(() -> underTest.addStudentCourse(bodyReq))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        String.format("Student %s already enrolled course %s", student1.getId(), course2.getId()));
    }

    @Test
    void itShouldUpdateStudentCourse() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Course course2 = courses.get(1);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        schoolTestHelper.saveStudentCourse(new StudentCourse(student1, course2, 6, now, now));

        UpdateStudentCourseApiReq bodyReq =
                new UpdateStudentCourseApiReq(student1.getId(), course2.getId(), 10);

        // When
        underTest.updateStudentToCourse(bodyReq);
        StudentCourse updatedStudentCourse =
                schoolTestHelper.findStudentCourseById(student1.getId(), course2.getId()).orElseThrow();

        // Then
        assertThat(updatedStudentCourse).isNotNull();
        assertThat(updatedStudentCourse.getRating()).isEqualTo(10);
    }

    @Test
    void itShouldThrowUpdatingIfStudentCourseNotFound() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Long studentId = 998L;
        Long courseId = 999L;
        UpdateStudentCourseApiReq bodyReq =
                new UpdateStudentCourseApiReq(studentId, courseId, 10);

        // When Then
        assertThatThrownBy(() -> underTest.updateStudentToCourse(bodyReq))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        String.format("StudentCourse for Student %d and Course %d not found", studentId, courseId));
    }

    @Test
    void itShouldDeleteStudentCourse() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Student student1 = students.get(0);
        Course course2 = courses.get(1);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        schoolTestHelper.saveStudentCourse(new StudentCourse(student1, course2, 6, now, now));

        DeleteStudentCourseApiReq bodyReq =
                new DeleteStudentCourseApiReq(student1.getId(), course2.getId());

        // When
        underTest.deleteStudentFromCourse(bodyReq);
        Optional<StudentCourse> studentCourseMaybe =
                schoolTestHelper.findStudentCourseById(student1.getId(), course2.getId());
        Course courseAfterDeletion = schoolTestHelper.findCourseById(course2.getId()).orElseThrow();
        Student studentAfterDeletion = schoolTestHelper.findStudentById(student1.getId()).orElseThrow();

        List<StudentCourse> allStudentsCourses = schoolTestHelper.findAllStudentsCourses();
        // Then
        assertThat(studentCourseMaybe).isEmpty();
        assertThat(allStudentsCourses).hasSize(0);
        assertThat(courseAfterDeletion).isNotNull();
        assertThat(studentAfterDeletion).isNotNull();
    }

    @Test
    void itShouldThrowDeletingIfStudentCourseNotFound() {
        // Given
        List<Student> students = getFakeStudentEntities();
        schoolTestHelper.saveStudentList(students);
        List<Course> courses = getFakeCourseEntities();
        schoolTestHelper.saveCourseList(courses);

        Long studentId = 998L;
        Long courseId = 999L;

        DeleteStudentCourseApiReq bodyReq =
                new DeleteStudentCourseApiReq(studentId, courseId);

        // Whe Then
        assertThatThrownBy(() -> underTest.deleteStudentFromCourse(bodyReq))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(
                        String.format("StudentCourse for Student %d and Course %d not found", studentId, courseId));
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