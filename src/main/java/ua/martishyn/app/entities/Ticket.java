package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
    private static final long serialVersionUID = -3867602400461722257L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "departure_id", nullable = false)
    private RoutePoint departure;

    @OneToOne
    @JoinColumn(name = "arrival_id", nullable = false)
    private RoutePoint arrival;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "passenger_id", nullable = false)
    private PassengerDetails passengerDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn( name = "ticket_details", nullable = false)
    private TicketDetails ticketDetails;
}
