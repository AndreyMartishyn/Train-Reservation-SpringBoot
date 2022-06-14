package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "train_model")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainModel implements Serializable {
    private static final long serialVersionUID = 3277775474019366782L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "model")
    private String model;
}
