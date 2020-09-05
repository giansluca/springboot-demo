package org.gmdev.api.school;

import org.gmdev.model.dto.school.CourseDto;
import org.gmdev.model.entity.school.Course;
import org.gmdev.model.mapper.entitymapper.school.CourseMapper;
import org.gmdev.service.school.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/course")
@RestController
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public List<CourseDto> getAll() {
        return courseService.getAll()
                .stream()
                .map(courseMapper::toDtoLazy)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{courseId}")
    public CourseDto getOne(@PathVariable Long courseId) {
        Course course = courseService.getOne(courseId);
        return courseMapper.toDtoLazy(course);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseDto addOne(@Valid @NotNull @RequestBody CourseDto courseDto) {
        Course newCourse = courseService.addOne(courseMapper.toEntity(courseDto));
        return courseMapper.toDtoLazy(newCourse);
    }

    @PutMapping(path = "{courseId}")
    public CourseDto updateOne(@PathVariable Long courseId, @Valid @NotNull @RequestBody CourseDto courseDto) {
        Course updatedCourse = courseService.updateOne(courseId, courseMapper.toEntity(courseDto));
        return courseMapper.toDtoLazy(updatedCourse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{courseId}")
    public void deleteOne(@PathVariable Long courseId) {
        courseService.deleteOne(courseId);
    }
}
