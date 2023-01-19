package org.gmdev.api.school;

import lombok.extern.slf4j.Slf4j;
import org.gmdev.api.model.school.CourseApiRes;
import org.gmdev.api.model.school.CreateCourseApiReq;
import org.gmdev.api.model.school.UpdateCourseApiReq;
import org.gmdev.service.school.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequestMapping("api/v1/course")
@Validated
@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CourseApiRes> getAll() {
        log.info("Incoming call to [CourseController - getAll]");
        return courseService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{courseId}")
    public CourseApiRes getOne(@PathVariable Long courseId) {
        log.info("Incoming call to [CourseController - getOne]");
        return courseService.getOne(courseId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseApiRes addOne(
            @Valid @NotNull @RequestBody CreateCourseApiReq bodyReq) {

        log.info("Incoming call to [CourseController - addOne]");
        return courseService.addOne(bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{courseId}")
    public CourseApiRes updateOne(
            @PathVariable Long courseId,
            @Valid @NotNull @RequestBody UpdateCourseApiReq bodyReq) {

        log.info("Incoming call to [CourseController - updateOne]");
        return courseService.updateOne(courseId, bodyReq);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{courseId}")
    public void deleteOne(@PathVariable Long courseId) {
        log.info("Incoming call to [CourseController - deleteOne]");
        courseService.deleteOne(courseId);
    }

}
