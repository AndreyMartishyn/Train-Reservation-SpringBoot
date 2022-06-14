package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.Route;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.models.RouteDTO;
import ua.martishyn.app.models.RoutePointDTO;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.TrainRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final RoutePointRepository routePointRepository;
    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        TrainRepository trainRepository,
                        RoutePointRepository routePointRepository,
                        ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.trainRepository = trainRepository;
        this.routePointRepository = routePointRepository;
        this.modelMapper = modelMapper;
    }

    public List<RouteDTO> getAllRoutesDTO() {
        return routeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search database for all routes with route-points
     * <p>
     * Return: List<Integer> of routes ids.
     */
    public List<Integer> getAllNotEmptyRoutesIds() {
        return routeRepository.findAll()
                .stream().filter(route -> !route.getRoutePoints().isEmpty())
                .map(Route::getRouteId)
                .collect(Collectors.toList());
    }

    public void addRouteWithoutStations(RouteDTO routeDTO) {
        Route routeToSave = convertEmptyRouteDtoToEntity(routeDTO);
        routeRepository.save(routeToSave);
    }

    public void addRoutePointToExistingRoute(RoutePointDTO routePointDTO) {
        RoutePoint routePointToSave = convertRoutePointDtoToEntity(routePointDTO);
        routePointRepository.save(routePointToSave);
    }

    public void updatePointIntExistingRoute(RoutePointDTO routePointDTO) {
        RoutePoint routePointUpdated = convertRoutePointDtoToEntity(routePointDTO);
        routePointRepository.save(routePointUpdated);
    }

    public void deleteRoute(Integer routeId) {
        routeRepository.deleteById(routeId);
    }

    public RoutePointDTO getRoutePointDtoById(Integer id) {
        return convertRoutePointToDTO(routePointRepository.getById(id));
    }

    public void deleteRoutePoint(Integer routePointId) {
        routePointRepository.deleteById(routePointId);
    }

    private RouteDTO convertToDto(Route route) {
        RouteDTO routeDTO = modelMapper.map(route, RouteDTO.class);
        Type listType = new TypeToken<List<RoutePointDTO>>() {
        }.getType();
        List<RoutePointDTO> dtoPoints = modelMapper.map(route.getRoutePoints(), listType);
        routeDTO.setIntermediateStations(dtoPoints);
        return routeDTO;
    }

    private Route convertEmptyRouteDtoToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setRouteId(routeDTO.getId());
        route.setTrain(trainRepository.getById(routeDTO.getTrainId()));
        return route;
    }

    private RoutePointDTO convertRoutePointToDTO(RoutePoint routePoint) {
        return modelMapper.map(routePoint, RoutePointDTO.class);
    }

    private RoutePoint convertRoutePointDtoToEntity(RoutePointDTO routePointDTO) {
        return modelMapper.map(routePointDTO, RoutePoint.class);
    }
}
