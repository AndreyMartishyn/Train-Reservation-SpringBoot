package ua.martishyn.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Integer id;

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "{user.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "{user.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.notcorrect}")
    private String email;

    @NotNull
    private Role role;
}
