package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.entities.Route;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.TrainRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RoutePointRepository routePointRepository;
    private final TrainService trainService;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        TrainService trainService,
                        RoutePointRepository routePointRepository,
                        ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.trainService = trainService;
        this.routePointRepository = routePointRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<RouteDTO> getAllRoutesDTO() {
        return routeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search database for all routes with route-points
     *
     * @return List<Integer> of routes ids.
     */

    @Transactional(readOnly = true)
    public List<Integer> getAllNotEmptyRoutesIds() {
        return routeRepository.findAll()
                .stream().filter(route -> !route.getRoutePoints().isEmpty())
                .map(Route::getRouteId)
                .collect(Collectors.toList());
    }

    /**
     * Call sublist method of List implementation to get only certain route-points as
     * per departure and arrival station. Aggregates including intermediate stations
     * <p>
     * Example: Route consists of following Route-Points:
     * A -> B -> C -> D . Our interested route is B->D.
     * This method gives us Station B, C, D collected in List
     *
     * @param routeId          routeId which contains all route-points
     * @param routePointFromId from station id(route-point)
     * @param routePointToId   to station id (route-point)
     * @return List<RoutePointDTO> of collected route-points
     */

    @Transactional(readOnly = true)
    public List<RoutePointDTO> collectRoutePointsForView(Integer routeId,
                                                         Integer routePointFromId,
                                                         Integer routePointToId) {
        Optional<Route> selectedRoute = routeRepository.findById(routeId);
        RouteDTO routeDTO = convertToDto(selectedRoute.get());
        List<RoutePointDTO> intermediateStations = routeDTO.getIntermediateStations();
        RoutePointDTO departurePoint = intermediateStations.stream()
                .filter(routePointDTO -> Objects.equals(routePointDTO.getId(), routePointFromId))
                .findFirst().get();
        RoutePointDTO arrivalPoint = intermediateStations.stream()
                .filter(routePointDTO -> Objects.equals(routePointDTO.getId(), routePointToId))
                .findFirst().get();
        return intermediateStations.subList(intermediateStations.indexOf(departurePoint),
                intermediateStations.indexOf(arrivalPoint) + 1);
    }

    @Transactional
    public void addRouteWithoutStations(RouteDTO routeDTO) {
        Route routeToSave = convertEmptyRouteDtoToEntity(routeDTO);
        routeRepository.save(routeToSave);
    }

    @Transactional
    public void addOrUpdateRoutePointToExistingRoute(RoutePointDTO routePointDTO) {
        RoutePoint routePointToSave = convertRoutePointDtoToEntity(routePointDTO);
        routePointRepository.save(routePointToSave);
    }

    @Transactional
    public void deleteRoute(Integer routeId) {
        routeRepository.deleteById(routeId);
    }

    @Transactional(readOnly = true)
    public RoutePointDTO getRoutePointDtoById(Integer id) {
        return convertRoutePointToDTO(routePointRepository.getById(id));
    }

    @Transactional
    public void deleteRoutePoint(Integer routePointId) {
        routePointRepository.deleteById(routePointId);
    }

    public RouteDTO convertToDto(Route route) {
        RouteDTO routeDTO = modelMapper.map(route, RouteDTO.class);
        Type listType = new TypeToken<List<RoutePointDTO>>() {
        }.getType();
        List<RoutePointDTO> dtoPoints = modelMapper.map(route.getRoutePoints(), listType);
        routeDTO.setIntermediateStations(dtoPoints);
        return routeDTO;
    }

    public Route convertEmptyRouteDtoToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setRouteId(routeDTO.getId());
        route.setTrain(trainService.getTrainById(routeDTO.getTrainId()));
        return route;
    }

    private RoutePointDTO convertRoutePointToDTO(RoutePoint routePoint) {
        return modelMapper.map(routePoint, RoutePointDTO.class);
    }

    private RoutePoint convertRoutePointDtoToEntity(RoutePointDTO routePointDTO) {
        return modelMapper.map(routePointDTO, RoutePoint.class);
    }


}
