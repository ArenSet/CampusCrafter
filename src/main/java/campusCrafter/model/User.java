package campusCrafter.model;

import campusCrafter.model.enums.Roles;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Column(name = "data_joined")
    private String dataJoined;

    @JsonIgnore
    @Column(name = "last_login")
    private String lastLogin;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String bio;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_id")
    private Roles role;
}
