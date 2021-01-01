package org.gmdev.model.mapper.entitymapper.school;

import org.gmdev.model.dto.school.StudentDto;
import org.gmdev.model.entity.school.Student;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { EntityMapperResolver.class, StudentCourseMapper.class } )
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(target = "studentCourse", qualifiedByName = "toStudentCourseDtoLazy")
    StudentDto toDto(Student student);

    @Named("toStudentDtoLazy")
    @Mapping(target = "studentCourse", ignore = true)
    StudentDto toDtoLazy(Student student);

    @Mapping(target = "studentCourse", qualifiedByName = "toEntityCollection")
    Student toEntity(StudentDto studentDto);

    default Student fromStudentId(Long studentId) {
        Student student = new Student();
        student.setId(studentId);
        return student;
    }

    @Named("toStudentEntityWithResolver")
    @BeanMapping(
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student toEntityWithResolver(StudentDto studentDto);

}
