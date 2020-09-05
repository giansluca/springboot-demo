package org.gmdev.model.mapper.entitymapper.school;

import org.gmdev.model.dto.school.CourseDto;
import org.gmdev.model.dto.school.StudentDto;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.entity.school.Student;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class EntityMapperResolver {

    @PersistenceContext
    private EntityManager em;

    @ObjectFactory
    public Course resolveCourse(CourseDto dto, @TargetType Class<Course> course) {
        return dto != null && dto.getId() != null ? em.find(course, dto.getId()) : new Course();
    }

    @ObjectFactory
    public Student resolveStudent(StudentDto dto, @TargetType Class<Student> student) {
        return dto != null && dto.getId() != null ? em.find(student, dto.getId()) : new Student();
    }

}
