package ua.martishyn.app.models;

import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteDTO {

    @NotNull
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=999, message="must be equal or less than 999")
    private Integer id;

    @NotNull
    @Min(value=1, message="must be equal or greater than 1")
    @Max(value=99, message="must be equal or less than 999")
    private Integer trainId;

    private List<RoutePointDTO> intermediateStations = new ArrayList<>();

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

