package ua.martishyn.app.models;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StationDTO {

    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff\\-]{1,16}$   ",
            message = "Station name should be from 1 to 16 letters cyrillic/latin")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Z\\u0400-\\u04ff]{1,3}$",
            message = "Code should be only up to 3 capitals cyrillic/latin")
    private String code;
}
