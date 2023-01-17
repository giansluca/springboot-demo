package org.gmdev.api.school;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.school.StudentCourseApiRes;
import org.gmdev.service.school.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping(path = "/course/{id}")
//    public List<StudentCourseApiRes> getCourseStudents(@PathVariable Long id) {
//        return studentCourseService.getCourseStudents(id)
//                .stream()
//                .map(scMapper::toCourseStudentsDto)
//                .collect(Collectors.toList());
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public StudentCourseApiRes addStudentToCourse(
//            @Valid @NotNull @RequestBody StudentCourseApiRes studentCourseDto) {
//
//        var newStudentCourse = studentCourseService
//                .addStudentToCourse(scMapper.toEntity(studentCourseDto));
//
//        return scMapper.toDto(newStudentCourse);
//    }
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
