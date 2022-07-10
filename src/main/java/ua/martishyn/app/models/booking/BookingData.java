package ua.martishyn.app.models.booking;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingData {
    private Integer route;

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{2,16}$",
            message = "{user.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{user.lastname.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{2,16}$",
            message = "{user.lastname.pattern}")
    private String lastName;

    @NotNull
    private Integer wagonNum;
    private Integer from;
    private Integer to;
    private String duration;
    private String type;
    private Integer price;
}
