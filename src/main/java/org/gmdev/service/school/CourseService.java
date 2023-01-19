package org.gmdev.service.school;

import org.gmdev.api.model.school.CreateCourseApiReq;
import org.gmdev.api.model.school.UpdateCourseApiReq;
import org.gmdev.dao.school.CourseRepository;
import org.gmdev.api.model.school.CourseApiRes;
import org.gmdev.model.entity.school.Course;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseApiRes> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(Course::toListApiRes)
                .toList();
    }

    public CourseApiRes getOne(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> getCourseNotFoundException(courseId)).toApiRes();
    }

    public Long addOne(CreateCourseApiReq bodyReq) {
        Course createdCourse = courseRepository.save(bodyReq.toEntity());
        return createdCourse.getId();
    }

    public CourseApiRes updateOne(Long courseId, UpdateCourseApiReq bodyReq) {
        Course updatedCourse = courseRepository.findById(courseId)
                .map(courseInDb -> {
                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
                    courseInDb.setUpdatedAt(timestamp);
                    courseInDb.setTitle(bodyReq.getTitle());

                    return courseRepository.save(courseInDb);
                }).orElseThrow(() -> getCourseNotFoundException(courseId));

        return updatedCourse.toApiRes();
    }

    public void deleteOne(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw getCourseNotFoundException(courseId);

        courseRepository.deleteById(courseId);
    }

    private ResponseStatusException getCourseNotFoundException(Long courseId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Course with id: %d not found", courseId));
    }

}
