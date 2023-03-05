package ru.alrosa.transport.gastransportstatistics.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.entity.User;

import java.time.LocalDate;

@Data
public class InfoWeeklyReportDto {
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
    private double consumptionGas;
    @NotBlank(groups = {Create.class})
    private double distanceGas;
    @NotBlank(groups = {Create.class})
    private double consumptionDt;
    @NotBlank(groups = {Create.class})
    private double distanceDt;
}
