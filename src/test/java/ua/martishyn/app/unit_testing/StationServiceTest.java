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
import ua.martishyn.app.entities.Station;
import ua.martishyn.app.repositories.StationRepository;
import ua.martishyn.app.service.StationService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private StationService stationService;

    private Station station;

    @BeforeEach
    void initServiceClass() {
        stationService = new StationService(stationRepository, modelMapper);
        station = new Station();
        station.setId(1);
        station.setName("station");
        station.setCode("ST");
    }

    @Test
    void shouldReturnStationWhenGettingById() {
        when(stationRepository.findStationById(anyInt())).thenReturn(Optional.of(station));
        Assertions.assertNotNull(stationService.getStationDtoById(anyInt()));
    }

    @Test
    void shouldReturnFalseIfUserIsNotPresentWhenDeletingById() {
        when(stationRepository.findStationById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertFalse(stationService.deleteStationById(anyInt()));
    }

    @Test
    void shouldReturnTrueIfUserIsPresentWhenDeletingById() {
        when(stationRepository.findStationById(anyInt())).thenReturn(Optional.of(station));
        Assertions.assertTrue(stationService.deleteStationById(anyInt()));
    }


}
