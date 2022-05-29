package ua.martishyn.app.entities;

import lombok.*;
import ua.martishyn.app.utils.Role;

import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="pass_encoded")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name="email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}

