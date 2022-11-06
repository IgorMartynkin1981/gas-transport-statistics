package ru.alrosa.transport.gastransportstatistics.plans.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "plan_gas_consumption")
public class Plan {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long UserId;
    @Column(name = "subdivision_id", nullable = false)
    private Long subdivisionId;
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;
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
