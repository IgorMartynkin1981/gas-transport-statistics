package ru.alrosa.transport.gastransportstatistics.users.model;

import lombok.*;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_first_name", nullable = false)
    private String firstName;
    @Column(name = "user_last_name", nullable = false)
    private String lastName;
    @Column(length = 512, unique = true)
    private String email;
    @Column(name = "login")
    private String login;
    @OneToOne
    @JoinColumn(name = "subdivision_id")
    private Subdivision subdivision;
}

