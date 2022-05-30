package ua.martishyn.app.models;

import lombok.*;
import ua.martishyn.app.utils.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {

    private Integer id;
    @NotEmpty(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "First name should be from 1 to 16 letters cyrillic/latin")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "Last name should be from 1 to 16 letters cyrillic/latin")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private Role role;
}
