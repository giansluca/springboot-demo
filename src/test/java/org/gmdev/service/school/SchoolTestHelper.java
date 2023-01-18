package org.gmdev.service.school;

import org.gmdev.dao.school.CourseRepository;
import org.gmdev.dao.school.StudentCourseRepository;
import org.gmdev.dao.school.StudentRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.gmdev.model.entity.school.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class SchoolTestHelper {

    @Autowired
    StudentCourseRepository studentCourseRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    @Transactional
    void cleanDb() {
        studentCourseRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Transactional
    void saveStudentList(List<Student> students) {
        studentRepository.saveAll(students);
    }

    @Transactional
    void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Transactional
    Optional<Student> findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Transactional
    List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Transactional
    List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    void saveCourseList(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    @Transactional
    void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Transactional
    void saveStudentCourseList(List<StudentCourse> studentCourses) {
        studentCourseRepository.saveAll(studentCourses);
    }

    @Transactional
    void saveStudentCourse(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
    }


}
