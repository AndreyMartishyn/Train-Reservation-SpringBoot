package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.config.security.UserPrincipal;
import ua.martishyn.app.entities.*;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.TicketDetailsRepository;
import ua.martishyn.app.repositories.TicketRepository;
import ua.martishyn.app.utils.enums.TicketStatus;
import ua.martishyn.app.utils.enums.Type;

import java.util.List;

@Service
public class TicketService {
    private final RoutePointRepository routePointRepository;
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;
    private final TicketDetailsRepository ticketDetailsRepository;

    @Autowired
    public TicketService(RoutePointRepository routePointRepository,
                         PassengerService passengerService,
                         TicketRepository ticketRepository,
                         TicketDetailsRepository ticketDetailsRepository) {
        this.routePointRepository = routePointRepository;
        this.passengerService = passengerService;
        this.ticketRepository = ticketRepository;
        this.ticketDetailsRepository = ticketDetailsRepository;
    }

    public List<Ticket> getAllUsersTickets(User user){
        return ticketRepository.findAllByUser(user);
    }

    public void createTicketFromUserData(BookingData bookingData, UserPrincipal currentUser) {
        Ticket ticket = new Ticket();
        ticket.setUser(currentUser.getUser());
        ticket.setTicketDetails(createTicketDetails(bookingData));
        ticket.setPassengerDetails(getPassengerFromData(bookingData));
        final RoutePoint departureStation = routePointRepository.getById(bookingData.getFrom());
        final RoutePoint arrivalStation = routePointRepository.getById(bookingData.getTo());
        ticket.setDeparture(departureStation);
        ticket.setArrival(arrivalStation);
        ticketRepository.save(ticket);
    }


    private TicketDetails createTicketDetails(BookingData bookingData) {
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setWagonNum(bookingData.getWagonNum());
        ticketDetails.setDuration(bookingData.getDuration());
        ticketDetails.setType(Type.valueOf(bookingData.getType()));
        ticketDetails.setPrice(bookingData.getPrice());
        ticketDetails.setStatus(TicketStatus.NOT_PAID);
        return ticketDetailsRepository.save(ticketDetails);
    }

    private PassengerDetails getPassengerFromData(BookingData bookingData) {
        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setFirstName(bookingData.getFirstName());
        passengerDetails.setLastName(bookingData.getLastName());
        return passengerService.saveNewPassenger(passengerDetails);
    }

}
