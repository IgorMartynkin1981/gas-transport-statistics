package ru.alrosa.transport.gastransportstatistics.plans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.plans.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.services.PlanService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/plan")
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public Collection<InfoPlanDto> findAllPlans() {
        return planService.getAllPlanDto();
    }

    @GetMapping("/{id}")
    public InfoPlanDto getPlanById(@PathVariable Long id) {
        return planService.getPlanDtoById(id);
    }

    @PostMapping
    public InfoPlanDto createPlan(@RequestBody PlanDto planDto) {
        System.out.println(planDto.getUserId());
        return planService.createPlanDto(planDto);
    }
}
