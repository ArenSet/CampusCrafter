package campusCrafter.service;

import campusCrafter.model.Assignment;
import campusCrafter.model.dto.AssignmentRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AssignmentService {

    Assignment findById(int id);

    List<Assignment> findByCourseId(int courseId);

    void createAssignment(AssignmentRequestDto requestDto, int courseId);

    void updateAssignment(Assignment assignment, String title, String content, String dueDate, Integer maxScore, String submissionFormat, int id);

    void deleteAssignment(Assignment assignment);
}
