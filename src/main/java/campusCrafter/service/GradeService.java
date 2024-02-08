package campusCrafter.service;

import campusCrafter.model.Assignment;
import campusCrafter.model.Grade;
import campusCrafter.model.dto.GradeRequestDto;

import java.util.List;

public interface GradeService {
    void postGrade(Assignment assignment, GradeRequestDto requestDto);

    List<Grade> getGradesForStudent(int studentId);

    void deleteGrade(int id);
}
