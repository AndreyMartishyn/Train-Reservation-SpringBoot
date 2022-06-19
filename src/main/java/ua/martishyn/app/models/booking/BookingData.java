package ua.martishyn.app.models.booking;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingData {
    private Integer route;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "First name should be from 1 to 16 letters cyrillic/latin")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff]{1,16}$",
            message = "First name should be from 1 to 16 letters cyrillic/latin")
    private String lastName;

    @NotNull
    private Integer wagonNum;
    private Integer from;
    private Integer to;
    private String duration;
    private String type;
    private Integer price;
}
