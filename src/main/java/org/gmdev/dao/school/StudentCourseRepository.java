package org.gmdev.dao.school;

import org.gmdev.model.entity.school.StudentCourse;
import org.gmdev.model.entity.school.StudentCourseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseKey> {
}
