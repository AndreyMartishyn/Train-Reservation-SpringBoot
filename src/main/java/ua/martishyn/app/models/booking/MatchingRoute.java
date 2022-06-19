package ua.martishyn.app.models.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.models.route.RouteDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingRoute {
    private RouteDTO routeDTO;
    private String roadTime;
    private MatchingRouteSeatsDetails seatsDetails;
}


