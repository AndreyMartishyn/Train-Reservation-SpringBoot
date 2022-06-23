package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "train")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Train implements Serializable {
    private static final long serialVersionUID = -7642926538780620188L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "model_id" ,nullable = false)
    private TrainModel model;
}
