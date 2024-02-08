package campusCrafter.service.impl;

import campusCrafter.model.Assignment;
import campusCrafter.model.Grade;
import campusCrafter.model.dto.GradeRequestDto;
import campusCrafter.repository.GradeRepository;
import campusCrafter.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public void postGrade(Assignment assignment, GradeRequestDto requestDto) {
        Grade grade = convertToGrade(requestDto, new Grade(), assignment);

        gradeRepository.save(grade);
    }

    @Override
    public List<Grade> getGradesForStudent(int studentId){
        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        return grades;
    }

    @Override
    public void deleteGrade(int id){
        Grade grade = gradeRepository.getById(id);
        gradeRepository.delete(grade);
    }

    private Grade convertToGrade(GradeRequestDto requestDto, Grade grade, Assignment assignment) {
        grade.setStudentId(requestDto.getStudentId());
        grade.setAssignmentId(requestDto.getAssignmentId());
        grade.setScore(requestDto.getScore());
        grade.setFeedback(requestDto.getFeedBack());
        grade.setSubmissionDate(assignment.getPostedDate());
        return grade;
    }
}
