package ua.martishyn.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.validation.passwords_match.PasswordFieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordFieldMatch(field = "password",
        fieldMatch = "verifyPassword",
message = "{user.password.mismatch}")
@Builder
public class UserRegisterDTO {

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "{user.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "{user.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{user.password.notblank}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])[A-Za-z\\d]{8,16}$",
            message = "{user.password.pattern}")
    private String password;

    @NotBlank(message = "{user.password.notblank}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])[A-Za-z\\d]{8,16}$",
            message = "{user.password.pattern}")
    private String verifyPassword;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.notcorrect}")
    private String email;

}
