package campusCrafter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @JsonIgnore
    @Column(name = "student_id")
    private int studentId;

    @JsonIgnore
    @Column(name = "assignment_id")
    private int assignmentId;

    private int score;

    private String feedback;

    @Column(name = "submission_date")
    private Long submissionDate;
}
