package org.gmdev.service.school;

import org.gmdev.api.school.model.CreateStudentApiReq;
import org.gmdev.api.school.model.GetStudentApiRes;
import org.gmdev.api.school.model.UpdateStudentApiReq;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.dao.school.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
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

    public List<GetStudentApiRes> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(Student::toListApiRes)
                .toList();
    }

    public GetStudentApiRes getOne(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> getStudentNotFoundException(studentId)).toApiRes();
    }

    public Long addOne(CreateStudentApiReq bodyReq) {
        Student createdStudent = studentRepository.save(bodyReq.toEntity());
        return createdStudent.getId();
    }

    public GetStudentApiRes updateOne(Long studentId, UpdateStudentApiReq bodyReq) {
        Student updatedCourse = studentRepository.findById(studentId)
                .map(studentInDb -> {
                    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
                    studentInDb.setUpdatedAt(now);

                    if (bodyReq.getName() != null)
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
