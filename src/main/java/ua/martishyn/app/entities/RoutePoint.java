package ua.martishyn.app.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "route_segment")
public class RoutePoint implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RoutePointPk id;

    @OneToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "arrival")
    private LocalDateTime arrival;

    @Column(name = "departure")
    private LocalDateTime departure;


}
