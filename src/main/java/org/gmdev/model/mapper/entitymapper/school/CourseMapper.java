package org.gmdev.model.mapper.entitymapper.school;

import org.gmdev.model.dto.school.CourseDto;
import org.gmdev.model.entity.school.Course;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { EntityMapperResolver.class, StudentCourseMapper.class } )
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(target = "studentCourse", qualifiedByName = "toStudentCourseDtoLazy")
    CourseDto toDto(Course course);

    @Named("toCourseDtoLazy")
    @Mapping(target = "studentCourse", ignore = true)
    CourseDto toDtoLazy(Course course);

    @Mapping(target = "studentCourse", qualifiedByName = "toEntityCollection")
    Course toEntity(CourseDto courseDto);

    default Course fromCourseId(Long courseId) {
        Course course = new Course();
        course.setId(courseId);
        return course;
    }

    @Named("toCourseEntityWithResolver")
    @BeanMapping(
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course toEntityWithResolver(CourseDto courseDto);

}
