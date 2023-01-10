package org.gmdev.service.school;

import org.gmdev.api.model.school.CreateCourseApiReq;
import org.gmdev.dao.school.CourseRepository;
import org.gmdev.model.entity.school.Course;
import org.gmdev.setup.PostgresSetUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
class CourseServiceTest extends PostgresSetUp {

    @Autowired
    private CourseRepository courseRepository;

    private CourseService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CourseService(courseRepository);
    }

    @Test
    void itShouldSaveNewCourse() {
        // Given
        CreateCourseApiReq newCourse = new CreateCourseApiReq("test-title");

        // When
        underTest.addOne(newCourse);
        List<Course> allCourses = courseRepository.findAll();

        // Then
        assertThat(allCourses.size()).isEqualTo(1);
        courseRepository.getReferenceById(1L);

        Course savedCourse = courseRepository.getReferenceById(allCourses.get(0).getId());
        assertThat(savedCourse.getTitle()).isEqualTo("test-title");
    }


}