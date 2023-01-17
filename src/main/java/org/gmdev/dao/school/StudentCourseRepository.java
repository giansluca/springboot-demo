package org.gmdev.dao.school;

import org.gmdev.model.entity.school.StudentCourse;
import org.gmdev.model.entity.school.StudentCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseKey> {

    List<StudentCourse> findByStudentId(Long studentId);

    List<StudentCourse> findByCourseId(Long courseId);

}
