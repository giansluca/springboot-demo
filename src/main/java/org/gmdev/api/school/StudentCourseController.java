package org.gmdev.api.school;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.school.CourseStudentApiRes;
import org.gmdev.api.model.school.StudentCourseApiRes;
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
    public List<StudentCourseApiRes> getStudentCourses(@PathVariable Long studentId) {
        log.info("Incoming call --> [StudentCourseController - getStudentCourses]");
        return studentCourseService.getStudentCourses(studentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/course/{courseId}")
    public List<CourseStudentApiRes> getCourseStudents(@PathVariable Long courseId) {
        log.info("Incoming call --> [StudentCourseController - getCourseStudents]");
        return studentCourseService.getCourseStudents(courseId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addStudentToCourse(
            @Valid @NotNull @RequestBody UpdateStudentCourseApiReq bodyReq) {
        studentCourseService.addStudentCourse(bodyReq);
    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping
//    public StudentCourseApiRes updateStudentToCourse(
//            @Valid @NotNull @RequestBody StudentCourseApiRes studentCourseDto) {
//
//        var studentCourseUpdated = studentCourseService
//                .updateStudentToCourse(scMapper.toEntity(studentCourseDto));
//
//        return scMapper.toDto(studentCourseUpdated);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @DeleteMapping
//    public void deleteStudentFromCourse(
//            @Valid @NotNull @RequestBody StudentCourseApiRes studentCourseDto) {
//
//        studentCourseService.deleteStudentFromCourse(
//                scMapper.toEntity(studentCourseDto));
//    }

}
