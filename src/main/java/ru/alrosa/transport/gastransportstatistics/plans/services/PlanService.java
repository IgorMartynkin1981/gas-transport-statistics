package ru.alrosa.transport.gastransportstatistics.plans.services;

import ru.alrosa.transport.gastransportstatistics.plans.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;

import java.util.Collection;

public interface PlanService {
    Collection<InfoPlanDto> getAllPlanDto(Long subdivisionId, String periodStart, String periodEnd);

    InfoPlanDto getPlanDtoById(Long planDtoId);

    InfoPlanDto updatePlanDto(PlanDto planDto);

    InfoPlanDto createPlanDto(PlanDto planDto);

    void deletePlanDtoById(Long planDtoId);
}
