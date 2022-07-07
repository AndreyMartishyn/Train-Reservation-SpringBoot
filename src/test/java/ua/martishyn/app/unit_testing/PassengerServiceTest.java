package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.martishyn.app.entities.PassengerDetails;
import ua.martishyn.app.repositories.PassengerRepository;
import ua.martishyn.app.service.PassengerService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void shouldSavePassengerWhenCreatingTicket(){
        PassengerDetails passengerDetails = new PassengerDetails();
        when(passengerRepository.save(passengerDetails)).thenReturn(passengerDetails);
        passengerService.saveNewPassenger(passengerDetails);
    }
}
