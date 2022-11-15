package ru.alrosa.transport.gastransportstatistics.plans.dto;

import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;

import javax.validation.constraints.NotBlank;
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
