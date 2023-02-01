package org.gmdev.service.school;

import org.gmdev.api.school.model.CreateStudentApiReq;
import org.gmdev.api.school.model.GetStudentApiRes;
import org.gmdev.api.school.model.UpdateStudentApiReq;
import org.gmdev.model.entity.school.Student;
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
class StudentServiceTest {

    @Autowired
    SchoolTestHelper schoolTestHelper;

    @Autowired
    StudentService underTest;

    @AfterEach
    void cleanUp() {
        schoolTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneStudent() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student("test-name", now, now);
        schoolTestHelper.saveStudent(student);

        // When
        GetStudentApiRes foundStudent = underTest.getOne(student.getId());

        // Then
        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getStudentId()).isNotNull();
        assertThat(foundStudent.getName()).isEqualTo("test-name");
        assertThat(foundStudent.getStudentCourses()).hasSize(0);
        assertThat(foundStudent.getCreatedAt()).isEqualTo(now);
        assertThat(foundStudent.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void itShouldThrowIfStudentNotFound() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student("test-name", now, now);
        schoolTestHelper.saveStudent(student);

        // When
        // Then
        assertThatThrownBy(() -> underTest.getOne(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Student with id: %d not found", 999));
    }

    @Test
    void itShouldFindAllStudents() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Student student1 = new Student("name-1", now, now);
        Student student2 = new Student("name-2", now, now);
        schoolTestHelper.saveStudentList(List.of(student1, student2));

        // When
        List<GetStudentApiRes> allStudents = underTest.getAll();

        // Then
        assertThat(allStudents).hasSize(2);
        assertThat(allStudents.get(0).getName()).isEqualTo("name-1");
        assertThat(allStudents.get(1).getName()).isEqualTo("name-2");
    }

    @Test
    void itShouldSaveNewStudent() {
        // Given
        CreateStudentApiReq bodyReq = new CreateStudentApiReq("test-name");

        // When
        Long studentId = underTest.addOne(bodyReq);
        Student savedStudent = schoolTestHelper.findStudentById(studentId).orElseThrow();

        // Then
        assertThat(savedStudent.getName()).isEqualTo("test-name");
    }

    @Test
    void itShouldUpdateStudent() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student("name-1", now, now);
        schoolTestHelper.saveStudent(student);
        Long studentId = student.getId();
        UpdateStudentApiReq bodyReq = new UpdateStudentApiReq("new-name");

        // When
        underTest.updateOne(studentId, bodyReq);
        Student updatedStudent = schoolTestHelper.findStudentById(studentId).orElseThrow();

        // Then
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getName()).isEqualTo("new-name");
    }

    @Test
    void itShouldDeleteStudent() {
        // Given
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student("name-1", now, now);
        schoolTestHelper.saveStudent(student);
        Long studentId = student.getId();

        // When
        underTest.deleteOne(studentId);
        Optional<Student> studentMaybe = schoolTestHelper.findStudentById(studentId);
        List<Student> allStudents = schoolTestHelper.findAllStudents();

        // Then
        assertThat(studentMaybe).isEmpty();
        assertThat(allStudents).hasSize(0);
    }

}