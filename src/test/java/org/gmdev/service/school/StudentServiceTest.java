package org.gmdev.service.school;

import org.gmdev.api.model.school.CreateStudentApiReq;
import org.gmdev.api.model.school.StudentApiRes;
import org.gmdev.api.model.school.UpdateStudentApiReq;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    StudentRepository studentRepository;

    StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void itShouldFindOneStudent() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student(
                "test-name",
                timestamp,
                timestamp
        );
        Student savedStudent = studentRepository.save(student);

        // When
        StudentApiRes foundStudent = underTest.getOne(savedStudent.getId());

        // Then
        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getId()).isNotNull();
        assertThat(foundStudent.getName()).isEqualTo("test-name");
        assertThat(foundStudent.getStudentCourse()).hasSize(0);
        assertThat(foundStudent.getInsertTimestamp()).isEqualTo(timestamp);
        assertThat(foundStudent.getUpdateTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void itShouldThrowIfStudentNotFound() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student(
                "test-name",
                timestamp,
                timestamp
        );
        studentRepository.save(student);

        // When
        // Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Student with id: %d not found", 99));
    }

    @Test
    void itShouldFindAllStudents() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Student student1 = new Student(
                "name-1",
                timestamp,
                timestamp
        );
        Student student2 = new Student(
                "name-2",
                timestamp,
                timestamp
        );
        studentRepository.saveAll(List.of(student1, student2));

        // When
        List<StudentApiRes> allStudents = underTest.getAll();

        // Then
        assertThat(allStudents).hasSize(2);
        assertThat(allStudents.get(0).getName()).isEqualTo("name-1");
        assertThat(allStudents.get(1).getName()).isEqualTo("name-2");
    }

    @Test
    void itShouldSaveNewStudent() {
        // Given
        CreateStudentApiReq newStudent = new CreateStudentApiReq("test-name");

        // When
        StudentApiRes studentApiRes = underTest.addOne(newStudent);
        Student savedStudent = studentRepository.findById(studentApiRes.getId()).orElseThrow();

        // Then
        assertThat(savedStudent.getName()).isEqualTo("test-name");
    }

    @Test
    void itShouldUpdateStudent() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student(
                "name-1",
                timestamp,
                timestamp
        );
        Student savedStudent = studentRepository.save(student);
        Long studentId = savedStudent.getId();
        UpdateStudentApiReq updateStudentApiReq = new UpdateStudentApiReq("new-name");

        // When
        underTest.updateOne(studentId, updateStudentApiReq);
        Student updatedStudent = studentRepository.findById(studentId).orElseThrow();

        // Then
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getName()).isEqualTo("new-name");
    }

    @Test
    void itShouldDeleteStudent() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Student student = new Student(
                "name-1",
                timestamp,
                timestamp
        );
        Student savedStudent = studentRepository.save(student);
        Long studentId = savedStudent.getId();

        // When
        underTest.deleteOne(studentId);
        Optional<Student> studentMaybe = studentRepository.findById(studentId);
        List<Student> allStudents = studentRepository.findAll();

        // Then
        assertThat(studentMaybe).isEmpty();
        assertThat(allStudents).hasSize(0);
    }

}