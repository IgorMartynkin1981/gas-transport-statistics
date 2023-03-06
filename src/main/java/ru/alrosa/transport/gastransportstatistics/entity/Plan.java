package ru.alrosa.transport.gastransportstatistics.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Simple domain object that represents application plan.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Data
@Entity
@Table(name = "plan_gas_consumption")
public class Plan {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
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
    @Column(name = "plan_consumption_gas", nullable = false)
    private double planConsumptionGas;
    @Column(name = "plan_distance_gas", nullable = false)
    private double planDistanceGas;
    @Column(name = "plan_consumption_dt", nullable = false)
    private double planConsumptionDt;
    @Column(name = "plan_distance_dt", nullable = false)
    private double planDistanceDt;
}
