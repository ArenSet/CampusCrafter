package campusCrafter.model.dto;

import campusCrafter.model.enums.Roles;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestDto {

    private int id;

    @NotBlank(message = "The name can't be null")
    private String name;

    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String email;

    @NotNull
    private Roles roles;

    @Size(min = 3, max = 15, message = "password must be between 3 to 15")
    private String password;

    private String profilePicture;

    private String bio;
}
