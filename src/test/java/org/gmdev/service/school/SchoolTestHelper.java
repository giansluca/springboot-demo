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

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public SchoolTestHelper(StudentCourseRepository studentCourseRepository,
                            CourseRepository courseRepository,
                            StudentRepository studentRepository) {

        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public void cleanDb() {
        studentCourseRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    public void saveStudentList(List<Student> students) {
        studentRepository.saveAll(students);
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public void saveCourseList(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public void saveStudentCourseList(List<StudentCourse> studentCourses) {
        studentCourseRepository.saveAll(studentCourses);
    }

    public void saveStudentCourse(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourse> findAllStudentsCourses() {
        return studentCourseRepository.findAll();
    }

    public Optional<StudentCourse> findStudentCourseById(Long studentId, Long courseId) {
        return studentCourseRepository.findById(new StudentCourseKey(studentId, courseId));
    }


}
