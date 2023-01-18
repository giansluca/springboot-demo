package org.gmdev.service.school;

import org.gmdev.api.model.school.CourseApiRes;
import org.gmdev.api.model.school.CreateCourseApiReq;
import org.gmdev.api.model.school.UpdateCourseApiReq;
import org.gmdev.model.entity.school.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    SchoolTestHelper schoolTestHelper;

    @Autowired
    CourseService underTest;

    @AfterEach
    void cleanUp() {
        schoolTestHelper.cleanDb();
    }

    @Test
    void itShouldFindOneCourse() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Course course = new Course(
                "test-title",
                timestamp,
                timestamp
        );
        schoolTestHelper.saveCourse(course);

        // When
        CourseApiRes foundCourse = underTest.getOne(course.getId());

        // Then
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getCourseId()).isNotNull();
        assertThat(foundCourse.getTitle()).isEqualTo("test-title");
        assertThat(foundCourse.getCourseStudents()).hasSize(0);
        assertThat(foundCourse.getCreatedAt()).isEqualTo(timestamp);
        assertThat(foundCourse.getUpdatedAt()).isEqualTo(timestamp);
    }

    @Test
    void itShouldThrowIfCourseNotFound() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Course course = new Course(
                "test-title",
                timestamp,
                timestamp
        );
        schoolTestHelper.saveCourse(course);

        // When
        // Then
        assertThatThrownBy(() -> underTest.getOne(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(String.format("Course with id: %d not found", 99));
    }

    @Test
    void itShouldFindAllCourses() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Course course1 = new Course(
                "test-1",
                timestamp,
                timestamp
        );
        Course course2 = new Course(
                "test-2",
                timestamp,
                timestamp
        );
        schoolTestHelper.saveCourseList(List.of(course1, course2));

        // When
        List<CourseApiRes> allCourses = underTest.getAll();

        // Then
        assertThat(allCourses).hasSize(2);
        assertThat(allCourses.get(0).getTitle()).isEqualTo("test-1");
        assertThat(allCourses.get(1).getTitle()).isEqualTo("test-2");
    }

    @Test
    void itShouldSaveNewCourse() {
        // Given
        CreateCourseApiReq newCourse = new CreateCourseApiReq("test-title");

        // When
        CourseApiRes courseApiRes = underTest.addOne(newCourse);
        Course savedCourse = schoolTestHelper.findCourseById(courseApiRes.getCourseId()).orElseThrow();

        // Then
        assertThat(savedCourse.getTitle()).isEqualTo("test-title");
    }

    @Test
    void itShouldUpdateCourse() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Course course = new Course(
                "test-1",
                timestamp,
                timestamp
        );
        schoolTestHelper.saveCourse(course);
        Long courseId = course.getId();
        UpdateCourseApiReq updateCourseApiReq = new UpdateCourseApiReq("new-title");

        // When
        underTest.updateOne(courseId, updateCourseApiReq);
        Course updatedCourse = schoolTestHelper.findCourseById(courseId).orElseThrow();

        // Then
        assertThat(updatedCourse).isNotNull();
        assertThat(updatedCourse.getTitle()).isEqualTo("new-title");
    }

    @Test
    void itShouldDeleteCourse() {
        // Given
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
        Course course = new Course(
                "test-1",
                timestamp,
                timestamp
        );
        schoolTestHelper.saveCourse(course);
        Long courseId = course.getId();

        // When
        underTest.deleteOne(courseId);
        Optional<Course> courseMaybe = schoolTestHelper.findCourseById(courseId);
        List<Course> allCourses = schoolTestHelper.findAllCourses();

        // Then
        assertThat(courseMaybe).isEmpty();
        assertThat(allCourses).hasSize(0);
    }


}