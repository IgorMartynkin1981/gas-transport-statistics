package ru.alrosa.transport.gastransportstatistics.plans.dto;

import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;
import ru.alrosa.transport.gastransportstatistics.users.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class InfoPlanDto {
    @Positive(groups = {Create.class})
    private Long id;
    private User user;
    @NotBlank(groups = {Create.class})
    private Subdivision subdivision;
    @NotBlank(groups = {Create.class})
    private LocalDate creationTime;
    @NotBlank(groups = {Create.class})
    private LocalDate periodStart;
    @NotBlank(groups = {Create.class})
    private LocalDate periodEnd;
    @NotBlank(groups = {Create.class})
    private double planConsumptionGas;
    @NotBlank(groups = {Create.class})
    private double planDistanceGas;
    @NotBlank(groups = {Create.class})
    private double planConsumptionDt;
    @NotBlank(groups = {Create.class})
    private double planDistanceDt;
}
