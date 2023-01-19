package org.gmdev.service.school;

import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.gmdev.model.entity.school.StudentCourseKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class SchoolTestHelper {

    @Autowired
    StudentCourseRepository studentCourseRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    //@Transactional
    void cleanDb() {
        studentCourseRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    void saveStudentList(List<Student> students) {
        studentRepository.saveAll(students);
    }

    void saveStudent(Student student) {
        studentRepository.save(student);
    }

    List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    Optional<Student> findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    void saveCourseList(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    void saveCourse(Course course) {
        courseRepository.save(course);
    }

    List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    void saveStudentCourseList(List<StudentCourse> studentCourses) {
        studentCourseRepository.saveAll(studentCourses);
    }

    void saveStudentCourse(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
    }

    List<StudentCourse> findAllStudentsCourses() {
        return studentCourseRepository.findAll();
    }

    Optional<StudentCourse> findStudentCourseById(Long studentId, Long courseId) {
        return studentCourseRepository.findById(new StudentCourseKey(studentId , courseId));
    }

}
