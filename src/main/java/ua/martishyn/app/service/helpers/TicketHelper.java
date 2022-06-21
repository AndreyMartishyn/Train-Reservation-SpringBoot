package ua.martishyn.app.service.helpers;

import ua.martishyn.app.entities.*;
import ua.martishyn.app.models.booking.BookingData;
import ua.martishyn.app.utils.enums.TicketStatus;
import ua.martishyn.app.utils.enums.Type;

public class TicketHelper {

    public Ticket createTicketFromData(User user,
                                       RoutePoint departureStation,
                                       RoutePoint arrivalStation) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setDeparture(departureStation);
        ticket.setArrival(arrivalStation);
        return ticket;
    }

    public TicketDetails createTicketDetails(BookingData bookingData) {
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setWagonNum(bookingData.getWagonNum());
        ticketDetails.setDuration(bookingData.getDuration());
        ticketDetails.setType(Type.valueOf(bookingData.getType()));
        ticketDetails.setPrice(bookingData.getPrice());
        ticketDetails.setStatus(TicketStatus.NOT_PAID);
        return ticketDetails;
    }

    public PassengerDetails getPassengerDetails(BookingData bookingData) {
        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setFirstName(bookingData.getFirstName());
        passengerDetails.setLastName(bookingData.getLastName());
        return passengerDetails;
    }
}
