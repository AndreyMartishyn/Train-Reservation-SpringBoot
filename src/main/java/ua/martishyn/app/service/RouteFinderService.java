package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.models.WagonDTO;
import ua.martishyn.app.models.booking.MatchingRoute;
import ua.martishyn.app.models.booking.MatchingRouteSeatsDetails;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.utils.enums.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RouteFinderService {

    private final RouteService routeService;
    private final WagonService wagonService;

    @Autowired
    public RouteFinderService(RouteService routeService,
                              WagonService wagonService) {
        this.routeService = routeService;
        this.wagonService = wagonService;
    }

    public List<MatchingRoute> makeBooking(int from, int to) {
        List<MatchingRoute> matchingRouteList = new ArrayList<>();
        List<RouteDTO> routeDTOS = routeService.getAllRoutesDTO();

        for (RouteDTO routeDTO : routeDTOS) {
            final List<RoutePointDTO> routePointsList = routeDTO.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(routePointsList, from, to)) {
                MatchingRoute personalRoute = new MatchingRoute();
                RouteDTO userRoute = new RouteDTO();
                userRoute.setId(routeDTO.getId());
                userRoute.setTrainId(routeDTO.getTrainId());
                int overallStationCounter = 0;
                int fromId = 0;
                int toId = 0;

                for (RoutePointDTO routePointDTO : routePointsList) {
                    int stationId = routePointDTO.getStation().getId();
                    if (stationId == from) {
                        userRoute.addRoutePointToRoute(routePointDTO);
                        fromId = overallStationCounter;
                    }
                    overallStationCounter++;
                    if (stationId == to) {
                        toId = overallStationCounter;
                        userRoute.addRoutePointToRoute(routePointDTO);
                    }
                }
                if (fromId < toId) {
                    personalRoute.setRouteDTO(userRoute);
                    int totalStationInRoute = toId - fromId;
                    Duration duration = Duration.between(
                            (personalRoute.getRouteDTO().getFirstStation().getDeparture()),
                            personalRoute.getRouteDTO().getLastStation().getArrival());
                    String formattedDuration = String.format("%d:%02d", duration.toHours(), duration.toMinutes() % 60);
                    personalRoute.setRoadTime(formattedDuration);

                    final List<WagonDTO> allWagonsByRouteId = wagonService.getAllWagonsByRouteId(personalRoute.getRouteDTO().getId());
                    personalRoute.setSeatsDetails(collectSeatsDetails(totalStationInRoute, allWagonsByRouteId));
                    matchingRouteList.add(personalRoute);
                }
            }
        }
        return matchingRouteList;
    }

    /**
     * Collects data for SeatsDetails class
     *
     * @param totalStationInRoute - total stations passed in route
     * @param allWagonsByRouteId  - collected wagons for certain route_id
     * @return new class with appropriate data
     */
    private MatchingRouteSeatsDetails collectSeatsDetails(int totalStationInRoute,
                                                          List<WagonDTO> allWagonsByRouteId) {
        return MatchingRouteSeatsDetails.builder()
                .firstClassSeats(getCertainClassTotalPlaces(allWagonsByRouteId, Type.FIRST))
                .secondClassSeats(getCertainClassTotalPlaces(allWagonsByRouteId, Type.SECOND))
                .firstClassTotalPrice(getCertainClassTotalPrice(Type.FIRST, totalStationInRoute, allWagonsByRouteId))
                .secondClassTotalPrice(getCertainClassTotalPrice(Type.SECOND, totalStationInRoute, allWagonsByRouteId))
                .build();
    }

    /**
     * Check if stations exists in list of RoutePoints. Compares input
     * with list by ids
     *
     * @param stations - list of collected stations from route
     * @param from     - departure station(id)
     * @param to       - arrival station(id)
     * @return true, if stations both stations exist in list. Otherwise false;
     */
    private boolean stationsExistInRoute(List<RoutePointDTO> stations,
                                         int from, int to) {
        return (stations.stream().anyMatch(routePoint -> routePoint.getStation().getId() == from)
                &&
                stations.stream().anyMatch(routePoint -> routePoint.getStation().getId() == to));
    }

    private int getCertainClassTotalPrice(Type type,
                                          int totalStationInRoute,
                                          List<WagonDTO> allWagonsByRouteId) {
        return wagonService.getPriceForClassSeat(allWagonsByRouteId, type, totalStationInRoute);
    }

    private int getCertainClassTotalPlaces(List<WagonDTO> allWagonsByRouteId,
                                           Type type) {
        return wagonService.getClassPlaces(allWagonsByRouteId, type);
    }

    public boolean areStationsSame(int fromId,
                                   int toId) {
        return fromId == toId;
    }
}
