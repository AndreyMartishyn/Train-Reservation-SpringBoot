package ua.martishyn.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.passwords_match_validator.PasswordFieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordFieldMatch(field = "password",
        fieldMatch = "verifyPassword")
@Builder
public class UserRegisterDTO {

    @NotEmpty(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "First name should be from 1 to 16 letters cyrillic/latin")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "Last name should be from 1 to 16 letters cyrillic/latin")
    private String lastName;

    @NotEmpty(message = "Password field cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])[A-Za-z\\d]{8,}$",
            message = "Password should be 8 symbols, at least 1 capital and 1 digit")
    private String password;

    @NotEmpty(message = "Password field cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])[A-Za-z\\d]{8,}$",
            message = "Password should be 8 symbols, at least 1 capital and 1 digit")
    private String verifyPassword;

    @NotEmpty(message = "Email field cannot be empty")
    @Email(message = "Please enter valid email")
    private String email;

}
