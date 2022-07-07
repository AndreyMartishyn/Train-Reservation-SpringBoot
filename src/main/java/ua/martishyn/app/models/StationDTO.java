package ua.martishyn.app.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StationDTO {

    private Integer id;

    @NotBlank(message = "{station.name.notblank}")
    @Pattern(regexp = "^[A-Za-z\\u0400-\\u04ff\\-]{4,20}$",
            message = "{station.name.pattern}")
    private String name;

    @NotBlank(message = "{station.code.notblank}")
    @Pattern(regexp = "^[A-Z\\u0400-\\u04ff]{1,3}$",
            message = "{station.code.pattern}")
    private String code;

}
