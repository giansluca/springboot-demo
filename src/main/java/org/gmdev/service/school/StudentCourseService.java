package org.gmdev.service.school;

import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Service
@Transactional
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository,
                         StudentRepository studentRepository,
                         CourseRepository courseRepository) {

        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Set<StudentCourse> getStudentCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Student with id: %d not found", studentId)));

        //return student.getStudentCourse();
        return null;
    }

    public Set<StudentCourse> getCourseStudents(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with id: %d not found", courseId)));

        //return course.getStudentCourse();
        return null;
    }

    public StudentCourse addStudentToCourse(StudentCourse studentCourse) {
        checkStudent(studentCourse.getId().getStudentId());
        checkCourse(studentCourse.getId().getCourseId());

        if (studentCourseRepository.existsById(studentCourse.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Student %d already enrolled course %d",
                            studentCourse.getId().getStudentId(),
                            studentCourse.getId().getCourseId()));

        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        studentCourse.setInsertTimestamp(timestamp);

        return studentCourseRepository.save(studentCourse);
    }

    public StudentCourse updateStudentToCourse(StudentCourse studentCourse) {
        checkStudent(studentCourse.getId().getStudentId());
        checkCourse(studentCourse.getId().getCourseId());

        return studentCourseRepository.findById(studentCourse.getId())
                .map(studentCourseInDb -> {
                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
                    studentCourseInDb.setUpdateTimestamp(timestamp);
                    studentCourseInDb.setRating(studentCourse.getRating());

                    return studentCourseRepository.save(studentCourseInDb);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record to update not found"));
    }

    public void deleteStudentFromCourse(StudentCourse studentCourse) {
        checkStudent(studentCourse.getId().getStudentId());
        checkCourse(studentCourse.getId().getCourseId());

        if (!studentCourseRepository.existsById(studentCourse.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record to delete not found");

        studentCourseRepository.deleteById(studentCourse.getId());
    }

    private void checkStudent(Long studentId) {
        if (!studentRepository.existsById(studentId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Student with id: %d not found", studentId));
    }

    private void checkCourse(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Course with id: %d not found", courseId));
    }

}
