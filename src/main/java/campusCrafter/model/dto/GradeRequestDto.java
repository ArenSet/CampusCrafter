package campusCrafter.model.dto;

import lombok.Data;

@Data
public class GradeRequestDto {

    private int id;

    private int studentId;

    private int assignmentId;

    private int score;

    private String feedBack;
}
