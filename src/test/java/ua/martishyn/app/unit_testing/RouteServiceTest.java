package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.martishyn.app.entities.Route;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.models.route.RoutePointDTO;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.service.RouteService;
import ua.martishyn.app.service.TrainService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private RoutePointRepository routePointRepository;

    @Mock
    private TrainService trainService;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private RouteService routeService;

    private List<RoutePoint> partsOfRoute;
    private Route route;


    @BeforeEach
    void initServiceClass() {
        routeService = new RouteService(routeRepository, trainService, routePointRepository, modelMapper);
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
        assertNotNull(subList);
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
    void shouldGetRoutePointDtoById() {
        RoutePoint routePoint = new RoutePoint();
        routePoint.setId(111);
        when(routePointRepository.getById(111)).thenReturn(routePoint);
        RoutePointDTO routePointDtoById = routeService.getRoutePointDtoById(111);
        assertNotNull(routePointDtoById);
        assertEquals(111, routePointDtoById.getId());
    }

    @Test
    void shouldReturnListOfIdsWhenRoutesAreNotEmpty() {
        Route routeA = new Route();
        RoutePoint routePoint1 = new RoutePoint();
        RoutePoint routePoint2 = new RoutePoint();
        List<RoutePoint> routePointsA = new ArrayList<>();
        routePointsA.add(routePoint1);
        routePointsA.add(routePoint2);
        routeA.setRoutePoints(routePointsA);
        routeA.setRouteId(1);

        Route routeB = new Route();
        RoutePoint routePoint3 = new RoutePoint();
        RoutePoint routePoint4 = new RoutePoint();
        List<RoutePoint> routePointsB = new ArrayList<>();
        routePointsB.add(routePoint3);
        routePointsB.add(routePoint4);
        routeB.setRoutePoints(routePointsB);
        routeB.setRouteId(2);

        Route emptyRoute = new Route();
        emptyRoute.setRouteId(22);
        emptyRoute.setRoutePoints(new ArrayList<>());

        List<Route> routes = new ArrayList<>();
        routes.add(routeA);
        routes.add(routeB);
        routes.add(emptyRoute);
        when(routeRepository.findAll()).thenReturn(routes);
        List<Integer> allNotEmptyRoutesIds = routeService.getAllNotEmptyRoutesIds();
        assertNotNull(allNotEmptyRoutesIds);
        assertEquals(2, allNotEmptyRoutesIds.size());
        assertEquals(1, allNotEmptyRoutesIds.get(0));
        assertEquals(2, allNotEmptyRoutesIds.get(1));
    }

    @Test
    void whenDeleteRouteIsVerifiedCall(){
        doNothing().when(routeRepository).deleteById(any());
        routeService.deleteRoute(any());
        verify(routeRepository, times(1)).deleteById(any());
    }

    @Test
    void whenDeleteRoutePointIsVerifiedCall(){
        doNothing().when(routePointRepository).deleteById(any());
        routeService.deleteRoutePoint(any());
        verify(routePointRepository, times(1)).deleteById(any());
    }
}


