package ua.martishyn.app.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table (name = "route_point")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoutePoint implements Serializable{
    private static final long serialVersionUID = -258390888911213382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="route_id")
    private Route route;

    @OneToOne
    @JoinColumn(name="station_id")
    private Station station;

    @Column(name = "arrival")
    private LocalDateTime arrival;

    @Column(name = "departure")
    private LocalDateTime departure;

}
