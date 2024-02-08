package campusCrafter.service.impl;

import campusCrafter.model.Course;
import campusCrafter.model.dto.CourseRequestDto;
import campusCrafter.repository.CourseRepository;
import campusCrafter.service.CourseService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> GetAllCourses(){
        List<Course> courses = courseRepository.findAll();

        return courses;
    }

    @Override
    public Course getSingleCourse(int id){
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findByTeacherId(int teacherId){

        List<Course> courses = courseRepository.findByTeacherId(teacherId);
        return courses;
    }

    @Override
    public void create(CourseRequestDto requestDto) {

        Course course = convertToCourse(requestDto, new Course());
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(Course course, String title, String description, Integer credits, Integer enrollmentLimit, Integer statusId){

        Optional<String> optionalTitle = Optional.of(Optional.ofNullable(title).orElse(course.getTitle()));
        Optional<String> optionalDesc = Optional.of(Optional.ofNullable(description).orElse(course.getDescription()));
        Optional<Integer> optionalCredits = Optional.of(Optional.ofNullable(credits).orElse(course.getCredits()));
        Optional<Integer> optionalLimit = Optional.of(Optional.ofNullable(enrollmentLimit).orElse(course.getEnrollmentLimit()));
        Optional<Integer> optionalStatus = Optional.of(Optional.ofNullable(statusId).orElse(course.getStatus().ordinal()));
        courseRepository.updateCourse(optionalTitle,optionalDesc,optionalCredits,optionalLimit,optionalStatus);
    }

    @Override
    public void deleteCourse(int id){
        Course course = courseRepository.findById(id);
        courseRepository.delete(course);
    }


    private Course convertToCourse(CourseRequestDto requestDto, Course course){

        course.setTitle(requestDto.getTitle());
        course.setDescription(requestDto.getDescription());
        course.setTeacherId(requestDto.getTeacherId());
        course.setStartDate(System.currentTimeMillis());
        course.setCredits(requestDto.getCredits());
        course.setEnrollmentLimit(requestDto.getEnrollmentLimit());
        course.setStatus(requestDto.getStatus());

        return course;
    }
}
