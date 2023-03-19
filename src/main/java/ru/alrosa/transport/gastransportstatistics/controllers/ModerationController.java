package ru.alrosa.transport.gastransportstatistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.dto.WeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.services.PlanService;
import ru.alrosa.transport.gastransportstatistics.services.UserService;
import ru.alrosa.transport.gastransportstatistics.services.WeeklyReportService;

import java.security.Principal;

/**
 * REST controller for moderation requests (create plan/report, etc.)
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@RestController
public class ModerationController {

    private final UserService userService;
    private final PlanService planService;
    private final WeeklyReportService weeklyReportService;

    @Autowired
    public ModerationController(UserService userService,
                                PlanService planService,
                                WeeklyReportService weeklyReportService) {
        this.userService = userService;
        this.planService = planService;
        this.weeklyReportService = weeklyReportService;
    }

    @GetMapping("/moderator_console")
    public String moderatorPage(Principal principal) {
        return String.format("This test page from role MODERATOR, user -> " + principal.getName());
    }

    @PatchMapping("/moderator_console/delete/{id}")
    public ResponseEntity<?> deactivationUser(@PathVariable Long id) {
        userService.deactivationUser(id);
        return new ResponseEntity<>(String.format("User with ID: %s was deactivation!", id), HttpStatus.OK);
    }

    @PostMapping("/moderator_console/plan")
    public InfoPlanDto createPlan(@RequestBody PlanDto planDto) {
        return planService.createPlanDto(planDto);
    }

    @PatchMapping("/moderator_console/plan/{id}")
    public InfoPlanDto updatePlan(@RequestBody PlanDto planDto) {
        return planService.updatePlanDto(planDto);
    }

    @DeleteMapping("/moderator_console/plan/{id}")
    public ResponseEntity<String> deletePlanById(@PathVariable Long id) {
        planService.deletePlanDtoById(id);
        return new ResponseEntity<>(String.format("Plan with ID: %s was deleted!", id), HttpStatus.OK);
    }

    @PostMapping("/moderator_console/weekly_report")
    public InfoWeeklyReportDto createWeeklyReport(@RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.createWeeklyReport(weeklyReportDto);
    }

    @PatchMapping("/moderator_console/weekly_report/{id}")
    public InfoWeeklyReportDto updateWeeklyReport(@RequestBody WeeklyReportDto weeklyReportDto) {
        return weeklyReportService.updateWeeklyReport(weeklyReportDto);
    }

    @DeleteMapping("/moderator_console/weekly_report/{id}")
    public ResponseEntity<String> deleteWeeklyReportById(@PathVariable Long id) {
        weeklyReportService.deleteWeeklyReportById(id);
        return new ResponseEntity<>(String.format("Plan with ID: %s was deleted!", id), HttpStatus.OK);
    }
}
