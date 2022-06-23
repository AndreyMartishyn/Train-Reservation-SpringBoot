package ua.martishyn.app.models.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.utils.validation.date_range.DateRange;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@DateRange(message = "{route.point.dates}")
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
