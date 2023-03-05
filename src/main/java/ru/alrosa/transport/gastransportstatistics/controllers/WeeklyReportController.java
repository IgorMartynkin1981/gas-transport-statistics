package ru.alrosa.transport.gastransportstatistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.dto.WeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.services.WeeklyReportService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/weeklyreport")
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

    @PostMapping
    public InfoWeeklyReportDto createWeeklyReport(@RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.createWeeklyReport(weeklyReportDto);
    }

    @PatchMapping("/{id}")
    public InfoWeeklyReportDto updateWeeklyReport(@RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.updateWeeklyReport(weeklyReportDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWeeklyReportById(@PathVariable Long id) {
        weeklyReportService.deleteWeeklyReportById(id);
        return new ResponseEntity<>(String.format("Plan with ID: %s was deleted!", id), HttpStatus.OK);
    }
}
