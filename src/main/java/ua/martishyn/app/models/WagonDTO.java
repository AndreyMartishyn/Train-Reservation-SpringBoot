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
    @Min(value = 1, message = "{wagon.seats.min}")
    @Max(value = 99, message = "{wagon.seats.max}")
    private Integer numOfSeats;

    @NotNull
    @Min(value = 1, message = "{wagon.price.min}")
    @Max(value = 999, message = "{wagon.price.max}")
    private Integer basePrice;
}
