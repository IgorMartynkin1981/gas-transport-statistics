package ru.alrosa.transport.gastransportstatistics.weeklyReport.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyReportDto {
    private Long id;
    private Long userId;
    private Long subdivisionId;
    private LocalDate creationTime;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private double consumptionGas;
    private double distanceGas;
    private double consumptionDt;
    private double distanceDt;
}
