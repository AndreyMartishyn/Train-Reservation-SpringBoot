package ua.martishyn.app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.martishyn.app.utils.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 2100202973203946216L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "pass_encoded", nullable = false)
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Ticket> tickets;

  }

