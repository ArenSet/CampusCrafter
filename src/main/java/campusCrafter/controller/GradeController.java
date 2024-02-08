package campusCrafter.controller;


import campusCrafter.model.Assignment;
import campusCrafter.model.Course;
import campusCrafter.model.Grade;
import campusCrafter.model.User;
import campusCrafter.model.dto.GradeRequestDto;
import campusCrafter.model.enums.Roles;
import campusCrafter.service.AssignmentService;
import campusCrafter.service.CourseService;
import campusCrafter.service.GradeService;
import campusCrafter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;


    @PostMapping("/assignments/{assignmentId}/grades")
    public ResponseEntity<?> postGrades(Authentication auth,
                                        @PathVariable int assignmentId,
                                        @RequestBody GradeRequestDto requestDto){

        User user = userService.getByUsername(auth.getName());
        Assignment assignment = assignmentService.findById(assignmentId);
        Course course = courseService.getSingleCourse(assignment.getCourseId());

        if ((user.getId() == course.getTeacherId() && user.getRole().equals(Roles.TEACHER)) || user.getRole().equals(Roles.ADMIN)){
            gradeService.postGrade(assignment, requestDto );
            return ResponseEntity.ok().build();
        }

        else return new ResponseEntity<>("You can't post the grade for this assignment", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/students/{studentId}/grades")
    public ResponseEntity<?> getGradesForStudent(Authentication auth,
                                                           @PathVariable int studentId){
        User user = userService.getByUsername(auth.getName());

        if (user.getId() == studentId || user.getRole().equals(Roles.ADMIN)){
            List<Grade> grades = gradeService.getGradesForStudent(studentId);
            return ResponseEntity.ok(grades);
        }
        else return new ResponseEntity<>("You can't view this grades", HttpStatus.BAD_REQUEST);
    }
}
