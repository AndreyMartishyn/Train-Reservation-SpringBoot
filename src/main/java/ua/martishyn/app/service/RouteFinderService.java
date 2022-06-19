package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.models.*;
import ua.martishyn.app.models.booking.MatchingRoute;
import ua.martishyn.app.models.booking.MatchingRouteSeatsDetails;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.utils.enums.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


@Service
public class RouteFinderService {

    private final RouteService routeService;
    private final StationService stationService;
    private final WagonService wagonService;

    @Autowired
    public RouteFinderService(RouteService routeService,
                              StationService stationService,
                              WagonService wagonService) {
        this.routeService = routeService;
        this.stationService = stationService;
        this.wagonService = wagonService;
    }

    public List<MatchingRoute> makeBooking(int from, int to) {
        List<MatchingRoute> matchingRouteList = new ArrayList<>();
        List<RouteDTO> routeDTOS = routeService.getAllRoutesDTO();
        StationDTO fromStation = stationService.getStationDtoById(from);
        StationDTO toStation = stationService.getStationDtoById(to);

        for (RouteDTO routeDTO : routeDTOS) {
            final List<RoutePointDTO> routePointsList = routeDTO.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(routePointsList, fromStation, toStation)) {
                MatchingRoute personalRoute = new MatchingRoute();
                RouteDTO userRoute = new RouteDTO();
                userRoute.setId(routeDTO.getId());
                userRoute.setTrainId(routeDTO.getTrainId());
                int overallStationCounter = 0;
                int fromId = 0;
                int toId = 0;

                for (RoutePointDTO routePointDTO : routePointsList) {
                    int stationId = routePointDTO.getStation().getId();
                    if (stationId == fromStation.getId()) {
                        userRoute.addRoutePointToRoute(routePointDTO);
                        fromId = overallStationCounter;
                    }
                    overallStationCounter++;
                    if (stationId == toStation.getId()) {
                        toId = overallStationCounter;
                        userRoute.addRoutePointToRoute(routePointDTO);
                    }
                }
                if (fromId < toId) {
                    personalRoute.setRouteDTO(userRoute);
                    int totalStationInRoute = toId - fromId;
                    DateFormat routePattern = new SimpleDateFormat("HH:mm");
                    long duration = Duration.between(
                            (personalRoute.getRouteDTO().getFirstStation().getDeparture()),
                            personalRoute.getRouteDTO().getLastStation().getArrival()).toMillis();
                    String durationFormatted = routePattern.format(new Date(duration));
                    personalRoute.setRoadTime(durationFormatted);

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
     * @param stations    - list of collected stations from route
     * @param fromStation - departure station selected by user
     * @param toStation   - arrival station selected by user
     * @return true, if stations both stations exist in list. Otherwise false;
     */
    private boolean stationsExistInRoute(List<RoutePointDTO> stations,
                                         StationDTO fromStation,
                                         StationDTO toStation) {
        return (stations.stream().anyMatch(routePoint -> Objects.equals(routePoint.getStation().getId(), fromStation.getId()))
                &&
                stations.stream().anyMatch(routePoint -> Objects.equals(routePoint.getStation().getId(), toStation.getId())));
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
