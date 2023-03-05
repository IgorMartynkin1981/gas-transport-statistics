package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.dto.WeeklyReportDto;

import java.util.Collection;

public interface WeeklyReportService {
    Collection<InfoWeeklyReportDto> getAllWeeklyReport(Long subdivisionId, String periodStart, String periodEnd);

    InfoWeeklyReportDto getWeeklyReportById(Long weeklyReportId);

    InfoWeeklyReportDto updateWeeklyReport(WeeklyReportDto weeklyReportDto);

    InfoWeeklyReportDto createWeeklyReport(WeeklyReportDto weeklyReportDto);

    void deleteWeeklyReportById(Long weeklyReportId);
}
