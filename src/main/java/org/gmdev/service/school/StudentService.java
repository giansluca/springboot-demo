package org.gmdev.service.school;

import org.gmdev.api.model.school.CreateStudentApiReq;
import org.gmdev.api.model.school.StudentApiRes;
import org.gmdev.api.model.school.UpdateStudentApiReq;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentApiRes> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(Student::toListApiRes)
                .toList();
    }

    public StudentApiRes getOne(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> getStudentNotFoundException(studentId)).toApiRes();
    }

    public StudentApiRes addOne(CreateStudentApiReq bodyReq) {
        Student createdStudent = studentRepository.save(bodyReq.toEntity());
        return createdStudent.toApiRes();
    }

    public StudentApiRes updateOne(Long studentId, UpdateStudentApiReq bodyReq) {
        Student updatedCourse = studentRepository.findById(studentId)
                .map(studentInDb -> {
                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
                    studentInDb.setUpdatedAt(timestamp);
                    studentInDb.setName(bodyReq.getName());

                    return studentRepository.save(studentInDb);
                }).orElseThrow(() -> getStudentNotFoundException(studentId));

        return updatedCourse.toApiRes();
    }

    public void deleteOne(Long studentId) {
        if (!studentRepository.existsById(studentId))
            throw getStudentNotFoundException(studentId);

        studentRepository.deleteById(studentId);
    }

    private ResponseStatusException getStudentNotFoundException(Long studentId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Student with id: %d not found", studentId));
    }

}
