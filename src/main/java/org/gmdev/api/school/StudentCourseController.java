package org.gmdev.api.school;

import org.gmdev.model.dto.school.StudentCourseDto;
import org.gmdev.service.school.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/studentcourse")
@Validated
@RestController
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @Autowired
    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @GetMapping(path = "/student/{id}")
    public List<StudentCourseDto> getStudentCourses(@PathVariable Long id) {
        return studentCourseService.getStudentCourses(id)
                .stream()
                .map(scMapper::toStudentCoursesDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/course/{id}")
    public List<StudentCourseDto> getCourseStudents(@PathVariable Long id) {
        return studentCourseService.getCourseStudents(id)
                .stream()
                .map(scMapper::toCourseStudentsDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StudentCourseDto addStudentToCourse(
            @Valid @NotNull @RequestBody StudentCourseDto studentCourseDto) {

        var newStudentCourse = studentCourseService
                .addStudentToCourse(scMapper.toEntity(studentCourseDto));

        return scMapper.toDto(newStudentCourse);
    }

    @PutMapping
    public StudentCourseDto updateStudentToCourse(
            @Valid @NotNull @RequestBody StudentCourseDto studentCourseDto) {

        var studentCourseUpdated = studentCourseService
                .updateStudentToCourse(scMapper.toEntity(studentCourseDto));

        return scMapper.toDto(studentCourseUpdated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteStudentFromCourse(
            @Valid @NotNull @RequestBody StudentCourseDto studentCourseDto) {

        studentCourseService.deleteStudentFromCourse(
                scMapper.toEntity(studentCourseDto));
    }

}
