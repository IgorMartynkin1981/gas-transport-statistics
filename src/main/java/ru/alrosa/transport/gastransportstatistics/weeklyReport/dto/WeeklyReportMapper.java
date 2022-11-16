package ru.alrosa.transport.gastransportstatistics.weeklyReport.dto;


import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.model.WeeklyReport;

import java.time.LocalDate;

public class WeeklyReportMapper {

    public static InfoWeeklyReportDto toInfoWeeklyReportDto(WeeklyReport weeklyReport) {
        InfoWeeklyReportDto infoWeeklyReportDto = new InfoWeeklyReportDto();
        infoWeeklyReportDto.setId(weeklyReport.getId());
        infoWeeklyReportDto.setUser(weeklyReport.getUser());
        infoWeeklyReportDto.setSubdivision(weeklyReport.getSubdivision());
        infoWeeklyReportDto.setCreationTime(weeklyReport.getCreationTime());
        infoWeeklyReportDto.setPeriodStart(weeklyReport.getPeriodStart());
        infoWeeklyReportDto.setPeriodEnd(weeklyReport.getPeriodEnd());
        infoWeeklyReportDto.setConsumptionGas(weeklyReport.getConsumptionGas());
        infoWeeklyReportDto.setDistanceGas(weeklyReport.getDistanceGas());
        infoWeeklyReportDto.setConsumptionDt(weeklyReport.getConsumptionDt());
        infoWeeklyReportDto.setDistanceDt(weeklyReport.getDistanceDt());

        return infoWeeklyReportDto;
    }

    public static WeeklyReport toWeeklyReport(WeeklyReportDto weeklyReportDto, User user, Subdivision subdivision) {
        WeeklyReport weeklyReport = new WeeklyReport();
        if (weeklyReportDto.getId() != null) {
            weeklyReport.setId(weeklyReportDto.getId());
        }
        weeklyReport.setUser(user);
        weeklyReport.setSubdivision(subdivision);
        if (weeklyReportDto.getCreationTime() != null) {
            weeklyReport.setCreationTime(weeklyReportDto.getCreationTime());
        } else {
            weeklyReport.setCreationTime(LocalDate.now());
        }
        weeklyReport.setPeriodStart(weeklyReportDto.getPeriodStart());
        weeklyReport.setPeriodEnd(weeklyReportDto.getPeriodEnd());
        weeklyReport.setConsumptionGas(weeklyReportDto.getConsumptionGas());
        weeklyReport.setDistanceGas(weeklyReportDto.getDistanceGas());
        weeklyReport.setConsumptionDt(weeklyReportDto.getConsumptionDt());
        weeklyReport.setDistanceDt(weeklyReportDto.getDistanceDt());

        return weeklyReport;
    }
}
