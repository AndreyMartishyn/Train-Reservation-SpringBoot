package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wagon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wagon implements Serializable {
    private static final long serialVersionUID = 8426687904793675718L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Enumerated(EnumType.STRING)
    @Column(name = "type" ,nullable = false)
    private Type type;

    @Column(name = "num_of_seats" , nullable = false)
    private Integer numOfSeats;

    @Column(name = "base_price" ,nullable = false)
    private Integer basePrice;

}
