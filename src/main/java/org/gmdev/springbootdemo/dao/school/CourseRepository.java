package org.gmdev.springbootdemo.dao.school;

import org.gmdev.springbootdemo.model.entity.school.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
