package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.martishyn.app.models.booking.MatchingRoute;
import ua.martishyn.app.models.route.RouteDTO;
import ua.martishyn.app.service.RouteFinderService;
import ua.martishyn.app.service.RouteService;
import ua.martishyn.app.service.StationService;
import ua.martishyn.app.service.WagonService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteFinderTest extends RouteFinderTestHelper {

    @InjectMocks
    private RouteFinderService routeFinderService;

    @Mock
    private RouteService routeService;


    @Mock
    private WagonService wagonService;


    @Test
    void shouldFindTwoSuitableRoutesWhenSearchingBetweenStations() {
        RouteDTO routeA = createRoute();
        RouteDTO routeB = createRoute();
        List<RouteDTO> routes = new ArrayList<>();
        routes.add(routeA);
        routes.add(routeB);

        when(routeService.getAllRoutesDTO()).thenReturn(routes);
        Assertions.assertNotNull(routeService.getAllRoutesDTO());
        List<MatchingRoute> matchingRoutes = routeFinderService.makeBooking(1, 5);
        Assertions.assertNotNull(matchingRoutes);
        Assertions.assertEquals(2, matchingRoutes.size());
    }

    @Test
    void firstAndLastStationsOfMatchingRootMustBeEqualToUserSearching() {
        RouteDTO routeA = createRoute();
        List<RouteDTO> routes = new ArrayList<>();
        routes.add(routeA);

        when(routeService.getAllRoutesDTO()).thenReturn(routes);
        Assertions.assertNotNull(routeService.getAllRoutesDTO());
        List<MatchingRoute> matchingRoutes = routeFinderService.makeBooking(1, 2);
        MatchingRoute matchingRoute = matchingRoutes.get(0);
        Assertions.assertNotNull(matchingRoute);
        Assertions.assertEquals(1, matchingRoute.getRouteDTO().getFirstStation().getStation().getId());
        Assertions.assertEquals(LocalDateTime.parse("2022-12-01T10:15:30"), matchingRoute.getRouteDTO().getFirstStation().getDeparture());
        Assertions.assertEquals(2, matchingRoute.getRouteDTO().getLastStation().getStation().getId());
        Assertions.assertEquals(LocalDateTime.parse("2022-12-01T10:40:30"), matchingRoute.getRouteDTO().getLastStation().getArrival());
    }

    @Test
    void shouldReturnEmptyListOfMatchingRoutesWhenNoRoutesForUsersSearch() {
        RouteDTO routeA = createRoute();
        RouteDTO routeB = createRoute();
        List<RouteDTO> routes = new ArrayList<>();
        routes.add(routeA);
        routes.add(routeB);

        when(routeService.getAllRoutesDTO()).thenReturn(routes);
        Assertions.assertNotNull(routeService.getAllRoutesDTO());
        List<MatchingRoute> matchingRoutes = routeFinderService.makeBooking(1, 8);
        Assertions.assertTrue(matchingRoutes.isEmpty());
    }

    @Test
    void shouldReturnCorrectRouteDurationBasedOnUsersSearch() {
        RouteDTO routeA = createRoute();
        List<RouteDTO> routes = new ArrayList<>();
        routes.add(routeA);

        when(routeService.getAllRoutesDTO()).thenReturn(routes);
        Assertions.assertNotNull(routeService.getAllRoutesDTO());
        MatchingRoute matchingRoute = routeFinderService.makeBooking(1, 2).get(0);
        Assertions.assertEquals("0:25", matchingRoute.getRoadTime());
    }
}
