package org.gmdev.api.school;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.school.CreateStudentApiReq;
import org.gmdev.api.model.school.StudentApiRes;
import org.gmdev.api.model.school.UpdateStudentApiReq;
import org.gmdev.model.entity.school.Student;
import org.gmdev.service.school.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("api/v1/student")
@Validated
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<StudentApiRes> getAll() {
        log.info("Incoming call to [StudentController - getAll]");
        return studentService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{studentId}")
    public StudentApiRes getOne(@PathVariable Long studentId) {
        log.info("Incoming call to [StudentController - getOne]");
        return studentService.getOne(studentId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StudentApiRes addOne(@Valid @NotNull @RequestBody CreateStudentApiReq createStudentApiReq) {
        log.info("Incoming call to [StudentController - addOne]");
        return studentService.addOne(createStudentApiReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{studentId}")
    public StudentApiRes updateOne(
            @PathVariable Long studentId,
            @Valid @NotNull @RequestBody UpdateStudentApiReq updateStudentApiReq) {

        log.info("Incoming call to [StudentController - updateOne]");
        return studentService.updateOne(studentId, updateStudentApiReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{studentId}")
    public void deleteOne(@PathVariable Long studentId) {
        log.info("Incoming call to [StudentController - deleteOne]");
        studentService.deleteOne(studentId);
    }

}
