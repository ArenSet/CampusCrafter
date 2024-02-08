package campusCrafter.service.impl;

import campusCrafter.model.Assignment;
import campusCrafter.model.dto.AssignmentRequestDto;
import campusCrafter.repository.AssignmentRepository;
import campusCrafter.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Override
    public Assignment findById(int id){
        return assignmentRepository.findById(id);
    }

    @Override
    public List<Assignment> findByCourseId(int courseId){

        List<Assignment> assignments = assignmentRepository.findByCourseId(courseId);

        return assignments;
    }

    @Override
    public void createAssignment(AssignmentRequestDto requestDto, int courseId){
        Assignment assignment = convertToAssignment(requestDto, new Assignment(), courseId);

        assignmentRepository.save(assignment);
    }

    @Override
    public void updateAssignment(Assignment assignment, String title, String content, String dueDate, Integer maxScore, String submissionFormat, int id){
        Optional<String> optionalTitle = Optional.of(Optional.ofNullable(title).orElse(assignment.getTitle()));
        Optional<String> optionalContent = Optional.of(Optional.ofNullable(content).orElse(assignment.getContent()));
        Optional<String> optionalDueDate = Optional.of(Optional.ofNullable(dueDate).orElse(assignment.getDueDate()));
        Optional<Integer> optionalMaxScore = Optional.of(Optional.ofNullable(maxScore).orElse(assignment.getMaxScore()));
        Optional<String> optionalSubmissionFormat = Optional.of(Optional.ofNullable(submissionFormat).orElse(assignment.getSubmissionFormat()));
        assignmentRepository.updateAssignment(optionalTitle,optionalContent,optionalDueDate,optionalMaxScore,optionalSubmissionFormat, id);


    }

    @Override
    public void deleteAssignment(Assignment assignment){
        assignmentRepository.delete(assignment);
    }

    private Assignment convertToAssignment(AssignmentRequestDto requestDto, Assignment assignment, int courseId){

        assignment.setTitle(requestDto.getTitle());
        assignment.setContent(requestDto.getContent());
        assignment.setDueDate(requestDto.getDueDate());
        assignment.setCourseId(courseId);
        assignment.setPostedDate(System.currentTimeMillis());
        assignment.setMaxScore(requestDto.getMaxScore());
        assignment.setSubmissionFormat(requestDto.getSubmissionFormat());

        return assignment;
    }
}
