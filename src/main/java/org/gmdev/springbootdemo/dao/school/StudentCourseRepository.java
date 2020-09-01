package org.gmdev.springbootdemo.dao.school;

import org.gmdev.springbootdemo.model.entity.school.StudentCourse;
import org.gmdev.springbootdemo.model.entity.school.StudentCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseKey> {
}
