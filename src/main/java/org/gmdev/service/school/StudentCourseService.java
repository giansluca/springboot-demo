package org.gmdev.service.school;

import org.gmdev.api.model.school.*;
import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.gmdev.model.entity.school.StudentCourseKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    public List<GetStudentCourseApiRes> getStudentCourses(Long studentId) {
        return studentCourseRepository.findByStudentId(studentId)
                .stream()
                .map(StudentCourse::toStudentCourseApiRes)
                .toList();
    }

    public List<GetCourseStudentApiRes> getCourseStudents(Long courseId) {
        return studentCourseRepository.findByCourseId(courseId)
                .stream()
                .map(StudentCourse::toCourseStudentApiRes)
                .toList();
    }

    public void addStudentCourse(CreateStudentCourseApiReq bodyReq) {
        Long studentId = bodyReq.getStudentId();
        Long courseId = bodyReq.getCourseId();

        Student student = getStudentOrThrow(studentId);
        Course course = getCourseOrThrow(courseId);

        StudentCourseKey studentCourseId = new StudentCourseKey(studentId, courseId);

        if (studentCourseRepository.existsById(studentCourseId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Student %d already enrolled course %d",
                            bodyReq.getStudentId(),
                            bodyReq.getCourseId()));

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Z"));
        StudentCourse studentCourse = new StudentCourse(
                student, course, bodyReq.getRating(), now, now);

        studentCourseRepository.save(studentCourse);
    }

    public void updateStudentToCourse(UpdateStudentCourseApiReq bodyReq) {
        Long studentId = bodyReq.getStudentId();
        Long courseId = bodyReq.getCourseId();

        StudentCourseKey studentCourseId = new StudentCourseKey(studentId, courseId);
        studentCourseRepository.findById(studentCourseId)
                .map(studentCourseInDb -> {
                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
                    studentCourseInDb.setUpdatedAt(timestamp);
                    studentCourseInDb.setRating(bodyReq.getRating());

                    return studentCourseRepository.save(studentCourseInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StudentCourse for Student %d and Course %d not found"));
    }

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

    private Student getStudentOrThrow(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Student with id: %d not found", studentId)));
    }

    private Course getCourseOrThrow(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with id: %d not found", courseId)));
    }


}
