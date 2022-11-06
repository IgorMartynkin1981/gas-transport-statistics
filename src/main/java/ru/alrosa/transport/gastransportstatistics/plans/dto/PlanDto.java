package ru.alrosa.transport.gastransportstatistics.plans.dto;

import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlanDto {
    @Positive(groups = {Create.class})
    private Long id;
    private Long UserId;
    @NotBlank(groups = {Create.class})
    private Long subdivisionId;
    private LocalDateTime creationTime;
    @NotBlank(groups = {Create.class})
    private LocalDate periodStart;
    @NotBlank(groups = {Create.class})
    private LocalDate periodEnd;
    @NotBlank(groups = {Create.class})
    private double planConsumptionGas;
    @NotBlank(groups = {Create.class})
    private double planDistanceGas;
    private double planConsumptionDt;
    private double planDistanceDt;
}
