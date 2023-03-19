package ru.alrosa.transport.gastransportstatistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.services.WeeklyReportService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/weekly_report")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    @Autowired
    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @GetMapping
    public Collection<InfoWeeklyReportDto> getAllWeeklyReport(@RequestParam(name = "subdivisionId", defaultValue = "0")
                                                              Long subdivisionId,
                                                              @RequestParam(name = "periodStart", defaultValue = "1900-01-01")
                                                              String periodStart,
                                                              @RequestParam(name = "periodEnd", defaultValue = "2200-01-01")
                                                              String periodEnd) {
        return weeklyReportService.getAllWeeklyReport(subdivisionId, periodStart, periodEnd);
    }

    @GetMapping("/{id}")
    public InfoWeeklyReportDto getWeeklyReportById(@PathVariable Long id) {
        return weeklyReportService.getWeeklyReportById(id);
    }
}
