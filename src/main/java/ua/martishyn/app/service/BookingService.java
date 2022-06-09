package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.models.PersonalRoute;
import ua.martishyn.app.models.Route;
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
public class BookingService {
    private final RouteAggregatorService routeAggregator;
    private final StationService stationService;
    private final List<PersonalRoute> suitableRoutes;

    @Autowired
    public BookingService(RouteAggregatorService routeAggregator,
                          StationService stationService) {
        this.routeAggregator = routeAggregator;
        this.stationService = stationService;
        suitableRoutes = new ArrayList<>();
    }


    public void makeBooking(int from, int to) {
        List<Route> routes = routeAggregator.getRouteList();
        StationDTO fromStation = stationService.getStationDtoById(from);
        StationDTO toStation = stationService.getStationDtoById(to);

        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateFormat routePattern = new SimpleDateFormat("HH:mm");

        for (Route route : routes) {
            List<Route.IntermediateStation> stations = route.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(stations, fromStation ,toStation)) {
                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(route.getId());
                personalRoute.setTrainId(route.getTrainId());
                StringBuilder redirectLink = new StringBuilder();
                redirectLink.append("?train=")
                        .append(personalRoute.getTrainId());
                int overallStationCounter = 0;
                int fromId = 0;
                int toId = 0;
                LocalDateTime depDate = null;
                LocalDateTime arrDate = null;

                for (Route.IntermediateStation stationObject : stations) {
                    StationDTO station = stationObject.getStation();
                    if (station.getId() == fromStation.getId()) {
                        depDate = stationObject.getDepartureDate();
                        fromId = overallStationCounter;
                        personalRoute.setDeparture(formatPattern.format(depDate));
                        personalRoute.setDepartureStation(fromStation.getName());
                        redirectLink.append("&fromStation=")
                                .append(station.getName())
                                .append("&departure=")
                                .append(formatPattern.format(depDate));
                    }
                    overallStationCounter++;
                    if (station.getId() == toStation.getId()) {
                        arrDate = stationObject.getArrivalDate();
                        toId = overallStationCounter;
                        personalRoute.setArrival(formatPattern.format(arrDate));
                        personalRoute.setArrivalStation(toStation.getName());
                        redirectLink.append("&toStation=")
                                .append(station.getName())
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
                    addRoute(personalRoute);
                }
            }
        }
    }


    public void addRoute(PersonalRoute personalRoute) {
        suitableRoutes.add(personalRoute);
    }

    public List<PersonalRoute> getSuitableRoutes() {
        return suitableRoutes;
    }

    private boolean stationsExistInRoute(List<Route.IntermediateStation> stations,
                                         StationDTO fromStation,
                                         StationDTO toStation) {
        return (stations.stream().anyMatch(st -> st.getStation().equals(fromStation))
                &&
                stations.stream().anyMatch(st1 -> st1.getStation().equals(toStation)));
    }

    public boolean areStationsSame(int fromId,
                                   int toId) {
        return fromId == toId;
    }

}
