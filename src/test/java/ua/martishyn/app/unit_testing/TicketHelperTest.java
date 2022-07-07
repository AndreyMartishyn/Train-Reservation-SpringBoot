package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.martishyn.app.entities.*;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.service.helpers.TicketHelper;
import ua.martishyn.app.utils.enums.Type;

class TicketHelperTest {

    private TicketHelper ticketHelper;

    private BookingData bookingData;

    @BeforeEach
    void setUp() {
        ticketHelper = new TicketHelper();
        bookingData = BookingData.builder()
                .firstName("testF")
                .lastName("testL")
                .wagonNum(1)
                .duration("00:01")
                .type("FIRST")
                .price(100)
                .build();
    }

    @Test
    void shouldCreateTicketDetailsWhenUserOrderingTicket() {
        TicketDetails ticketDetails = ticketHelper.createTicketDetails(bookingData);
        Assertions.assertNotNull(ticketDetails);
        Assertions.assertEquals(1, ticketDetails.getWagonNum());
        Assertions.assertEquals("00:01", ticketDetails.getDuration());
        Assertions.assertEquals(Type.FIRST, ticketDetails.getType());
        Assertions.assertEquals(100, ticketDetails.getPrice());
    }

    @Test
    void shouldCreatePassengerDetailsWhenOrderingTicket(){
        PassengerDetails passengerDetails = ticketHelper.createPassengerDetails(bookingData);
        Assertions.assertNotNull(passengerDetails);
        Assertions.assertEquals("testF", passengerDetails.getFirstName());
        Assertions.assertEquals("testL", passengerDetails.getLastName());
    }

    @Test
    void shouldCreateTicketBaseWhenOrderingTicket(){
        User user = new User();
        RoutePoint routePointA = new RoutePoint();
        RoutePoint routePointB = new RoutePoint();
        Ticket ticketBase = ticketHelper.createTicketBase(user, routePointA, routePointB);
        Assertions.assertNotNull(ticketBase);
    }

    @AfterEach
    void tearDown(){
        ticketHelper = null;
        bookingData = null;
    }
}
