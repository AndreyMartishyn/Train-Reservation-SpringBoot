package ua.martishyn.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final WagonService wagonService;
    private final TicketRepository ticketRepository;
    private final TicketDetailsRepository ticketDetailsRepository;
    private final EmailService emailService;
    private final TicketHelper ticketHelper;

    @Autowired
    public TicketService(RoutePointRepository routePointRepository,
                         PassengerService passengerService,
                         TicketRepository ticketRepository,
                         TicketDetailsRepository ticketDetailsRepository,
                         EmailService emailService,
                         WagonService wagonService) {
        this.routePointRepository = routePointRepository;
        this.passengerService = passengerService;
        this.ticketRepository = ticketRepository;
        this.ticketDetailsRepository = ticketDetailsRepository;
        this.emailService = emailService;
        this.ticketHelper = new TicketHelper();
        this.wagonService = wagonService;
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllUsersTickets(User user) {
        return ticketRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketByItsId(Integer id) {
        return ticketRepository.getById(id);
    }

    //TODO: DTO TICKET AND REFACTOR
    @Transactional
    public void createTicketFromUserData(BookingData bookingData, User user) {
        RoutePoint departureStation = routePointRepository.getById(bookingData.getFrom());
        RoutePoint arrivalStation = routePointRepository.getById(bookingData.getTo());
        Ticket newTicket = ticketHelper.createTicketBase(user, departureStation, arrivalStation);
        newTicket.setTicketDetails(createTicketDetails(bookingData));
        newTicket.setPassengerDetails(createPassengerDetails(bookingData));
        ticketRepository.save(newTicket);
        emailService.sendBookingNotification(user);
    }

    @Transactional
    public TicketDetails createTicketDetails(BookingData bookingData) {
        TicketDetails ticketDetails = ticketHelper.createTicketDetails(bookingData);
        return ticketDetailsRepository.save(ticketDetails);
    }

    @Transactional
    public PassengerDetails createPassengerDetails(BookingData bookingData) {
        PassengerDetails passengerDetails = ticketHelper.createPassengerDetails(bookingData);
        return passengerService.saveNewPassenger(passengerDetails);
    }

    @Transactional
    public void payForTicket(Integer id, User user) {
        //implement working with users balance. Reason of boolean return type
        Ticket ticketFromDb = ticketRepository.getById(id);
        ticketFromDb.getTicketDetails().setStatus(TicketStatus.PAID);
        ticketRepository.save(ticketFromDb);
        emailService.sendOrderPayedConfirmation(user);
    }

    @Transactional
    public void cancelBooking(Integer id, User user) {
        Ticket ticketFromDB
                = ticketRepository.getById(id);
        Integer numOfBookedWagon = ticketFromDB.getTicketDetails().getWagonNum();
        wagonService.replenishPlaceAfterCancelling(numOfBookedWagon);
        ticketRepository.delete(ticketRepository.getById(id));
        emailService.sendBookingCancellation(user);
    }
}
