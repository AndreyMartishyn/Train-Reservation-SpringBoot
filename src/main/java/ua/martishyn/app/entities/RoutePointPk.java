package ua.martishyn.app.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoutePointPk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

}
