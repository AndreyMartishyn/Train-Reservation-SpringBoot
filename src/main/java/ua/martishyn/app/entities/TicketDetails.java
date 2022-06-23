package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.TicketStatus;
import ua.martishyn.app.utils.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ticket_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDetails implements Serializable {

    private static final long serialVersionUID = 3520022601526710984L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "wagon", nullable = false)
    private Integer wagonNum;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "ticket_type" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "total_price" ,nullable = false)
    private Integer price;

    @Column(name = "status" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}