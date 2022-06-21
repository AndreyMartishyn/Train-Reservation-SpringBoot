package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;
import ua.martishyn.app.entities.Route;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.TrainRepository;
import ua.martishyn.app.service.RouteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private RoutePointRepository routePointRepository;

    @Spy
    private TrainRepository trainRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private RouteService routeService;

    private List<RoutePoint> partsOfRoute;
    private Route route;


    @BeforeEach
    void initServiceClass() {
        routeService = new RouteService(routeRepository, trainRepository, routePointRepository, modelMapper);
        partsOfRoute = new ArrayList<>();
        route = new Route();
        route.setRouteId(111);
    }

    @Test
    void shouldReturnEmptyListWhenRoutesNotFound() {
        when(routeRepository.findAll()).thenReturn(Collections.emptyList());
        List<RouteDTO> actual = routeService.getAllRoutesDTO();
        assertEquals(0, actual.size());
    }

    @Test
    void shouldCollectRoutePointsForUserWhenUserSelectRoute() {
        RoutePoint routePointA = new RoutePoint();
        routePointA.setId(1);
        routePointA.setRoute(route);
        RoutePoint routePointB = new RoutePoint();
        routePointB.setId(2);
        routePointA.setRoute(route);
        RoutePoint routePointC = new RoutePoint();
        routePointC.setId(3);
        routePointA.setRoute(route);
        RoutePoint routePointD = new RoutePoint();
        routePointD.setId(4);
        routePointA.setRoute(route);

        route.setRoutePoints(partsOfRoute);

        partsOfRoute.add(routePointA);
        partsOfRoute.add(routePointB);
        partsOfRoute.add(routePointC);
        partsOfRoute.add(routePointD);
        when(routeRepository.findById(111)).thenReturn(java.util.Optional.of(route));

        List<RoutePointDTO> subList = routeService.collectRoutePointsForView(111, 2, 4);
        assertEquals(3, subList.size());
        Assertions.assertNotNull(subList);
        assertEquals(subList.get(0).getId(), routePointB.getId());
        assertEquals(subList.get(2).getId(), routePointD.getId());
    }

    @Test
    void shouldAddRouteWhenItIsEmpty() {
        RouteDTO routeDTO = RouteDTO.builder()
                .id(111)
                .build();
        when(routeRepository.save(route)).thenReturn(route);
        routeService.addRouteWithoutStations(routeDTO);
    }

    @Test
    void shouldAddOrUpdateRoutePointInExistingRoute() {
        RoutePointDTO routePointDTO = RoutePointDTO.builder()
                .id(111)
                .build();
        RoutePoint routePoint = new RoutePoint();
        routePoint.setId(111);
        when(routePointRepository.save(routePoint)).thenReturn(routePoint);
        routeService.addOrUpdateRoutePointToExistingRoute(routePointDTO);
    }

    @Test
    void shouldGetRoutePointDtoById(){
        RoutePoint routePoint = new RoutePoint();
        routePoint.setId(111);
        when(routePointRepository.getById(111)).thenReturn(routePoint);
        RoutePointDTO routePointDtoById = routeService.getRoutePointDtoById(111);
        Assertions.assertNotNull(routePointDtoById);
        Assertions.assertEquals(111, routePointDtoById.getId());
    }
}


//public RoutePointDTO getRoutePointDtoById(Integer id) {
//        return convertRoutePointToDTO(routePointRepository.getById(id));
//    }