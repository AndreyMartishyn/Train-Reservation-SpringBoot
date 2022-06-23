package ua.martishyn.app.models.booking;

import lombok.Data;
import ua.martishyn.app.models.route.RouteDTO;

@Data
public class MatchingRoute {
    private RouteDTO routeDTO;
    private String roadTime;
    private MatchingRouteSeatsDetails seatsDetails;
}


