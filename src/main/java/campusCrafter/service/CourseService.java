package campusCrafter.service;

import campusCrafter.model.Course;
import campusCrafter.model.dto.CourseRequestDto;
import campusCrafter.util.exceptions.DuplicateException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService {
    List<Course> GetAllCourses();

    Course getSingleCourse(int id);

    List<Course> findByTeacherId(int teacherId);

    void create(CourseRequestDto requestDto);

    void updateCourse(Course course, String title, String description, Integer credits, Integer enrollmentLimit, Integer statusId);

    void deleteCourse(int id);
}
