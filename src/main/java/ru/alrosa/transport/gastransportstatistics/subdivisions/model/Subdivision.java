package ru.alrosa.transport.gastransportstatistics.subdivisions.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "subdivisions")
public class Subdivision {
    @Id
    @Column(name = "subdivision_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subdivision_name", unique = true, nullable = false)
    private String subdivisionName;
    @Column(name = "subdivision_full_name", nullable = false)
    private String subdivisionFullName;
}

