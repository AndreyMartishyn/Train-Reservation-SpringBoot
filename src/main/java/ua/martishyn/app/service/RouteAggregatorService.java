package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.models.Route;
import ua.martishyn.app.models.RoutePointDTO;
import ua.martishyn.app.models.StationDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteAggregatorService {
    private final StationService stationService;
    private final RouteService routeService;
    private final List<Route> routeList;

    @Autowired
    public RouteAggregatorService(StationService stationService, RouteService routeService) {
        this.stationService = stationService;
        this.routeService = routeService;
        routeList = new ArrayList<>();
        init();
    }

    private void init() {
        List<RoutePointDTO> allRoutePointDTO = routeService.getAllRoutePointDTO();
        makeRoutesFromRoutePoints(allRoutePointDTO);
    }

    public void makeRoutesFromRoutePoints(List<RoutePointDTO> routePointDTOList) {
        int routeId;
        Route route;
        for (RoutePointDTO routePointDTO : routePointDTOList) {
            routeId = routePointDTO.getRouteId();
            route = getRoute(routeList, routeId);
            if (route == null) {
                route = new Route();
                route.setId(routeId);
                route.setTrainId(routePointDTO.getTrainId());
                routeList.add(route);
            }
            addIntermediateStation(route, routePointDTO);
        }
    }

    private void addIntermediateStation(Route route, RoutePointDTO routePointDTO) {
        StationDTO stationDTO = stationService.getStationDtoById(routePointDTO.getStationId());
        LocalDateTime arrivalDate = routePointDTO.getArrival();
        LocalDateTime departureDate = routePointDTO.getDeparture();
        route.addIntermediateStation(stationDTO, arrivalDate, departureDate);

    }

    public List<Route> getRouteList() {
        return routeList;
    }

    private Route getRoute(List<Route> routeList, int routeId) {
        for (Route route : routeList) {
            if (route.getId() == routeId) {
                return route;
            }
        }
        return null;
    }

}
