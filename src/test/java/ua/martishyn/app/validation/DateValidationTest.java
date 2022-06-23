package ua.martishyn.app.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.martishyn.app.models.StationDTO;
import ua.martishyn.app.models.route.RoutePointDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

class DateValidationTest {
    private Validator validator;
    private RoutePointDTO routePointDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        routePointDTO = RoutePointDTO.builder()
                .station(StationDTO.builder()
                        .id(1)
                        .build())
                .build();

    }

    @Test
    void shouldReturnFalseWhenBothDatesAreInPast() {
        routePointDTO.setDeparture(LocalDateTime.parse("2022-01-01T10:01:00"));
        routePointDTO.setArrival(LocalDateTime.parse("2022-01-01T10:00:00"));
        Set<ConstraintViolation<RoutePointDTO>> violationList = validator.validate(routePointDTO);
        Assertions.assertFalse(violationList.isEmpty());
    }

    @Test
    void shouldReturnFalseWhenDepartureDateIsBeforeArrivalDate() {
        routePointDTO.setDeparture(LocalDateTime.parse("2022-07-07T10:01:00"));
        routePointDTO.setArrival(LocalDateTime.parse("2022-07-07T10:02:00"));
        Set<ConstraintViolation<RoutePointDTO>> violationList = validator.validate(routePointDTO);
        Assertions.assertFalse(violationList.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenDepartureDateIsAfterArrivalDate() {
        routePointDTO.setDeparture(LocalDateTime.parse("2022-07-07T10:03:00"));
        routePointDTO.setArrival(LocalDateTime.parse("2022-07-07T10:02:00"));
        Set<ConstraintViolation<RoutePointDTO>> violationList = validator.validate(routePointDTO);
        Assertions.assertTrue(violationList.isEmpty());
    }
}
