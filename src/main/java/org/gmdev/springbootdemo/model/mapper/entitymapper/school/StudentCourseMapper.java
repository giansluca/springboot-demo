package org.gmdev.springbootdemo.model.mapper.entitymapper.school;

import org.gmdev.springbootdemo.model.dto.school.StudentCourseDto;
import org.gmdev.springbootdemo.model.entity.school.StudentCourse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { StudentMapper.class, CourseMapper.class })
public interface StudentCourseMapper {

    @Named("toEntity")
    @Mapping(target = "id.studentId", source = "studentId")
    @Mapping(target = "id.courseId", source = "courseId")
    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "course", source = "courseId")
    StudentCourse toEntity(StudentCourseDto studentCourseDto);

    @Named("toEntityWithResolver")
    @Mapping(target = "student", qualifiedByName = "toStudentEntityWithResolver")
    @Mapping(target = "course", qualifiedByName = "toCourseEntityWithResolver")
    @Mapping(target = "id.studentId", source = "student.id")
    @Mapping(target = "id.courseId", source = "course.id")
    StudentCourse toEntityWithResolver(StudentCourseDto studentCourseDto);

    @Mapping(target = "studentId", source = "id.studentId")
    @Mapping(target = "courseId", source = "id.courseId")
    @Mapping(target = "student", qualifiedByName = "toStudentDtoLazy")
    @Mapping(target = "course", qualifiedByName = "toCourseDtoLazy")
    StudentCourseDto toDto(StudentCourse studentCourse);

    @Named("toStudentCourseDtoLazy")
    @Mapping(target = "studentId", source = "id.studentId")
    @Mapping(target = "courseId", source = "id.courseId")
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    StudentCourseDto toDtoLazy(StudentCourse studentCourse);

    @Mapping(target = "studentId", source = "id.studentId")
    @Mapping(target = "courseId", source = "id.courseId")
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", qualifiedByName = "toCourseDtoLazy")
    StudentCourseDto toStudentCoursesDto(StudentCourse studentCourse);

    @Mapping(target = "studentId", source = "id.studentId")
    @Mapping(target = "courseId", source = "id.courseId")
    @Mapping(target = "student", qualifiedByName = "toStudentDtoLazy")
    @Mapping(target = "course", ignore = true)
    StudentCourseDto toCourseStudentsDto(StudentCourse studentCourse);
}
