package campusCrafter.controller;

import campusCrafter.model.Assignment;
import campusCrafter.model.Course;
import campusCrafter.model.Grade;
import campusCrafter.model.User;
import campusCrafter.model.dto.UserRequestDto;
import campusCrafter.model.enums.Roles;
import campusCrafter.service.AssignmentService;
import campusCrafter.service.CourseService;
import campusCrafter.service.GradeService;
import campusCrafter.service.UserService;
import campusCrafter.util.exceptions.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users")
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDto requestDto) throws DuplicateException {
        userService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserProfile(Authentication auth,
                                            @PathVariable int userId){

        User user = userService.getByUsername(auth.getName());

        if (user.getId() == userId || user.getRole().equals(Roles.ADMIN)){
            User user1 = userService.getUserProfile(userId);
            return ResponseEntity.ok(user1);
        }
        else return new ResponseEntity<>("You can't view not your profile", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUserProfile(Authentication auth,
                                               @PathVariable int userId,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String password,
                                               @RequestParam(required = false) String profilePicture,
                                               @RequestParam(required = false) String bio){

        User user = userService.getByUsername(auth.getName());

        if (user.getId() == userId || user.getRole().equals(Roles.ADMIN)){
            userService.updateUser(user,name,password,profilePicture,bio, userId);
            return ResponseEntity.ok().build();
        }
        else return new ResponseEntity<>("You can't update this profile", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        User user = userService.getUserProfile(userId);

        List<Course> courses = courseService.findByTeacherId(userId);
        List<Grade> grades = gradeService.getGradesForStudent(userId);
        for (int i = 0; i < grades.size(); i++){
            gradeService.deleteGrade(grades.get(i).getId());
        }

        for (int i = 0; i < courses.size(); i++){
            List<Assignment> assignments = assignmentService.findByCourseId(courses.get(i).getId());
            for (int j = 0; j < assignments.size(); j++){
                assignmentService.deleteAssignment(assignments.get(i));
            }
            courseService.deleteCourse(courses.get(i).getId());
        }
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
