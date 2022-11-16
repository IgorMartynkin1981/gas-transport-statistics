package ru.alrosa.transport.gastransportstatistics.weeklyReport.services;

import ru.alrosa.transport.gastransportstatistics.weeklyReport.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.dto.WeeklyReportDto;

import java.util.Collection;

public interface WeeklyReportService {
    Collection<InfoWeeklyReportDto> getAllWeeklyReport(Long subdivisionId, String periodStart, String periodEnd);

    InfoWeeklyReportDto getWeeklyReportById(Long weeklyReportId);

    InfoWeeklyReportDto updateWeeklyReport(WeeklyReportDto weeklyReportDto);

    InfoWeeklyReportDto createWeeklyReport(WeeklyReportDto weeklyReportDto);

    void deleteWeeklyReportById(Long weeklyReportId);
}
