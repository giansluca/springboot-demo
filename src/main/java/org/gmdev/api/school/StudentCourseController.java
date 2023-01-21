package org.gmdev.api.school;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.school.GetCourseStudentApiRes;
import org.gmdev.api.model.school.GetStudentCourseApiRes;
import org.gmdev.api.model.school.CreateStudentCourseApiReq;
import org.gmdev.api.model.school.UpdateStudentCourseApiReq;
import org.gmdev.service.school.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/studentcourse")
@Validated
@RestController
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @Autowired
    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/student/{studentId}")
    public List<GetStudentCourseApiRes> getStudentCourses(@PathVariable Long studentId) {
        log.info("Incoming call --> [StudentCourseController - getStudentCourses]");
        return studentCourseService.getStudentCourses(studentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/course/{courseId}")
    public List<GetCourseStudentApiRes> getCourseStudents(@PathVariable Long courseId) {
        log.info("Incoming call --> [StudentCourseController - getCourseStudents]");
        return studentCourseService.getCourseStudents(courseId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addStudentToCourse(
            @Valid @NotNull @RequestBody CreateStudentCourseApiReq bodyReq) {
        log.info("Incoming call --> [StudentCourseController - addStudentToCourse]");
        studentCourseService.addStudentCourse(bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updateStudentToCourse(
            @Valid @NotNull @RequestBody UpdateStudentCourseApiReq bodyReq) {
        log.info("Incoming call --> [StudentCourseController - updateStudentToCourse]");
        studentCourseService.updateStudentToCourse(bodyReq);
    }



//
//    @ResponseStatus(HttpStatus.OK)
//    @DeleteMapping
//    public void deleteStudentFromCourse(
//            @Valid @NotNull @RequestBody GetStudentCourseApiRes studentCourseDto) {
//
//        studentCourseService.deleteStudentFromCourse(
//                scMapper.toEntity(studentCourseDto));
//    }



}
