package ru.alrosa.transport.gastransportstatistics.plans.services;

import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;

import java.util.Collection;

public interface PlanService {
    Collection<PlanDto> getAllPlanDto();

    PlanDto getPlanDtoById(Long planDtoId);

    PlanDto updatePlanDto(PlanDto planDto);

    PlanDto createPlanDto(PlanDto planDto);

    void deletePlanDtoById(Long planDtoId);
}
