package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.models.PersonalRoute;
import ua.martishyn.app.models.RouteDTO;
import ua.martishyn.app.models.RoutePointDTO;
import ua.martishyn.app.models.StationDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RouteFindHelper {
    private final RouteService routeService;
    private final StationService stationService;
    private final List<PersonalRoute> suitableRoutes;

    @Autowired
    public RouteFindHelper(RouteService routeService,
                           StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
        suitableRoutes = new ArrayList<>();
    }


    public void makeBooking(int from, int to) {
        List<RouteDTO> routeDTOS = routeService.getAllRoutesDTO();
        StationDTO fromStation = stationService.getStationDtoById(from);
        StationDTO toStation = stationService.getStationDtoById(to);

        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateFormat routePattern = new SimpleDateFormat("HH:mm");

        for (RouteDTO routeDTO : routeDTOS) {
            final List<RoutePointDTO> routePointsList = routeDTO.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(routePointsList, fromStation, toStation)) {
                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(routeDTO.getId());
                personalRoute.setTrainId(routeDTO.getTrainId());
                StringBuilder redirectLink = new StringBuilder();
                redirectLink.append("?train=")
                        .append(personalRoute.getTrainId());
                int overallStationCounter = 0;
                int fromId = 0;
                int toId = 0;
                LocalDateTime depDate = null;
                LocalDateTime arrDate = null;

                for (RoutePointDTO routePointDTO : routePointsList) {
                    int stationId = routePointDTO.getId();
                    StationDTO currentStationFromList = stationService.getStationDtoById(stationId);
                    if (stationId == fromStation.getId()) {
                        depDate = routePointDTO.getDeparture();
                        fromId = overallStationCounter;
                        personalRoute.setDeparture(formatPattern.format(depDate));
                        personalRoute.setDepartureStation(fromStation.getName());
                        redirectLink.append("&fromStation=")
                                .append(currentStationFromList.getName())
                                .append("&departure=")
                                .append(formatPattern.format(depDate));
                    }
                    overallStationCounter++;
                    if (stationId == toStation.getId()) {
                        arrDate = routePointDTO.getArrival();
                        toId = overallStationCounter;
                        personalRoute.setArrival(formatPattern.format(arrDate));
                        personalRoute.setArrivalStation(toStation.getName());
                        redirectLink.append("&toStation=")
                                .append(currentStationFromList.getName())
                                .append("&arrival=")
                                .append(formatPattern.format(arrDate));
                    }
                }
                if (fromId < toId) {
                    int totalStationInRoute = toId - fromId;
                    long duration = Duration.between(Objects.requireNonNull(depDate), arrDate).toMillis();
                    String durationFormatted = routePattern.format(new Date(duration));
                    redirectLink.append("&duration=")
                            .append(durationFormatted);
                    personalRoute.setRoadTime(durationFormatted);
                    personalRoute.setRedirectLink(redirectLink);
                    suitableRoutes.add(personalRoute);
                }
            }
        }
    }

    public List<PersonalRoute> getSuitableRoutes() {
        return suitableRoutes;
    }

    private boolean stationsExistInRoute(List<RoutePointDTO> stations,
                                         StationDTO fromStation,
                                         StationDTO toStation) {
        return (stations.stream().anyMatch(st -> Objects.equals(st.getId(), fromStation.getId()))
                &&
                stations.stream().anyMatch(st1 -> Objects.equals(st1.getId(), toStation.getId())));
    }

    public boolean areStationsSame(int fromId,
                                   int toId) {
        return fromId == toId;
    }

}
