package org.gmdev.springbootdemo.service.school;

import org.gmdev.springbootdemo.dao.school.CourseRepository;
import org.gmdev.springbootdemo.model.entity.school.Course;
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

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getOne(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> getCourseNotFoundException(courseId));
    }

    public Course addOne(Course course) {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        course.setInsertTimestamp(timestamp);

        return courseRepository.save(course);
    }

    public Course updateOne(Long courseId, Course course) {
        return courseRepository.findById(courseId)
                .map(courseInDb -> {
                    ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
                    courseInDb.setUpdateTimestamp(timestamp);
                    courseInDb.setTitle(course.getTitle());

                    return courseRepository.save(courseInDb);
                }).orElseThrow(() -> getCourseNotFoundException(courseId));
    }

    public void deleteOne(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw getCourseNotFoundException(courseId);

        courseRepository.deleteById(courseId);
    }

    private ResponseStatusException getCourseNotFoundException(Long courseId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Course with id %d: not found", courseId));
    }

}
