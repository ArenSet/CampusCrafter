package campusCrafter.model.dto;

import lombok.Data;

@Data
public class AssignmentRequestDto {

    private String title;

    private String content;

    private String dueDate;

    private int courseId;

    private Long postedDate;

    private int maxScore;

    private String submissionFormat;

}
