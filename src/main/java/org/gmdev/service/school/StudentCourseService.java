package org.gmdev.service.school;

import org.gmdev.api.model.school.CourseStudentApiRes;
import org.gmdev.api.model.school.StudentCourseApiRes;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.model.entity.school.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
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

//
//    public StudentCourse addStudentToCourse(StudentCourse studentCourse) {
//        checkStudent(studentCourse.getId().getStudentId());
//        checkCourse(studentCourse.getId().getCourseId());
//
//        if (studentCourseRepository.existsById(studentCourse.getId()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    String.format("Student %d already enrolled course %d",
//                            studentCourse.getId().getStudentId(),
//                            studentCourse.getId().getCourseId()));
//
//        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
//        studentCourse.setCreatedAt(timestamp);
//
//        return studentCourseRepository.save(studentCourse);
//    }
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
//    private void checkStudent(Long studentId) {
//        if (!studentRepository.existsById(studentId))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    String.format("Student with id: %d not found", studentId));
//    }
//
//    private void checkCourse(Long courseId) {
//        if (!courseRepository.existsById(courseId))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    String.format("Course with id: %d not found", courseId));
//    }

}
