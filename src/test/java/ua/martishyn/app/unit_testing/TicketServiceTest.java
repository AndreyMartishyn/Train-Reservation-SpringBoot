package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.entities.Ticket;
import ua.martishyn.app.entities.TicketDetails;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.repositories.PassengerRepository;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.TicketDetailsRepository;
import ua.martishyn.app.repositories.TicketRepository;
import ua.martishyn.app.service.EmailService;
import ua.martishyn.app.service.PassengerService;
import ua.martishyn.app.service.TicketService;
import ua.martishyn.app.service.WagonService;
import ua.martishyn.app.utils.enums.TicketStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RoutePointRepository routePointRepository;

    @Mock
    private TicketDetailsRepository  ticketDetailsRepository;

    @Mock
    private PassengerService passengerService;

    @Mock
    private WagonService wagonService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TicketService ticketService;

    private User user;

    @Test
    void shouldCreateTicket() {
        BookingData bookingData = BookingData.builder()
                .firstName("testF")
                .lastName("testL")
                .wagonNum(1)
                .duration("00:01")
                .type("FIRST")
                .price(100)
                .build();
        user = new User();
        user.setId(1);
        RoutePoint departure = new RoutePoint();
        RoutePoint arrival = new RoutePoint();
        when(routePointRepository.getById(bookingData.getFrom())).thenReturn(departure);
        when(routePointRepository.getById(bookingData.getTo())).thenReturn(arrival);
        when(ticketRepository.save(any())).thenReturn(any());
        when(emailService.sendBookingNotification(user)).thenReturn(true);
        ticketService.createTicketFromUserData(bookingData, user);
        verify(ticketRepository).save(any());
    }

    @Test
    void shouldChangeTicketStatusToPaidWhenUserPaysForTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setStatus(TicketStatus.NOT_PAID);
        ticket.setTicketDetails(ticketDetails);
        when(ticketRepository.getById(1)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        ticketService.payForTicket(1, user);
        Assertions.assertEquals(TicketStatus.PAID, ticket.getTicketDetails().getStatus());
    }

    @Test
    void shouldDeleteTicketAfterCancellationOfBooking() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setWagonNum(2);
        ticket.setTicketDetails(ticketDetails);
        when(ticketRepository.getById(anyInt())).thenReturn(ticket);
        doNothing().when(wagonService).replenishPlaceAfterCancelling(any());
        doNothing().when(ticketRepository).delete(any());
        ticketService.cancelBooking(1, user);
    }

}

