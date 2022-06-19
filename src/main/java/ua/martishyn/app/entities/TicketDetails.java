package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.TicketStatus;
import ua.martishyn.app.utils.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ticket_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "wagon")
    private Integer wagonNum;

    @Column(name = "duration")
    private String duration;

    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "total_price")
    private Integer price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}