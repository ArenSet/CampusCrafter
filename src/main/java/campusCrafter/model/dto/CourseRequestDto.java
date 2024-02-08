package campusCrafter.model.dto;

import campusCrafter.model.enums.Status;
import lombok.Data;

@Data
public class CourseRequestDto {

    private int id;

    private String title;

    private String description;

    private int teacherId;

    private int credits;

    private int enrollmentLimit;

    private Status status;


}
