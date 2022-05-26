package ua.martishyn.app.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="pass_encoded")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private Role role;
}

