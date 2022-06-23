package ua.martishyn.app.models.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteDTO {

    @NotNull(message = "{route.id.notnull}")
    @Min(value = 1, message = "{route.id.min}")
    @Max(value = 999, message = "{route.id.max}")
    private Integer id;

    @NotNull()
    private Integer trainId;

    @Builder.Default
    private List<RoutePointDTO> intermediateStations = new ArrayList<>();

    public void addRoutePointToRoute(RoutePointDTO routePointDTO) {
        intermediateStations.add(routePointDTO);
    }

    public List<RoutePointDTO> getIntermediateStations() {
        return intermediateStations;
    }

    public RoutePointDTO getFirstStation() {
        return intermediateStations.get(0);
    }

    public RoutePointDTO getLastStation() {
        return intermediateStations.get(intermediateStations.size() - 1);
    }

}

