package campusCrafter.controller;

import campusCrafter.model.Assignment;
import campusCrafter.model.Course;
import campusCrafter.model.User;
import campusCrafter.model.dto.AssignmentRequestDto;
import campusCrafter.model.enums.Roles;
import campusCrafter.service.AssignmentService;
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
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @PutMapping("courses/{courseId}/assignments")
    public ResponseEntity<?> createAssignments(@PathVariable int courseId,
                                               @RequestBody AssignmentRequestDto requestDto,
                                               Authentication auth){

        User user = userService.getByUsername(auth.getName());
        Course course = courseService.getSingleCourse(courseId);
        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER)) || user.getRole().equals(Roles.ADMIN)){
            assignmentService.createAssignment(requestDto, courseId);
            return ResponseEntity.ok().build();
        }

        else return new ResponseEntity<>("You can't create an assignment", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{courseId}/assignments")
    public ResponseEntity<?> getAssignmentsForCourse(@PathVariable int courseId){
        List<Assignment> assignments = assignmentService.findByCourseId(courseId);

        return ResponseEntity.ok(assignments);
    }

    @PutMapping("assignments/{id}")
    public ResponseEntity<?> updateAssignment(Authentication auth,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String content,
                                              @RequestParam(required = false) String dueDate,
                                              @RequestParam(required = false) Integer maxScore,
                                              @RequestParam(required = false) String submissionFormat,
                                              @PathVariable int id
                                              ){
        User user = userService.getByUsername(auth.getName());
        Assignment assignment = assignmentService.findById(id);
        Course course = courseService.getSingleCourse(assignment.getCourseId());

        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER)) || user.getRole().equals(Roles.ADMIN)){
            assignmentService.updateAssignment(assignment, title, content, dueDate, maxScore, submissionFormat, id);
            return ResponseEntity.ok().build();
        }

        else return new ResponseEntity<>("You can't update this assignment", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("assignments/{id}")
    public ResponseEntity<?> deleteAssignment(Authentication auth,
                                              @PathVariable int id){
        User user = userService.getByUsername(auth.getName());
        Assignment assignment = assignmentService.findById(id);
        Course course = courseService.getSingleCourse(assignment.getCourseId());

        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER)) || user.getRole().equals(Roles.ADMIN)){
            assignmentService.deleteAssignment(assignment);
            return ResponseEntity.ok().build();
        }
        else return new ResponseEntity<>("You can't delete this assignment", HttpStatus.BAD_REQUEST);
    }

}
