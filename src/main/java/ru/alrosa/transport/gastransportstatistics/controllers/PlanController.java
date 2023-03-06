package ru.alrosa.transport.gastransportstatistics.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.services.PlanService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/plans")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public Collection<InfoPlanDto> getAllPlans(@RequestParam(name = "subdivisionId", defaultValue = "0")
                                               Long subdivisionId,
                                               @RequestParam(name = "periodStart", defaultValue = "1900-01-01")
                                               String periodStart,
                                               @RequestParam(name = "periodEnd", defaultValue = "2200-01-01")
                                               String periodEnd) {
        return planService.getAllPlanDto(subdivisionId, periodStart, periodEnd);
    }

    @GetMapping("/{id}")
    public InfoPlanDto getPlanById(@PathVariable Long id) {
        return planService.getPlanDtoById(id);
    }

    @PostMapping
    public InfoPlanDto createPlan(@RequestBody PlanDto planDto) {
        return planService.createPlanDto(planDto);
    }

    @PatchMapping("/{id}")
    public InfoPlanDto updatePlan(@RequestBody PlanDto planDto) {
        return planService.updatePlanDto(planDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlanById(@PathVariable Long id) {
        planService.deletePlanDtoById(id);
        return new ResponseEntity<>(String.format("Plan with ID: %s was deleted!", id), HttpStatus.OK);
    }
}
