package ua.martishyn.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.Type;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WagonDTO {

    private Integer id;

    @NotNull
    private Integer route;

    private Type type;

    @NotNull
    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 99, message = "must be equal or less than 999")
    private Integer numOfSeats;

    @NotNull
    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 999, message = "must be equal or less than 999")
    private Integer basePrice;
}
