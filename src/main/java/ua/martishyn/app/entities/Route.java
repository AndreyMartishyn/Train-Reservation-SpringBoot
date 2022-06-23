package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "route")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Route implements Serializable {
    private static final long serialVersionUID = -4990065531228554182L;

    @Id
    @Column(name = "routeId")
    private Integer routeId;

    @OneToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,
    mappedBy = "route")
    private List<RoutePoint> routePoints;
}
