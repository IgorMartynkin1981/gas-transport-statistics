package ru.alrosa.transport.gastransportstatistics.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Simple domain object that represents application roles
 * Simple domain object that represents application user's role - ADMIN, USER, etc.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true)
    private String name;
}
