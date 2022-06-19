package ua.martishyn.app.models.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteDTO {

    @NotNull
    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 999, message = "must be equal or less than 999")
    private Integer id;

    @NotNull
    @Min(value = 1, message = "must be equal or greater than 1")
    @Max(value = 99, message = "must be equal or less than 999")
    private Integer trainId;

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

