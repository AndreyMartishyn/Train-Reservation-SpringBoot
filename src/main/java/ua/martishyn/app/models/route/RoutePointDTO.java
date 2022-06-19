package ua.martishyn.app.models.route;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.martishyn.app.models.StationDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoutePointDTO {

    private Integer id;

    private Integer routeId;

    @NotNull
    private StationDTO station;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrival;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departure;

}
