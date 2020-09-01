package org.gmdev.springbootdemo.dao.school;

import org.gmdev.springbootdemo.model.entity.school.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
