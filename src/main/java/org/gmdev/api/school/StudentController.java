package org.gmdev.api.school;

import org.gmdev.model.dto.school.StudentDto;
import org.gmdev.model.entity.school.Student;
import org.gmdev.service.school.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/student")
@Validated
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getAll() {
        return studentService.getAll()
                .stream()
                .map(studentMapper::toDtoLazy)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{studentId}")
    public StudentDto getOne(@PathVariable Long studentId) {
        Student student = studentService.getOne(studentId);
        return studentMapper.toDtoLazy(student);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StudentDto addOne(@Valid @NotNull @RequestBody StudentDto studentDto) {
        Student newStudent = studentService.addOne(studentMapper.toEntity(studentDto));
        return studentMapper.toDtoLazy(newStudent);
    }

    @PutMapping(path = "{studentId}")
    public StudentDto updateOne(@PathVariable Long studentId, @Valid @NotNull @RequestBody StudentDto studentDto) {
        Student updatedStudent = studentService.updateOne(studentId, studentMapper.toEntity(studentDto));
        return studentMapper.toDtoLazy(updatedStudent);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{studentId}")
    public void deleteOne(@PathVariable Long studentId) {
        studentService.deleteOne(studentId);
    }
}
