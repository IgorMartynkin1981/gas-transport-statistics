package ru.alrosa.transport.gastransportstatistics.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanDto {
    private Long id;
    private Long userId;
    private Long subdivisionId;
    private LocalDate creationTime;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private double planConsumptionGas;
    private double planDistanceGas;
    private double planConsumptionDt;
    private double planDistanceDt;
}
