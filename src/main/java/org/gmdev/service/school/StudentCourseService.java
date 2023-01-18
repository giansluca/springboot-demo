package org.gmdev.service.school;

import org.gmdev.api.model.school.CourseStudentApiRes;
import org.gmdev.api.model.school.StudentCourseApiRes;
import org.gmdev.api.model.school.UpdateStudentCourseApiReq;
import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository,
                                CourseRepository courseRepository,
                                StudentRepository studentRepository) {

        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentCourseApiRes> getStudentCourses(Long studentId) {
        return studentCourseRepository.findByStudentId(studentId)
                .stream()
                .map(StudentCourse::toStudentCourseApiRes)
                .toList();
    }

    public List<CourseStudentApiRes> getCourseStudents(Long courseId) {
        return studentCourseRepository.findByCourseId(courseId)
                .stream()
                .map(StudentCourse::toCourseStudentApiRes)
                .toList();
    }

    public StudentCourse addStudentCourse(UpdateStudentCourseApiReq updateStudentCourseApiReq) {
        checkStudentIsPresentOrThrow(updateStudentCourseApiReq.getStudentId());
        checkCourseIsPresentOrThrow(updateStudentCourseApiReq.getCourseId());

        if (studentCourseRepository.existsById(null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Student %d already enrolled course %d",
                            updateStudentCourseApiReq.getStudentId(),
                            updateStudentCourseApiReq.getCourseId()));

//        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
//        studentCourse.setCreatedAt(timestamp);
//
//        return studentCourseRepository.save(studentCourse);

        return null;
    }
//
//    public StudentCourse updateStudentToCourse(StudentCourse studentCourse) {
//        checkStudent(studentCourse.getId().getStudentId());
//        checkCourse(studentCourse.getId().getCourseId());
//
//        return studentCourseRepository.findById(studentCourse.getId())
//                .map(studentCourseInDb -> {
//                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
//                    studentCourseInDb.setUpdatedAt(timestamp);
//                    studentCourseInDb.setRating(studentCourse.getRating());
//
//                    return studentCourseRepository.save(studentCourseInDb);
//                })
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record to update not found"));
//    }
//
//    public void deleteStudentFromCourse(StudentCourse studentCourse) {
//        checkStudent(studentCourse.getId().getStudentId());
//        checkCourse(studentCourse.getId().getCourseId());
//
//        if (!studentCourseRepository.existsById(studentCourse.getId()))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record to delete not found");
//
//        studentCourseRepository.deleteById(studentCourse.getId());
//    }
//

    private void checkStudentIsPresentOrThrow(Long studentId) {
        if (!studentRepository.existsById(studentId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Student with id: %d not found", studentId));
    }

    private void checkCourseIsPresentOrThrow(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Course with id: %d not found", courseId));
    }

}
