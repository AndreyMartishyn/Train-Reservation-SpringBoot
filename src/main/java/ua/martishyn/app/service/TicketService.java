package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.*;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.repositories.RoutePointRepository;
import ua.martishyn.app.repositories.TicketDetailsRepository;
import ua.martishyn.app.repositories.TicketRepository;
import ua.martishyn.app.service.helpers.TicketHelper;
import ua.martishyn.app.utils.enums.TicketStatus;

import java.util.List;

@Service
public class TicketService {
    private final RoutePointRepository routePointRepository;
    private final PassengerService passengerService;
    private final TicketRepository ticketRepository;
    private final TicketDetailsRepository ticketDetailsRepository;
    private final EmailService emailService;
    private final TicketHelper ticketHelper;

    @Autowired
    public TicketService(RoutePointRepository routePointRepository,
                         PassengerService passengerService,
                         TicketRepository ticketRepository,
                         TicketDetailsRepository ticketDetailsRepository,
                         EmailService emailService) {
        this.routePointRepository = routePointRepository;
        this.passengerService = passengerService;
        this.ticketRepository = ticketRepository;
        this.ticketDetailsRepository = ticketDetailsRepository;
        this.emailService = emailService;
        this.ticketHelper = new TicketHelper();
    }

    public List<Ticket> getAllUsersTickets(User user) {
        return ticketRepository.findAllByUser(user);
    }

    //TODO: DTO TICKET AND REFACTOR
    public void createTicketFromUserData(BookingData bookingData, User user) {
        RoutePoint departureStation = routePointRepository.getById(bookingData.getFrom());
        RoutePoint arrivalStation = routePointRepository.getById(bookingData.getTo());
        Ticket newTicket = ticketHelper.createTicketFromData(user, departureStation, arrivalStation);
        newTicket.setTicketDetails(createTicketDetails(bookingData));
        newTicket.setPassengerDetails(getPassengerFromData(bookingData));
        newTicket.setTicketDetails(createTicketDetails(bookingData));
        ticketRepository.save(newTicket);
        emailService.sendBookingNotification(user);
    }

    private TicketDetails createTicketDetails(BookingData bookingData) {
        TicketDetails ticketDetails = ticketHelper.createTicketDetails(bookingData);
        return ticketDetailsRepository.save(ticketDetails);
    }

    private PassengerDetails getPassengerFromData(BookingData bookingData) {
        PassengerDetails passengerDetails = ticketHelper.getPassengerDetails(bookingData);
        return passengerService.saveNewPassenger(passengerDetails);
    }

    public boolean payForTicket(Integer id, User user) {
        //implement working with users balance. Reason of boolean return type
        Ticket ticketFromDb = ticketRepository.getById(id);
        ticketFromDb.getTicketDetails().setStatus(TicketStatus.PAID);
        ticketRepository.save(ticketFromDb);
        emailService.sendOrderPayedConfirmation(user);
        return true;
    }

    public boolean cancelBooking(Integer id, User user) {
        //implement replenish of occupied place. Reason of boolean return type
        ticketRepository.delete(ticketRepository.getById(id));
        emailService.sendBookingCancellation(user);
        return true;
    }
}
