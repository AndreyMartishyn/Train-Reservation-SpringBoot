package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "departure_id")
    private RoutePoint departure;

    @OneToOne
    @JoinColumn(name = "arrival_id")
    private RoutePoint arrival;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private PassengerDetails passengerDetails;

    @ManyToOne
    @JoinColumn(name = "ticket_details")
    private TicketDetails ticketDetails;
}
