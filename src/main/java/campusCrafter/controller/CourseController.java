package campusCrafter.controller;

import campusCrafter.model.Course;
import campusCrafter.model.User;
import campusCrafter.model.dto.CourseRequestDto;
import campusCrafter.model.enums.Roles;
import campusCrafter.service.CourseService;
import campusCrafter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = courseService.GetAllCourses();

        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping ("/courses")
    public ResponseEntity<Void> createCourse(@RequestBody CourseRequestDto requestDto){
        courseService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("courses/{id}")
    public ResponseEntity<Course> getSingleCourse(@PathVariable int id){
        Course course = courseService.getSingleCourse(id);

        return ResponseEntity.ok(course);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(Authentication auth,
                                          @RequestParam(required = false) String title,
                                          @RequestParam(required = false) String description,
                                          @RequestParam(required = false) Integer credits,
                                          @RequestParam(required = false) Integer limit,
                                          @RequestParam(required = false) Integer status,
                                             @PathVariable int id){
        User user = userService.getByUsername(auth.getName());

        Course course = courseService.getSingleCourse(id);

        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER)) || user.getRole().equals(Roles.ADMIN)) {
            courseService.updateCourse(course, title, description, credits, limit, status);
            return ResponseEntity.ok().build();
        }
        else return new ResponseEntity<>("You are not the right teacher", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(Authentication auth, @PathVariable int id){
        User user = userService.getByUsername(auth.getName());

        Course course = courseService.getSingleCourse(id);

        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER))|| user.getRole().equals(Roles.ADMIN)) {
            courseService.deleteCourse(id);
            return ResponseEntity.ok().build();
        }
        else return new ResponseEntity<>("You can't delete this course", HttpStatus.BAD_REQUEST);
    }


}
