package ru.alrosa.transport.gastransportstatistics.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Simple domain object that represents application weekly_report.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Data
@Entity
@Table(name = "weekly_report")
public class WeeklyReport {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subdivision_id", nullable = false)
    private Subdivision subdivision;

    @Column(name = "creation_time", nullable = false)
    private LocalDate creationTime;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "consumption_gas", nullable = false)
    private double consumptionGas;

    @Column(name = "distance_gas", nullable = false)
    private double distanceGas;

    @Column(name = "consumption_dt", nullable = false)
    private double consumptionDt;

    @Column(name = "distance_dt", nullable = false)
    private double distanceDt;
}
