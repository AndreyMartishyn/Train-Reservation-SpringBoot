package ua.martishyn.app.unit_testing;

import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;

import java.time.LocalDateTime;

public class RouteFinderTestHelper {

    protected RouteDTO createRoute() {
        RouteDTO newRoute = RouteDTO.builder()
                .id(111)
                .trainId(125)
                .build();
        RoutePointDTO routePointA = RoutePointDTO.builder()
                .routeId(111)
                .id(1)
                .station(StationDTO.builder()
                        .id(1)
                        .build())
                .departure(LocalDateTime.parse("2022-12-01T10:15:30"))
                .build();
        RoutePointDTO routePointB = RoutePointDTO.builder()
                .routeId(111)
                .id(2)
                .station(StationDTO.builder()
                        .id(4)
                        .build())
                .departure(LocalDateTime.parse("2022-12-01T10:25:30"))
                .arrival(LocalDateTime.parse("2022-12-01T10:20:30"))
                .build();
        RoutePointDTO routePointC = RoutePointDTO.builder()
                .routeId(111)
                .id(3)
                .station(StationDTO.builder()
                        .id(5)
                        .build())
                .departure(LocalDateTime.parse("2022-12-01T10:35:30"))
                .arrival(LocalDateTime.parse("2022-12-01T10:30:30"))
                .build();
        RoutePointDTO routePointD = RoutePointDTO.builder()
                .routeId(111)
                .id(4)
                .station(StationDTO.builder()
                        .id(2)
                        .build())
                .arrival(LocalDateTime.parse("2022-12-01T10:40:30"))
                .build();
        newRoute.addRoutePointToRoute(routePointA);
        newRoute.addRoutePointToRoute(routePointB);
        newRoute.addRoutePointToRoute(routePointC);
        newRoute.addRoutePointToRoute(routePointD);
        return newRoute;
    }
}
