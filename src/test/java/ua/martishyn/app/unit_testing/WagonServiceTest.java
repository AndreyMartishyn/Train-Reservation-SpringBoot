package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ua.martishyn.app.entities.Route;
import ua.martishyn.app.entities.Wagon;
import ua.martishyn.app.models.WagonDTO;
import ua.martishyn.app.repositories.RouteRepository;
import ua.martishyn.app.repositories.WagonRepository;
import ua.martishyn.app.service.WagonService;
import ua.martishyn.app.utils.enums.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WagonServiceTest {
    @Mock
    private WagonRepository wagonRepository;

    @Mock
    private RouteRepository routeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private WagonService wagonService;
    private List<WagonDTO> wagonDTOList;

    @BeforeEach
    void initServiceClass() {
        wagonService = new WagonService(wagonRepository, routeRepository, modelMapper);
        wagonDTOList = new ArrayList<>();
        wagonDTOList.add(WagonDTO.builder()
                .id(1)
                .route(111)
                .type(Type.SECOND)
                .numOfSeats(1)
                .basePrice(20)
                .build());
        wagonDTOList.add(WagonDTO.builder()
                .id(2)
                .route(111)
                .type(Type.SECOND)
                .numOfSeats(10)
                .basePrice(20)
                .build());
        wagonDTOList.add(WagonDTO.builder()
                .id(2)
                .route(111)
                .type(Type.FIRST)
                .numOfSeats(5)
                .basePrice(30)
                .build());
    }

    @Test
    void shouldReturnListOfDTOWagonWhenFindingAll() {
        when(wagonRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, wagonService.getAllWagonsDTO().size());
        Assertions.assertNotNull(wagonService.getAllWagonsDTO());
    }

    @Test
    void shouldReturnListOfDTOWagonWhenFindingAllByRouteId() {
        when(wagonRepository.findAllWagonsByRouteId(anyInt())).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, wagonService.getAllWagonsByRouteId(anyInt()).size());
        Assertions.assertNotNull(wagonService.getAllWagonsByRouteId(anyInt()));
    }

    @Test
    void shouldGetNumberOfPlacesForCertainTypeWagon() {
        int placesQuantity = wagonService.getClassPlaces(wagonDTOList, Type.SECOND);
        Assertions.assertEquals(11, placesQuantity);
    }

    @Test
    void shouldReturnPriceForWagonsBasedOnNumberOfStationsPassedWhenFirstType() {
        int placePrice = wagonService.getPriceForClassSeat(wagonDTOList, Type.FIRST, 3);
        Assertions.assertEquals(90, placePrice);
    }

    @Test
    void shouldReturnPriceForWagonsBasedOnNumberOfStationsPassedWhenSecondType() {
        int placePrice = wagonService.getPriceForClassSeat(wagonDTOList, Type.SECOND, 3);
        Assertions.assertEquals(60, placePrice);
    }

    @Test
    void shouldReturnTrueWagonBookedWagonHasMoreThanZeroPlaces(){
        Wagon wagon = new Wagon();
        wagon.setId(1);
        wagon.setNumOfSeats(1);
        when(wagonRepository.getById(anyInt())).thenReturn(wagon);
        boolean result = wagonService.checkForAvailablePlaceAndBook(1);
        Assertions.assertEquals(0, wagon.getNumOfSeats());
        Assertions.assertTrue(result);

    }
    @Test
    void shouldReturnFalseWhenBookedWagonHasNoFreePlaces(){
        Wagon wagon = new Wagon();
        wagon.setId(1);
        wagon.setNumOfSeats(0);
        when(wagonRepository.getById(anyInt())).thenReturn(wagon);
        boolean result = wagonService.checkForAvailablePlaceAndBook(1);
        Assertions.assertEquals(0, wagon.getNumOfSeats());
        Assertions.assertFalse(result);
    }

    @Test
    void shouldIncrementAvailableSeatsNumberWhenBookingCancelled(){
        Wagon wagon = new Wagon();
        wagon.setId(1);
        wagon.setNumOfSeats(0);
        when(wagonRepository.getById(anyInt())).thenReturn(wagon);
        when(wagonRepository.save(any())).thenReturn(any());
        wagonService.replenishPlaceAfterCancelling(1);
        Assertions.assertEquals(1, wagon.getNumOfSeats());
    }

    @Test
    void whenDeleteWagonIsVerifiedCall(){
        doNothing().when(wagonRepository).deleteById(any());
        wagonService.deleteWagon(any());
        verify(wagonRepository, times(1)).deleteById(any());
    }
}