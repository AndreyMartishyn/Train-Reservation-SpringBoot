package ua.martishyn.app.models;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoutePointDTO {

    @NotEmpty
    private Integer routeId;

    @NotEmpty
    private Integer trainId;

    @NotEmpty
    private Integer stationId;

    @NotEmpty
    private LocalDateTime arrival;

    @NotEmpty
    private LocalDateTime departure;
}
