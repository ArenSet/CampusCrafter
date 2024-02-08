package campusCrafter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String title;

    private String content;

    @Column(name = "due_date")
    private String dueDate;

    @JsonIgnore
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "posted_date")
    private Long postedDate;

    @Column(name = "max_score")
    private int maxScore;

    @Column(name = "submission_format")
    private String submissionFormat;

}
