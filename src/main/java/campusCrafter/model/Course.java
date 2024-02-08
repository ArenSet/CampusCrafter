package campusCrafter.model;


import campusCrafter.model.enums.Roles;
import campusCrafter.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String title;

    private String description;

    @JsonIgnore
    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "start_date")
    private Long startDate;

    private int credits;

    @Column(name = "enrollment_limit")
    private int enrollmentLimit;

    @JsonIgnore
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private Status status;
}
